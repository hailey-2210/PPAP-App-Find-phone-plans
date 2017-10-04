
package application.ppap_findphoneplans;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import application.ppap_findphoneplans.adapters.PhoneList;
import application.ppap_findphoneplans.models.Phone;

public class PhoneSearchActivity extends AppCompatActivity {
    public static final String PHONE_NAME = "com.hdo.phoneplanandpriceapp.phonename";
    public static final String PHONE_ID = "com.hdo.phoneplanandpriceapp.phoneid";

    EditText searchText;
    Button buttonSearchPhone;
    ListView listViewPhones;
    PhoneList phoneAdapter;

    //a list to store all the phone from firebase database
    List<Phone> phones;

    //our database reference object
    DatabaseReference databasePhones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_search);

        //getting the reference of phones node
        databasePhones = FirebaseDatabase.getInstance().getReference("phones");

        //getting views
        searchText = (EditText) findViewById(R.id.search);
        listViewPhones = (ListView) findViewById(R.id.listViewPhones);

        //list to store phones
        phones = new ArrayList<>();

        //attaching value event listener
        databasePhones.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //clearing the previous phone list
                phones.clear();
                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting phone
                    Phone phone = postSnapshot.getValue(Phone.class);
                    //adding phone to the list
                    phones.add(phone);
                }
                //creating adapter
                phoneAdapter = new PhoneList(PhoneSearchActivity.this, phones);
                //attaching adapter to the listview
                listViewPhones.setAdapter(phoneAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // search function
        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = searchText.getText().toString().toLowerCase(Locale.getDefault());
                phoneAdapter.filter(text);
            }
        });

        //attaching listener to listview
        listViewPhones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //getting the selected phone
                Phone phone = phones.get(i);

                //creating an intent
                Intent intent = new Intent(getApplicationContext(), PhonePlanActivity.class);

                //putting phone name and id to intent
                intent.putExtra(PHONE_ID, phone.getPhoneId());
                intent.putExtra(PHONE_NAME, phone.getPhoneName());

                //starting the activity with intent
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}

package application.ppap_findphoneplans;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import application.ppap_findphoneplans.adapters.DealList;
import application.ppap_findphoneplans.adapters.PlanList;
import application.ppap_findphoneplans.models.Deal;
import application.ppap_findphoneplans.models.Plan;

public class DealActivity extends AppCompatActivity {
    ListView listViewDeals;
    DealList dealAdapter;

    //a list to store all the phone from firebase database
    List<Deal> deals;

    //our database reference object
    DatabaseReference databaseDeals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deal);

        //getting the reference of phones node
        databaseDeals = FirebaseDatabase.getInstance().getReference("deals");

        //getting views
        listViewDeals = (ListView) findViewById(R.id.listViewDeals);

        //list to store phones
        deals = new ArrayList<>();

        //attaching value event listener
        databaseDeals.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //clearing the previous phone list
                deals.clear();
                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting phone
                    Deal deal = postSnapshot.getValue(Deal.class);
                    //adding phone to the list
                    deals.add(deal);
                }
                //creating adapter
                dealAdapter = new DealList(DealActivity.this, deals);
                //attaching adapter to the listview
                listViewDeals.setAdapter(dealAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //attaching listener to listview
        listViewDeals.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //getting the selected deal
                Deal deal = deals.get(i);

                //creating an intent
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse(deal.getUrl()));
                startActivity(intent);
            }
        });
    }

}

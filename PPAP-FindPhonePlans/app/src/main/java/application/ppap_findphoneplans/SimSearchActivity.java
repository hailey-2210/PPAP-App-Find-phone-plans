package application.ppap_findphoneplans;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import application.ppap_findphoneplans.adapters.PlanList;
import application.ppap_findphoneplans.models.Plan;

public class SimSearchActivity extends AppCompatActivity {

    EditText searchData;
    Switch ntnCallSwitch, intCallSwitch;
    Button buttonSearchSIM, buttonReset;
    ListView listViewSims;
    PlanList planAdapter;

    //a list to store all the phone from firebase database
    List<Plan> plans;

    //our database reference object
    DatabaseReference databaseSims;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sim_search);

        //getting the reference of phones node
        databaseSims = FirebaseDatabase.getInstance().getReference("SIMs");

        //getting views
        searchData = (EditText) findViewById(R.id.searchData);
        ntnCallSwitch = (Switch) findViewById(R.id.ntnCallSwitch);
        intCallSwitch =(Switch) findViewById(R.id.intCallSwitch);
        listViewSims = (ListView) findViewById(R.id.listViewSims);
        buttonSearchSIM = (Button) findViewById(R.id.searchSimBtn);
        buttonReset = (Button) findViewById(R.id.resetSimBtn);

        //list to store phones
        plans = new ArrayList<>();

        //attaching value event listener
        databaseSims.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //clearing the previous phone list
                plans.clear();
                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting phone
                    Plan plan = postSnapshot.getValue(Plan.class);
                    //adding phone to the list
                    plans.add(plan);
                }
                //creating adapter
                planAdapter = new PlanList(SimSearchActivity.this, plans);
                //attaching adapter to the listview
                listViewSims.setAdapter(planAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        buttonSearchSIM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                planAdapter.filter(searchData.getText().toString(), ntnCallSwitch.isChecked(), intCallSwitch.isChecked());
            }
        });

        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                planAdapter.resetFilter();
            }
        });


        //attaching listener to listview
        listViewSims.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //getting the selected phone
                Plan plan = plans.get(i);

                //creating an intent
                Intent intent = new Intent(getApplicationContext(), SingleItemView.class);

                //putting phone name and id to intent
                intent.putExtra("planId", plan.getId());
                intent.putExtra("name", plan.getPlanName());
                intent.putExtra("price", plan.getPrice());
                intent.putExtra("data", plan.getData());
                intent.putExtra("ntnCall", plan.getNtnCall());
                intent.putExtra("intCall", plan.getIntCall());
                intent.putExtra("providerUrl", plan.getProviderImgUrl());
                intent.putExtra("url", plan.getUrl());

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
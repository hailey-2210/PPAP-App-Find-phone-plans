
package application.ppap_findphoneplans;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import application.ppap_findphoneplans.adapters.PlanList;
import application.ppap_findphoneplans.models.Plan;

public class PhonePlanActivity extends AppCompatActivity {

    // Button buttonAddPlan;
    // EditText editTextPlanName, editTextPrice, editTextUrl, editTextProviderUrl;
    TextView textViewPhone;
    ListView listViewPlans;

    DatabaseReference databasePlans;

    List<Plan> plans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_plan);

        Intent intent = getIntent();

        databasePlans = FirebaseDatabase.getInstance().getReference("plans").child(intent.getStringExtra(PhoneSearchActivity.PHONE_ID));

        textViewPhone = (TextView) findViewById(R.id.textViewPhone);
        listViewPlans = (ListView) findViewById(R.id.listViewPlans);

        plans = new ArrayList<>();

        textViewPhone.setText(intent.getStringExtra(PhoneSearchActivity.PHONE_NAME));

        databasePlans.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                plans.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Plan plan = postSnapshot.getValue(Plan.class);
                    plans.add(plan);
                }
                PlanList planListAdapter = new PlanList(PhonePlanActivity.this, plans);
                listViewPlans.setAdapter(planListAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        listViewPlans.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
}


package application.ppap_findphoneplans;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.ArraySet;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import application.ppap_findphoneplans.adapters.FavoriteList;
import application.ppap_findphoneplans.adapters.PlanList;
import application.ppap_findphoneplans.models.Plan;

public class FavoriteActivity extends AppCompatActivity{

    ListView listViewFavs;
    FavoriteList favAdapter;

    //a list to store all the phone from firebase database
    List<Plan> favList;

    //our database reference object
    DatabaseReference databaseFavs;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav);

        Intent intent = getIntent();

        // authentication of users
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        databaseFavs = FirebaseDatabase.getInstance().getReference("favs").child(currentUser.getUid());
        listViewFavs = (ListView) findViewById(R.id.listViewFavs);

        //list to store phones
        favList = new ArrayList<>();

        //attaching value event listener
        databaseFavs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //clearing the previous phone list
                favList.clear();
                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting phone
                    Plan plan = postSnapshot.getValue(Plan.class);
                    //adding phone to the list
                    favList.add(plan);
                }
                //creating adapter
                favAdapter = new FavoriteList(FavoriteActivity.this, favList);
                //attaching adapter to the listview
                listViewFavs.setAdapter(favAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    @Override
    public void onStart() {
        super.onStart();

    }
    // [END on_start_check_user]


}

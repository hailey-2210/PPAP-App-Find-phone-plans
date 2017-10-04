package application.ppap_findphoneplans;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import application.ppap_findphoneplans.models.Plan;

public class SingleItemView extends AppCompatActivity {
    private final String TAG = SingleItemView.class.getSimpleName();

    // Declare Variables
    TextView textViewName, textViewPrice, textViewUrl, textViewData, textViewNtnCall, textViewIntCall;
    ImageView imageProvider;
    Button goBtn, shareBtn, addBtn;

    String id;
    String planName;
    String price;
    String data;
    String ntnCall;
    String intCall;
    String url;
    String providerUrl;

    private HashSet<Plan> favList; // favoriteList
    FirebaseAuth mAuth;
    DatabaseReference databaseFavs;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_item_view);

        // Get the intent from ListViewAdapter
        Intent i = getIntent();
        // Get the results of flags
        id = i.getStringExtra("planId");
        planName = i.getStringExtra("name");
        price = i.getStringExtra("price");
        providerUrl = i.getStringExtra("providerUrl");
        url = i.getStringExtra("url");
        data = i.getStringExtra("data");
        ntnCall = i.getStringExtra("ntnCall");
        intCall = i.getStringExtra("intCall");

        // Locate the TextViews in singleitemview.xml
        textViewName = (TextView) findViewById(R.id.textViewName);
        textViewPrice = (TextView) findViewById(R.id.textViewPrice);
        textViewData = (TextView) findViewById(R.id.textViewData);
        textViewNtnCall = (TextView) findViewById(R.id.textViewNtnCall);
        textViewIntCall = (TextView) findViewById(R.id.textViewIntCall);

        imageProvider = (ImageView) findViewById(R.id.imageProvider);
        goBtn = (Button) findViewById(R.id.goBtn);
        shareBtn = (Button) findViewById(R.id.shareBtn);
        addBtn = (Button) findViewById(R.id.addBtn);


        // Load the results into the TextViews
        textViewName.setText(planName);
        textViewPrice.setText("$ " + price + " per month");
        textViewData.setText("Data: " + data);
        textViewNtnCall.setText("National call and text: " + ntnCall);
        textViewIntCall.setText("International call: " + intCall);

        // Load the image into the ImageView
        Picasso.with(this)
                .load(providerUrl)
                .error(R.drawable.placeholder)
                .into(imageProvider);

        // Go Button set up

        goBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        // Share button set up
        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = planName + "-just $" + price + "per month with" + data + " data!"
                        + "\n" + "Url: " + url;
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Check this plan I have just found");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                SingleItemView.this.startActivity(Intent.createChooser(sharingIntent, "Share via"));

            }
        });

        // add to Favorite button

        // authentication of users & database
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser currentUser = mAuth.getCurrentUser();
        databaseFavs = FirebaseDatabase.getInstance().getReference("favs").child(currentUser.getUid());

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentUser.getUid()== null) {
                Toast.makeText(SingleItemView.this, "Please sign in or register to access your favorite list", Toast.LENGTH_LONG).show();
                }
                else { // add the product to fav list
                    String favId = databaseFavs.push().getKey();

                    //creating an Phone Object
                    Plan favItem = new Plan(id, planName, price, url, providerUrl, data, ntnCall, intCall);

                    //Saving the Phone
                    databaseFavs.child(id).setValue(favItem);

                    Toast.makeText(SingleItemView.this, "Added to favorite list",
                            Toast.LENGTH_SHORT).show();
                    }
            }
        });
    }

}
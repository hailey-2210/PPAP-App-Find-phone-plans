package application.ppap_findphoneplans.adapters;


import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.ArraySet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import application.ppap_findphoneplans.R;
import application.ppap_findphoneplans.models.Plan;

public class FavoriteList extends ArrayAdapter<Plan> {
    private Activity context;
    LayoutInflater inflater;
    List<Plan> items;

    public FavoriteList (Activity context, List<Plan> items){
        super(context, R.layout.layout_fav_list, items);
        inflater = context.getLayoutInflater();
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listViewItem = inflater.inflate(R.layout.layout_fav_list, null, true);

        TextView textViewPlanName = (TextView) listViewItem.findViewById(R.id.tvPlanName);
        TextView textViewPrice = (TextView) listViewItem.findViewById(R.id.tvPrice);
      /*  TextView textViewData =(TextView) listViewItem.findViewById(R.id.textViewDisplay);
        TextView textViewNtnCall = (TextView) listViewItem.findViewById(R.id.textViewCamera);
        TextView textViewIntCall = (TextView) listViewItem.findViewById(R.id.textViewMemory);*/
        TextView textViewDescription = (TextView) listViewItem.findViewById(R.id.tvDescription);
        ImageView imageProvider = (ImageView) listViewItem.findViewById(R.id.imageProvider);

        Plan item = items.get(position);
        textViewPlanName.setText(item.getPlanName());
        textViewPrice.setText("$" + item.getPrice() + " per month");
     /*   textViewData.setText(plan.getData());
        textViewNtnCall.setText(plan.getNntCall());
        textViewIntCall.setText(plan.getIntCall());*/
        textViewDescription.setText("Data: " + item.getData() + "\n"
                + "National calls and texts: " + item.getNtnCall() + "\n"
                + "International calls: " + item.getIntCall());


        //Initializing the ImageView


        //Loading Image from URL
        Picasso.with(context)
                .load(item.getProviderImgUrl())
                .error(R.drawable.placeholder)
                .into(imageProvider);

        return listViewItem;

    }
}
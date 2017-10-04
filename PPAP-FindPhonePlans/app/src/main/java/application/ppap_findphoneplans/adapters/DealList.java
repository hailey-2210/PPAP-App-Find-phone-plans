package application.ppap_findphoneplans.adapters;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import application.ppap_findphoneplans.R;
import application.ppap_findphoneplans.models.Deal;

public class DealList extends ArrayAdapter<Deal> {
    private Activity context;
    LayoutInflater inflater;
    List<Deal> deals;

    public DealList(Activity context, List<Deal> deals) {
        super(context, R.layout.layout_deal_list, deals);
        inflater = context.getLayoutInflater();
        this.context = context;
        this.deals = deals;

    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View listViewItem = inflater.inflate(R.layout.layout_deal_list, null, true);

        TextView textViewDealName = (TextView) listViewItem.findViewById(R.id.textViewDeal);
        ImageView dealImg = (ImageView) listViewItem.findViewById(R.id.dealImg);

        Deal deal = deals.get(position);
        textViewDealName.setText(deal.getName() + " provided by " + deal.getProvider());
        //Initializing the ImageView


        //Loading Image from URL
        Picasso.with(context)
                .load(deal.getImgUrl())
                .error(R.drawable.placeholder)
                .into(dealImg);
        return listViewItem;
    }
}


package application.ppap_findphoneplans.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import application.ppap_findphoneplans.models.Phone;
import application.ppap_findphoneplans.R;


public class PhoneList extends ArrayAdapter<Phone> {
    LayoutInflater inflater;
    private Activity context;
    List<Phone> phones;
    List<Phone> filterList;


    public PhoneList(Activity context, List<Phone> phones) {
        super(context, R.layout.layout_phone_list, phones);
        this.context = context;
        this.phones = phones;
        inflater = context.getLayoutInflater();
        this.filterList = new ArrayList<>();
        this.filterList.addAll(phones);

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listViewItem = inflater.inflate(R.layout.layout_phone_list, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewPhoneName);
        TextView textViewBrand = (TextView) listViewItem.findViewById(R.id.textViewBrand);
        TextView textViewReview = (TextView) listViewItem.findViewById(R.id.textViewReview);
        ImageView imagePhone = (ImageView) listViewItem.findViewById(R.id.imagePhone);

        Phone phone = phones.get(position);
        textViewName.setText(phone.getPhoneName());
        textViewBrand.setText(phone.getPhoneBrand());
        textViewReview.setText(phone.getReview());
        //Initializing the ImageView


        //Loading Image from URL
        Picasso.with(context)
                .load(phone.getImgUrl())
                .error(R.drawable.placeholder)
                .into(imagePhone);

        return listViewItem;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        phones.clear();
        if (charText.length() == 0) {
            phones.addAll(filterList);
        } else {
            for (Phone phone : filterList) {
                if (phone.getPhoneName().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    phones.add(phone);
                }
            }
        }
        notifyDataSetChanged();
    }



}


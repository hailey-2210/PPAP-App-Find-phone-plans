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
import java.util.Scanner;

import application.ppap_findphoneplans.models.Plan;
import application.ppap_findphoneplans.R;

public class PlanList extends ArrayAdapter<Plan> {
        private Activity context;
        LayoutInflater inflater;
        List<Plan> plans;
        List<Plan> allPlanList;

    public PlanList(Activity context, List<Plan> plans) {
        super(context, R.layout.layout_phone_list, plans);
        inflater = context.getLayoutInflater();
        this.context = context;
        this.plans = plans;
        this.allPlanList = new ArrayList<>();
        this.allPlanList.addAll(plans);

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listViewItem = inflater.inflate(R.layout.layout_phone_list, null, true);

        TextView textViewPlanName = (TextView) listViewItem.findViewById(R.id.textViewPhoneName);
        TextView textViewPrice = (TextView) listViewItem.findViewById(R.id.textViewBrand);
      /*  TextView textViewData =(TextView) listViewItem.findViewById(R.id.textViewDisplay);
        TextView textViewNtnCall = (TextView) listViewItem.findViewById(R.id.textViewCamera);
        TextView textViewIntCall = (TextView) listViewItem.findViewById(R.id.textViewMemory);*/
        TextView textViewDescription = (TextView) listViewItem.findViewById(R.id.textViewReview);
        ImageView imageProvider = (ImageView) listViewItem.findViewById(R.id.imagePhone);

        Plan plan = plans.get(position);
        textViewPlanName.setText(plan.getPlanName());
        textViewPrice.setText("$" + plan.getPrice() + " per month");
     /*   textViewData.setText(plan.getData());
        textViewNtnCall.setText(plan.getNntCall());
        textViewIntCall.setText(plan.getIntCall());*/
        textViewDescription.setText("Data: " + plan.getData() + "\n"
                + "National calls and texts: " + plan.getNtnCall() + "\n"
                + "International calls: " + plan.getIntCall());


        //Initializing the ImageView


        //Loading Image from URL
        Picasso.with(context)
                .load(plan.getProviderImgUrl())
                .error(R.drawable.placeholder)
                .into(imageProvider);

        return listViewItem;
    }

    public void filter(String dataMin, boolean unlimitedNtnCall, boolean includedIntCall) {
        plans.addAll(allPlanList);
        // filter by data
        if (!dataMin.isEmpty() && dataMin.length() != 0 && !dataMin.equals("") && dataMin != null) {
            for (int i =0; i<plans.size(); i++) {
                int dataPlan = new Scanner(plans.get(i).getData()).useDelimiter("[^0-9]+").nextInt();
                int dataQuery = Integer.parseInt(dataMin);
                if (dataPlan < dataQuery) {
                    plans.remove(plans.get(i));
                }
            }
        }
        // filter by ntnCall
        if (unlimitedNtnCall = true) {
            for (int i = 0; i<plans.size(); i++) {
                if (!plans.get(i).getNtnCall().toLowerCase(Locale.getDefault()).contains("unlimited")) {
                    plans.remove(plans.get(i));
                }
            }
        }
        // filter by intCall
        if (includedIntCall = true) {
            for (int i=0; i<plans.size(); i++) {
                if (plans.get(i).getIntCall().toLowerCase(Locale.getDefault()).contains("none")
                        | plans.get(i).getIntCall().toLowerCase(Locale.getDefault()).contains("not available")
                        | plans.get(i).getIntCall().toLowerCase(Locale.getDefault()).isEmpty()) {
                    plans.remove(plans.get(i));
                }
            }
        }
        notifyDataSetChanged();
    }

    public void resetFilter() {
        plans.addAll(allPlanList);
    }

}
package com.example.covtrack;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DistrictRecyclerViewAdapter extends RecyclerView.Adapter<DistrictRecyclerViewAdapter.DistrictRecyclerViewHolder> {
    private Context context;
    private ArrayList<DistricCasesModel> districtCasesModelArrayList;

    public DistrictRecyclerViewAdapter(Context context, ArrayList<DistricCasesModel> distCasesModelArrayList) {
        this.context = context;
        this.districtCasesModelArrayList = distCasesModelArrayList;
    }

    @NonNull
    @Override
    public DistrictRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_item, parent, false);
        return new DistrictRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DistrictRecyclerViewHolder holder, int position) {
        DistricCasesModel stateCasesModelObj = districtCasesModelArrayList.get(position);

        String state = stateCasesModelObj.getDistrict();
        String death = stateCasesModelObj.getDeath();
        String recovered = stateCasesModelObj.getRecovered();
        String active = stateCasesModelObj.getActive();
        String confirmed = stateCasesModelObj.getConfirmed();
        String todayDeath = stateCasesModelObj.getTodayDeath();
        String todayActive = stateCasesModelObj.getTodayActive();
        String todayRecovered = stateCasesModelObj.getTodayRecovered();

        holder.district.setText(state);
        holder.death.setText(death);
        holder.recovered.setText(recovered);
        holder.active.setText(active);
        holder.confirmed.setText(confirmed);
        holder.todayDeath.setText(String.format("(%s)", todayDeath));
        holder.todayActive.setText(String.format("(%s)", todayActive));
        holder.todayRecovered.setText(String.format("(%s)", todayRecovered));
    }

    @Override
    public int getItemCount() {
        return districtCasesModelArrayList.size();
    }

    public class DistrictRecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView district, death, recovered, active, confirmed, todayDeath, todayActive, todayRecovered;
        Button viewmore;
        LinearLayout lastupdatedlayout;

        public DistrictRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            district = itemView.findViewById(R.id.stateName);
            death = itemView.findViewById(R.id.death);
            recovered = itemView.findViewById(R.id.recovered);
            active = itemView.findViewById(R.id.active);
            confirmed = itemView.findViewById(R.id.confirmed);
            todayDeath = itemView.findViewById(R.id.todayDeath);
            todayActive = itemView.findViewById(R.id.todayActive);
            todayRecovered = itemView.findViewById(R.id.todayRecovered);
            viewmore = itemView.findViewById(R.id.viewmore);
            viewmore.setVisibility(View.GONE);
            lastupdatedlayout = itemView.findViewById(R.id.lastupdatedllayout);
            lastupdatedlayout.setVisibility(View.GONE);
        }
    }
}

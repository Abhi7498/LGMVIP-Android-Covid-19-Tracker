package com.example.covtrack;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StateRecyclerViewAdapter extends RecyclerView.Adapter<StateRecyclerViewAdapter.StateRecyclerViewHolder> {
    private Context context;
    private ArrayList<StateCasesModel> stateCasesModelArrayList;

    public StateRecyclerViewAdapter(Context context, ArrayList<StateCasesModel> stateCasesModelArrayList) {
        this.context = context;
        this.stateCasesModelArrayList = stateCasesModelArrayList;
    }

    @NonNull
    @Override
    public StateRecyclerViewAdapter.StateRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_item, parent, false);
        return new StateRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StateRecyclerViewAdapter.StateRecyclerViewHolder holder, int position) {
        StateCasesModel stateCasesModelObj = stateCasesModelArrayList.get(position);
        String state = stateCasesModelObj.getState();
        String death = stateCasesModelObj.getDeath();
        String recovered = stateCasesModelObj.getRecovered();
        String active = stateCasesModelObj.getActive();
        String confirmed = stateCasesModelObj.getConfirmed();
        String lastUpdt = stateCasesModelObj.getLastUpdated();
        String todayDeath = stateCasesModelObj.getTodayDeath();
        String todayActive = stateCasesModelObj.getTodayActive();
        String todayRecovered = stateCasesModelObj.getTodayRecovered();

        holder.state.setText(state);
        holder.death.setText(death);
        holder.recovered.setText(recovered);
        holder.active.setText(active);
        holder.confirmed.setText(confirmed);
        holder.lastUpdate.setText(lastUpdt);
        holder.todayDeath.setText(String.format("(%s)", todayDeath));
        holder.todayActive.setText(String.format("(%s)", todayActive));
        holder.todayRecovered.setText(String.format("(%s)", todayRecovered));

        holder.viewmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent districtIntent = new Intent(context, DistrictActivity.class);
                districtIntent.putExtra("statename", state);
                context.startActivity(districtIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return stateCasesModelArrayList.size();
    }

    public class StateRecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView state, death, recovered, active, confirmed, lastUpdate, todayDeath, todayActive, todayRecovered;
        Button viewmore;

        public StateRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            state = itemView.findViewById(R.id.stateName);
            death = itemView.findViewById(R.id.death);
            recovered = itemView.findViewById(R.id.recovered);
            active = itemView.findViewById(R.id.active);
            confirmed = itemView.findViewById(R.id.confirmed);
            lastUpdate = itemView.findViewById(R.id.lastUpdated);
            todayDeath = itemView.findViewById(R.id.todayDeath);
            todayActive = itemView.findViewById(R.id.todayActive);
            todayRecovered = itemView.findViewById(R.id.todayRecovered);
            viewmore = itemView.findViewById(R.id.viewmore);
        }
    }

}

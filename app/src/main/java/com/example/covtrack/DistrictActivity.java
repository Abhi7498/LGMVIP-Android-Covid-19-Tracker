package com.example.covtrack;

import static com.example.covtrack.MainActivity.hasInternet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DistrictActivity extends AppCompatActivity {
    private TextView dailyDeaths, dailyConfirm, dailyReco, dateHeaders, totalDeath, totalConfirm, totalRecovered;
    private RecyclerView recyclerView;
    private ProgressBar districtProgressBar;
    private ArrayList<DistricCasesModel> covidDistrictList;
    private RequestQueue requestQueue;
    private String statename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_district);
        Intent stateIntent = getIntent();
        Bundle bundle = stateIntent.getExtras();

        if(bundle != null){
            statename = bundle.getString("statename");
            if(getSupportActionBar() != null) {
                getSupportActionBar().setTitle(statename);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0C23A6")));
            }

        }

        recyclerView = findViewById(R.id.districtrecyclerview);
        districtProgressBar = findViewById(R.id.districtProgressBar);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        covidDistrictList = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(this);

        if(hasInternet(getApplicationContext())) {
            getDistrictData();
        }
        else {
            districtProgressBar.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), "This service needs internet connection!", Toast.LENGTH_SHORT).show();
        }

    }

    private void getDistrictData() {
        String url = "https://data.covid19india.org/state_district_wise.json";
        districtProgressBar.setVisibility(View.VISIBLE);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject districtwiseJson = response.getJSONObject(statename);
                    JSONObject districtData = districtwiseJson.getJSONObject("districtData");

                    for (int i = 0; i < districtData.length(); i++) {
                        JSONArray distictNames = districtData.names();
                        Log.d("CovTrack", "onResponse: "+districtData);
                        Log.d("Distict","District Names"+distictNames);

                        JSONObject individualdistrict = districtData.getJSONObject(distictNames.get(i).toString());
                        Log.d("Individual Distict","Individual District Names"+individualdistrict);
                        String active = individualdistrict.getString("active");
                        String death = individualdistrict.getString("deceased");
                        String recovered = individualdistrict.getString("recovered");
                        String district = distictNames.get(i).toString();
                        String confirmed = individualdistrict.getString("confirmed");

                        JSONObject delta = individualdistrict.getJSONObject("delta");
                        String todayActive = "+" + delta.getString("confirmed");
                        String todayDeath = "+" + delta.getString("deceased");
                        String todayRecovered = "+" + delta.getString("recovered");


                        DistricCasesModel stateItem = new DistricCasesModel(district, death, active, recovered, confirmed, todayDeath, todayRecovered, todayActive);
                        covidDistrictList.add(stateItem);

                        districtProgressBar.setVisibility(View.GONE);
                        DistrictRecyclerViewAdapter districtRecyclerViewAdapter = new DistrictRecyclerViewAdapter(DistrictActivity.this, covidDistrictList);
                        recyclerView.setAdapter(districtRecyclerViewAdapter);
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        requestQueue.add(request);
    }

    // this event will enable the functionality of the back button(in the action bar)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
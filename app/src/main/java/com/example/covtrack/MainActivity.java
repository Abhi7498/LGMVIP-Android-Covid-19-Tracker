
package com.example.covtrack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
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

public class MainActivity extends AppCompatActivity {

    private TextView dailyDeaths, dailyConfirm, dailyReco, dateHeaders, totalDeath, totalConfirm, totalRecovered;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private ArrayList<StateCasesModel> covidStateList;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        dailyConfirm = findViewById(R.id.dailyConfirm);
        dailyDeaths = findViewById(R.id.dailyDeath);
        dailyReco = findViewById(R.id.dailyRecovered);
        dateHeaders = findViewById(R.id.dateHeader);

        totalRecovered = findViewById(R.id.totalRecovered);
        totalConfirm = findViewById(R.id.totalConfirm);
        totalDeath = findViewById(R.id.totalDeath);

        recyclerView = findViewById(R.id.recyclerview);
        progressBar = findViewById(R.id.progressBar);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        covidStateList = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(this);
        if(hasInternet(getApplicationContext())) {
            getStateData();
        }
        else {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), "This service needs internet connection!", Toast.LENGTH_SHORT).show();
        }

    }

    private void getStateData(){
        progressBar.setVisibility(View.VISIBLE);
        String url = "https://data.covid19india.org/data.json";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray stateWiseArray = response.getJSONArray("statewise");
                    JSONObject currentIndiaData = stateWiseArray.getJSONObject(0);

                    String dailyConfirmed = "+" + currentIndiaData.getString("deltaconfirmed");
                    String dailyDeath = "+" + currentIndiaData.getString("deltadeaths");
                    String dailyRec = "+" + currentIndiaData.getString("deltarecovered");
                    String date = currentIndiaData.getString("lastupdatedtime");
                    String dateHeader = date.substring(0,10);

                    String currentTotalDeath = currentIndiaData.getString("deaths");
                    String currentTotalRecovered = currentIndiaData.getString("recovered");
                    String currentTotalConfirmed = currentIndiaData.getString("confirmed");

                    dailyConfirm.setText(dailyConfirmed);
                    dailyReco.setText(dailyRec);
                    dailyDeaths.setText(dailyDeath);
                    dateHeaders.setText(dateHeader);
                    totalConfirm.setText(currentTotalConfirmed);
                    totalRecovered.setText(currentTotalRecovered);
                    totalDeath.setText(currentTotalDeath);

                    for (int i = 1; i < stateWiseArray.length(); i++) {
                        JSONObject stateWiseArrayJSONObject = stateWiseArray.getJSONObject(i);
                        Log.d("CovTrack", "onResponse: "+stateWiseArrayJSONObject);
                        String active = stateWiseArrayJSONObject.getString("active");
                        String death = stateWiseArrayJSONObject.getString("deaths");
                        String recovered = stateWiseArrayJSONObject.getString("recovered");
                        String state = stateWiseArrayJSONObject.getString("state");
                        String confirmed = stateWiseArrayJSONObject.getString("confirmed");
                        String lastUpdated = stateWiseArrayJSONObject.getString("lastupdatedtime");


                        String todayActive = "+" + stateWiseArrayJSONObject.getString("deltaconfirmed");
                        String todayDeath = "+" + stateWiseArrayJSONObject.getString("deltadeaths");
                        String todayRecovered = "+" + stateWiseArrayJSONObject.getString("deltarecovered");


                        StateCasesModel stateItem = new StateCasesModel(state, death, active, recovered, confirmed, lastUpdated, todayDeath, todayRecovered, todayActive);
                        covidStateList.add(stateItem);

                        progressBar.setVisibility(View.GONE);
                        StateRecyclerViewAdapter stateRecyclerViewAdapter = new StateRecyclerViewAdapter(MainActivity.this, covidStateList);
                        recyclerView.setAdapter(stateRecyclerViewAdapter);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, "Something went wrong... Try again later", Toast.LENGTH_SHORT).show();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        requestQueue.add(request);
        Log.d("Ending", "executed...");
    }

    public static boolean hasInternet(Context c) {
        ConnectivityManager cm = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return(netInfo != null && netInfo.isConnected());
    }

}
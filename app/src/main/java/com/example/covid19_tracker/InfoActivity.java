package com.example.covid19_tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import model.Engine;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class InfoActivity extends AppCompatActivity {

    private String ver = "xa";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        TextView region = findViewById(R.id.countryText);
        final TextView totalCount = findViewById(R.id.covidCountText);
        final TextView deathCount = findViewById(R.id.deathCountText);
        final TextView hospitalCount = findViewById(R.id.hospitalCountText);
        final TextView recoverCount = findViewById(R.id.recoverCountText);
        final TextView testCount = findViewById(R.id.testsCountText);
        final TextView critCount = findViewById(R.id.critsCountText);
        Engine engine = new Engine();
        String passedRegion = getIntent().getExtras().getString("cty");
        region.setText(passedRegion);
        String code = engine.getCode(passedRegion);
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "";
        if(code.equals("")){
            url = "https://api.covid19tracker.ca/reports";
        }
        else{
            url = "https://api.covid19tracker.ca/reports/province/" + code;
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String date = response.getString("last_updated");
                            JSONObject data = response.getJSONArray("data").getJSONObject(response.getJSONArray("data").length()-1);
                            int tc = data.getInt("total_cases");
                            int dc = data.getInt("total_fatalities");
                            int ac = data.getInt("change_cases");
                            int ad = data.getInt("change_fatalities");

                            int hc = data.getInt("total_hospitalizations");
                            int ah = data.getInt("change_hospitalizations");;
                            int rc = data.getInt("total_recoveries");;
                            int ar = data.getInt("change_recoveries");;

                            int tt = data.getInt("change_tests");
                            int cc = data.getInt("total_criticals");


                            totalCount.setText(tc + " (" + ac + " today)");
                            deathCount.setText(dc + " (" + ad + " today)");
                            hospitalCount.setText(hc + " (" + ah + " today)");
                            recoverCount.setText(rc + " (" + ar + " today)");
                            testCount.setText(String.valueOf(tt));
                            critCount.setText(String.valueOf(cc));

                        } catch (JSONException e) {
                            e.printStackTrace();
                            totalCount.setText("WRONG");
                        }


                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        totalCount.setText(error.toString());
                    }
                });
        queue.add(jsonObjectRequest);







    }
}
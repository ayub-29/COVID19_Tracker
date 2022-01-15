package model;


import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Engine {
    public interface VolleyCallback {
        void onSuccess(String result);
        void onError(String result);
    }
    public final String[] items = new String[]{"All regions","Alberta", "British Columbia", "Manitoba", "New Brunswick",
            "Newfoundland and Labrador", "Northwest Territories", "Nova Scotia", "Nunavut", "Ontario",
            "Prince Edward Island", "Quebec", "Saskatchewan", "Yukon"};
    public final String[] codes = new String[]{"","AB", "BC", "MB", "NB", "NL", "NT", "NS", "NU", "ON", "PE", "QC", "SK", "YT"};
    private HashMap<String, String> map;
    public Engine(){
        this.map = new HashMap<>();
        for(int i = 0; i < this.items.length; i++){
            this.map.put(this.items[i], this.codes[i]);
        }
    }

    public String getCode(String loc){
        return this.map.get(loc);
    }

    public void getVersion(RequestQueue queue,final VolleyCallback callback){
        //First get version
        String url = "https://api.opencovid.ca/version?dateonly=true";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            callback.onSuccess(response.getString("version"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            callback.onError(e.toString());
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        queue.add(jsonObjectRequest);
    }







}

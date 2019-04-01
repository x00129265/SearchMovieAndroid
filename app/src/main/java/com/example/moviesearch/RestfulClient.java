package com.example.moviesearch;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestfulClient {
    private Context mContext;

    public RestfulClient(Context mContext){
        this.mContext = mContext;
    }

    private String SERVICE_URI = "https://ca2ead2api.azurewebsites.net/api/Movies/";

    public void callService(String query, final ServerCallback callback)
    {
        try
        {
            query = query.replace(" ", "_"); // on server String "Name_2014 will change to Name 2014
            RequestQueue queue = Volley.newRequestQueue(mContext);
            try
            {
                StringRequest strObjRequest = new StringRequest(Request.Method.GET, SERVICE_URI+query,
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response){
                                callback.onSuccess(response);
                            }
                        },
                        new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError error)
                            {
                                callback.onError();
                                Log.d("RestfulClient, Error", error.toString());
                            }

                        });
                queue.add(strObjRequest);
            }
            catch(Exception e1)
            {
                Log.d("RestfulClient, e1", e1.toString());
            }
        }
        catch (Exception e2)
        {
            Log.d("RestfulClient, e2", e2.toString());
        }
    }
}

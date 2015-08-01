package com.example.arafathossain.smsreceiver;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;


public class SMSUpload {
    private static final String SERVER_URL = "http://darksider.byethost13.com/sms/insert_sms_detail.php";
    private RequestQueue queue;
    private Pppp pppp;

    public SMSUpload(Context context,Pppp pppp) {
        queue = Volley.newRequestQueue(context);
        this.pppp = pppp;
    }

    public void makeRequest(final SMS sms) {
        StringRequest request = new StringRequest(Request.Method.POST, SERVER_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.d("response", s);
                if (s.equalsIgnoreCase("upload complete"))if (pppp!=null) pppp.qqqq(sms.getId());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyError.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("from", sms.getFrom());
                params.put("body", sms.getBody());
                params.put("time", sms.getTime());
                return params;
            }
        };
        queue.add(request);
        Log.d("pppp", "asda");
    }
    interface Pppp{
        void qqqq(String id);
    }

}
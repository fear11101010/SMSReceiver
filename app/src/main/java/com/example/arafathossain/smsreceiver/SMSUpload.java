package com.example.arafathossain.smsreceiver;

import android.content.Context;
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
    private static final String SERVER_URL = "http://api.livestockbd.info//StoreMassage/createAnEntry";
    private RequestQueue queue;
    private OnResponseListener onResponseListener;

    public SMSUpload(Context context, OnResponseListener onResponseListener) {
        queue = Volley.newRequestQueue(context);
        this.onResponseListener = onResponseListener;
    }

    public void makeRequest(final SMS sms) {
        StringRequest request = new StringRequest(Request.Method.POST, SERVER_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.d("response", s);
                if (s.equalsIgnoreCase("\"Stored Successfully\"")) if (onResponseListener != null)
                    onResponseListener.OnResponseOk(sms.getId());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                onResponseListener.OnResponseFailed(sms);
                volleyError.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("number", sms.getFrom());
                params.put("sms", sms.getBody());
                params.put("received", sms.getTime());
                return params;
            }
        };
        queue.add(request);
        Log.d("onResponseListener", "asda");
    }

    interface OnResponseListener {
        void OnResponseOk(String id);

        void OnResponseFailed(SMS sms);
    }

}

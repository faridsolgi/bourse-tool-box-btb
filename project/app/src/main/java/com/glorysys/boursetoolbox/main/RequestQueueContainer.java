package com.glorysys.boursetoolbox.main;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class RequestQueueContainer {
    private static RequestQueue requestQueue;
    private  RequestQueueContainer(){
    }
    public static RequestQueue getInstance(Context context){
        if(requestQueue==null){
            requestQueue= Volley.newRequestQueue(context);
        }
        return requestQueue;
    }


}

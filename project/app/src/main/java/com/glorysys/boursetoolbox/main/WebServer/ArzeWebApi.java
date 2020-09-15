package com.glorysys.boursetoolbox.main.WebServer;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.glorysys.boursetoolbox.main.Models.ArzeDataSample;
import com.glorysys.boursetoolbox.main.RequestQueueContainer;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ArzeWebApi {

    private Context context;
    public ArzeWebApi(Context context){
        this.context=context;
    }
    public void getapiarze(final getApiArzeCallBack getApiArzeCallBack){
        String url="http://glorysys.ir/api/v1/ipo";
        final JsonArrayRequest jsonArrayRequestArze=new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        ArzeDataSample arzeDataSample=new ArzeDataSample();
                        List<ArzeDataSample> arzeDataSamples=new ArrayList<>();
                        for (int i = 0; i <response.length() ; i++) {
                            try {
                                JSONObject jsonObject=response.getJSONObject(i);
                                arzeDataSample.setCountdown(String.valueOf(jsonObject.getLong("countdown")));
                                arzeDataSample.setName(jsonObject.getString("name"));
                                arzeDataSamples.add(arzeDataSample);
                            } catch (Exception e) { e.getStackTrace();}
                        }

                        getApiArzeCallBack.arzeSuccess(arzeDataSamples);

                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //   Toast.makeText(MainActivity.this, , Toast.LENGTH_SHORT).show();
                getApiArzeCallBack.arzeError();

            }
        });
        RequestQueueContainer.getInstance(context).add(jsonArrayRequestArze);
        jsonArrayRequestArze.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }
    public interface getApiArzeCallBack{
        public void arzeSuccess(List<ArzeDataSample> arzeDataSampleList);
        public void arzeError();

    }
}

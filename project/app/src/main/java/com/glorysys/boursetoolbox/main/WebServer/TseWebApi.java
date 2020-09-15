package com.glorysys.boursetoolbox.main.WebServer;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.glorysys.boursetoolbox.main.RequestQueueContainer;
import com.glorysys.boursetoolbox.main.Models.ArzeDataSample;
import com.glorysys.boursetoolbox.main.Models.TseDataSimple;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TseWebApi {
    private Context context;
    private static String url="http://glorysys.ir/api/v1/tseparseday";
    public TseWebApi(Context context){
        this.context=context;
    }
    public void getapi(final getApiCallBack getApiCallBack){
        final JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        List<TseDataSimple> dataBazarmains = new ArrayList<>();
                        TseDataSimple dataBazarmain = new TseDataSimple();
                        for (int i = 0; i <response.length() ; i++) {
                        try {
                            JSONObject jsonObject=response.getJSONObject(i);
                            dataBazarmain.setBazarStatusText(jsonObject.getString("name0"));
                            dataBazarmain.setShakhesKolText(jsonObject.getString("name1"));
                            dataBazarmain.setShakhesKolHamVaznText(jsonObject.getString("name2"));
                            dataBazarmain.setArzeshBazarText(jsonObject.getString("name3"));
                            dataBazarmain.setPriceInfoText(jsonObject.getString("name4"));
                            dataBazarmain.setNumOfExchangeText(jsonObject.getString("name5"));
                            dataBazarmain.setValueOfExchangeText(jsonObject.getString("name6"));
                            dataBazarmain.setVolOfExchangeText(jsonObject.getString("name7"));
                            dataBazarmain.setBazarStatus(jsonObject.getString("status0"));
                            dataBazarmain.setShakhesKol(jsonObject.getString("status1"));
                            dataBazarmain.setShakhesKolHamVazn(jsonObject.getString("status3"));
                            dataBazarmain.setArzeshBazar(jsonObject.getString("status5"));
                            dataBazarmain.setPriceInfo(jsonObject.getString("status6"));
                            dataBazarmain.setNumOfExchange(jsonObject.getString("status7"));
                            dataBazarmain.setValueOfExchange(jsonObject.getString("status8"));
                            dataBazarmain.setVolOfExchange(jsonObject.getString("status9"));
                            dataBazarmain.setShakhesKolChange(jsonObject.getString("change2"));
                            dataBazarmain.setShakhesKolHamVaznChange(jsonObject.getString("change4"));
                            dataBazarmains.add(dataBazarmain);
                        } catch (Exception e) {

                        }
                        }
                    getApiCallBack.Success(dataBazarmains);
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getApiCallBack.Error();
            }
    });
        RequestQueueContainer.getInstance(context).add(jsonArrayRequest);
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }
    public interface getApiCallBack{
        public void Success(List<TseDataSimple> dataBazarmainList);
        public void Error();
        
    }
}



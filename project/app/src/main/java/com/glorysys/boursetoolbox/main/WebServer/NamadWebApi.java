package com.glorysys.boursetoolbox.main.WebServer;

import android.content.Context;

import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.glorysys.boursetoolbox.main.RequestQueueContainer;
import com.glorysys.boursetoolbox.main.Models.NamadsDataSample;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class NamadWebApi {
    private Context context;
    private static String url="http://glorysys.ir/api/v1/namads";
    public NamadWebApi(Context context){
        this.context=context;
    }
    public void getapi(final getApiCallBack getApiCallBack){
        final JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        List<NamadsDataSample> namadsDataSamples = new ArrayList<>();
                        for (int i = 0; i <response.length() ; i++) {
                            try {

                                NamadsDataSample namadsDataSample = new NamadsDataSample();
                                JSONObject jsonObject=response.getJSONObject(i);
                                namadsDataSample.setNamadName(jsonObject.getString("namadname"));
                                namadsDataSample.setNamadCompanyName(jsonObject.getString("namadcompany"));
                                namadsDataSample.setAmount(jsonObject.getLong("amount"));
                                namadsDataSample.setVolume(jsonObject.getLong("volume"));
                                namadsDataSample.setValue(jsonObject.getLong("value"));
                                namadsDataSample.setYesterday(jsonObject.getLong("yesterday"));
                                namadsDataSample.setFirstPrice(jsonObject.getLong("firstprice"));
                                namadsDataSample.setLastDealPriceAmount(jsonObject.getLong("lastdealpriceamount"));
                                namadsDataSample.setLastDealChangePrice(jsonObject.getLong("lastdealchangeprice"));
                                namadsDataSample.setLastDealPercentage(jsonObject.getDouble("lastdealpercentage"));
                                namadsDataSample.setLastPriceAmount(jsonObject.getLong("lastpriceamount"));
                                namadsDataSample.setLastPriceChange(jsonObject.getLong("lastpricechange"));
                                namadsDataSample.setLastPricePercentage(jsonObject.getDouble("lastpricepercentage"));
                                namadsDataSample.setLowestPrice(jsonObject.getLong("lowestprice"));
                                namadsDataSample.setHighestPrice(jsonObject.getLong("highestprice"));
                                namadsDataSample.setEPS(jsonObject.getString("eps"));
                                namadsDataSample.setPE(jsonObject.getString("pe"));
                                namadsDataSample.setBuy(jsonObject.getString("buy"));
                                namadsDataSample.setSell(jsonObject.getString("sell"));
                                namadsDataSamples.add(namadsDataSample);
                            } catch (Exception e) { }

                        }
                        getApiCallBack.Success(namadsDataSamples);
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getApiCallBack.Error();
            }
        }){
            @Override
            protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {




                try {
                    Cache.Entry cacheEntry = HttpHeaderParser.parseCacheHeaders(response);
                    if (cacheEntry == null) {
                        cacheEntry = new Cache.Entry();
                    }
                    final long cacheHitButRefreshed = 3 * 60 * 1000; // in 3 minutes cache will be hit, but also refreshed on background
                    final long cacheExpired = 1* 60 * 60 * 1000; // in 1 hours this cache entry expires completely
                    long now = System.currentTimeMillis();
                    final long softExpire = now + cacheHitButRefreshed;
                    final long ttl = now + cacheExpired;
                    cacheEntry.data = response.data;
                    cacheEntry.softTtl = softExpire;
                    cacheEntry.ttl = ttl;
                    String headerValue;
                    headerValue = response.headers.get("Date");
                    if (headerValue != null) {
                        cacheEntry.serverDate = HttpHeaderParser.parseDateAsEpoch(headerValue);
                    }
                    headerValue = response.headers.get("Last-Modified");
                    if (headerValue != null) {
                        cacheEntry.lastModified = HttpHeaderParser.parseDateAsEpoch(headerValue);
                    }
                    cacheEntry.responseHeaders = response.headers;
                    final String jsonString = new String(response.data,
                            HttpHeaderParser.parseCharset(response.headers));
                    return Response.success(new JSONArray(jsonString), cacheEntry);
                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                } catch (JSONException e) {
                    return Response.error(new ParseError(e));
                }

            }

            @Override
            protected void deliverResponse(JSONArray response) {
                super.deliverResponse(response);
            }


            @Override
            protected VolleyError parseNetworkError(VolleyError volleyError) {
                return super.parseNetworkError(volleyError);
            }

            @Override
            public void deliverError(VolleyError error) {
                super.deliverError(error);
            }


        };
        RequestQueueContainer.getInstance(context).add(jsonArrayRequest);
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }
    public interface getApiCallBack{
        public void Success(List<NamadsDataSample> namadsDataSampleList);
        public void Error();

    }
}

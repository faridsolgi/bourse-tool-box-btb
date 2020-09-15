package com.glorysys.boursetoolbox.main.WebServer;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.glorysys.boursetoolbox.main.Models.AparatDataModel;
import com.glorysys.boursetoolbox.main.RequestQueueContainer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class AparatWepApi {
    private Context context;
    private int i,counter=0;
    private static final String TAG = "testAparat";
    private List<AparatDataModel> finallist;
    public AparatWepApi(Context context) {
        this.context = context;
    }

    public void getVideos(final int page, final getVideoFromAparat getVideoFromAparat) {

        String baseUrl = "https://www.aparat.com/etc/api/videobysearch/text/%D8%A2%D9%85%D9%88%D8%B2%D8%B4%20%D8%A8%D9%88%D8%B1%D8%B3/perpage/100/curoffset/";
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, baseUrl+page, null
                    , new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try {
                        JSONArray jsonArray = response.getJSONArray("videobysearch");
                        List<AparatDataModel> aparatDataModelList = new ArrayList<>();
                        for (int j = 0; j < jsonArray.length(); j++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(j);
                            AparatDataModel aparatDataModel = new AparatDataModel();
                            aparatDataModel.setTitle(jsonObject.getString("title"));
                            aparatDataModel.setVisit_cnt(jsonObject.getInt("visit_cnt"));
                            aparatDataModel.setDate(jsonObject.getString("sdate"));
                            aparatDataModel.setDuration(jsonObject.getInt("duration"));
                            aparatDataModel.setPoster(jsonObject.getString("small_poster"));
                            aparatDataModel.setUrl(jsonObject.getString("frame"));
                            aparatDataModelList.add(aparatDataModel);
                        }
                        getVideoFromAparat.AparatVideo_success(aparatDataModelList,page);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }


            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    getVideoFromAparat.AparatVideo_Error();
                }
            }) {
                @Override
                protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {


                    try {
                        Cache.Entry cacheEntry = HttpHeaderParser.parseCacheHeaders(response);
                        if (cacheEntry == null) {
                            cacheEntry = new Cache.Entry();
                        }
                        final long cacheHitButRefreshed = 3 * 60 * 1000; // in 3 minutes cache will be hit, but also refreshed on background
                        final long cacheExpired = 10 * 60 * 60 * 1000; // in 1 hours this cache entry expires completely
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
                        return Response.success(new JSONObject(jsonString), cacheEntry);
                    } catch (UnsupportedEncodingException e) {
                        return Response.error(new ParseError(e));
                    } catch (JSONException e) {
                        return Response.error(new ParseError(e));
                    }

                }

                @Override
                protected void deliverResponse(JSONObject response) {
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

            RequestQueueContainer.getInstance(context).add(jsonObjectRequest);
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                    10000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


    }

    public interface getVideoFromAparat {
        void AparatVideo_success(List<AparatDataModel> aparatDataModelList,int page);

        void AparatVideo_Error();

    }


}

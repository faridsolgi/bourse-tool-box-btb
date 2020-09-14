package com.glorysys.boursetoolbox.main.WebServer;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.glorysys.boursetoolbox.main.Models.NewsDataModel;
import com.glorysys.boursetoolbox.main.RequestQueueContainer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class NewsWebServer {
    private static final String TAG = "NewsWebServer";
    private static String URL="http://glorysys.ir/api/v1/getnews";
    private Context context;
    public NewsWebServer(Context context) {
        this.context=context;
    }

    public void getnews(final getNewsInterface getNewsInterface){
        JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                List<NewsDataModel> newsDataModelList = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    NewsDataModel newsDataModel = new NewsDataModel();
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        newsDataModel.setTitle(jsonObject.getString("title"));
                        newsDataModel.setDescription(jsonObject.getString("description"));
                        newsDataModel.setUrl(jsonObject.getString("link"));
                        newsDataModel.setImgurl(jsonObject.getString("imgurl"));
                        newsDataModelList.add(newsDataModel);
                        Log.d(TAG, "onResponse: "+i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d(TAG, "oncatch: "+i);
                    }

                }
                Log.d(TAG, "oninter: "+newsDataModelList);
                getNewsInterface.getNewsSuccessful(newsDataModelList);
            }

        },
            new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getNewsInterface.getNewsError();
            }
        });
        RequestQueueContainer.getInstance(context).add(jsonArrayRequest);

    }

    public interface getNewsInterface{
        void getNewsSuccessful(List<NewsDataModel> newsDataModelList);
        void getNewsError();


    }
}

package com.cdhxqh.travel_ticket_app.api;

import android.content.Context;
import android.util.Log;

import com.cdhxqh.travel_ticket_app.config.Constants;
import com.cdhxqh.travel_ticket_app.model.PersistenceHelper;
import com.cdhxqh.travel_ticket_app.model.Zw_Model;
import com.cdhxqh.travel_ticket_app.utils.SafeHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by yw on 2015/5/2.
 */
class WrappedJsonHttpResponseHandler<T extends Zw_Model> extends JsonHttpResponseHandler {
    private static final String TAG = "WrappedJsonHttpResponseHandler";
    HttpRequestHandler<ArrayList<T>> handler;
    Class c;
    Context context;
    String key;

    public WrappedJsonHttpResponseHandler(Context cxt, Class c, String key,
                                          HttpRequestHandler<ArrayList<T>> handler) {
        this.handler = handler;
        this.c = c;
        this.context = cxt;
        this.key = key;
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        Log.i(TAG, "response123=" + response);
        String errcode = null;
        String errmsg=null;
        ArrayList<T> models = new ArrayList<T>();

        try {
            errcode = response.getString("errcode");
            Log.i(TAG,"errcode="+errcode);
            if (errcode.equals(Constants.REQUEST_SUCCESS)) {
                Log.i(TAG,"1");
                T obj = (T) Class.forName(c.getName()).newInstance();
                obj.parse(response);
                Log.i(TAG,"2");
                if (obj != null) {
                    models.add(obj);
                }
                Log.i(TAG,"3");
                PersistenceHelper.saveModelList(context, models, key);
                SafeHandler.onSuccess(handler, models);
            } else {
                SafeHandler.onFailure(handler, errmsg);


            }


        } catch (JSONException e) {
            Log.i(TAG,"errcode="+e);
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            Log.i(TAG,"errcode="+e);
            e.printStackTrace();
        } catch (InstantiationException e) {
            Log.i(TAG,"errcode="+e);
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            Log.i(TAG,"errcode="+e);
            e.printStackTrace();
        }


    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
        Log.i(TAG, "response=" + response);
        ArrayList<T> models = new ArrayList<T>();
        Log.i(TAG, "response size=" + response.length());
        for (int i = 0; i < response.length(); i++) {
            try {
                JSONObject jsonObj = response.getJSONObject(i);
                Log.i(TAG, "jsonObj=" + jsonObj);
                T obj = (T) Class.forName(c.getName()).newInstance();
                obj.parse(jsonObj);
                if (obj != null)
                    models.add(obj);
            } catch (Exception e) {
            }
        }
        PersistenceHelper.saveModelList(context, models, key);
        SafeHandler.onSuccess(handler, models);
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, String responseBody, Throwable e) {
        handleFailure(statusCode, e.getMessage());
    }

    public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject errorResponse) {
        handleFailure(statusCode, e.getMessage());
    }

    public void onFailure(int statusCode, Header[] headers, Throwable e, JSONArray errorResponse) {
        handleFailure(statusCode, e.getMessage());
    }

    private void handleFailure(int statusCode, String error) {
        error = ErrorType.errorMessage(context, ErrorType.ErrorApiForbidden);
        SafeHandler.onFailure(handler, error);
    }
}


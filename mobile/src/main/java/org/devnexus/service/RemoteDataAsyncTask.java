package org.devnexus.service;

import android.os.AsyncTask;
import android.util.Log;

import org.devnexus.handler.JsonHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RemoteDataAsyncTask<T> extends AsyncTask<Void, Void, List<T>> {

    private static final String TAG = RemoteDataAsyncTask.class.getName();

    private String url;
    private JsonHandler<T> handler;
    private Callback<T> callback;

    private Exception error;

    public RemoteDataAsyncTask<T> withUrl(String url) {
        this.url = url;
        return this;
    }

    public RemoteDataAsyncTask<T> withHandler(JsonHandler<T> handler) {
        this.handler = handler;
        return this;
    }

    public RemoteDataAsyncTask<T> withCallback(Callback<T> callback) {
        this.callback = callback;
        return this;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        callback.onPreExecute();
    }

    @Override
    protected List<T> doInBackground(Void... voids) {

        try {
            String response = new RemoteDataService(url).getData();
            return handler.parse(response);
        } catch (IOException e) {
            error = e;
            Log.e(TAG, e.getMessage(), e);
            return new ArrayList<>();
        }

    }

    @Override
    protected void onPostExecute(List<T> data) {
        super.onPostExecute(data);

        callback.onFinish();

        if(error != null) {
            callback.onFailure(error);
        } else {
            callback.onSuccess(data);
        }
    }

}

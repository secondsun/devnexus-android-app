package org.devnexus.service;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Read data from an URL
 */
public class RemoteDataService extends DataService {

    private final String url;

    /**
     * Default constructor
     * @param url URL of the data will be loaded
     */
    public RemoteDataService(String url) {
        this.url = url;
    }

    /**
     * Load data from the URL
     *
     * @return Response string
     */
    public String getData() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response responses = client.newCall(request).execute();
        return responses.body().string();
    }

}

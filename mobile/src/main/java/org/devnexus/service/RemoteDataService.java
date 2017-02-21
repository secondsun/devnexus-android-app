package org.devnexus.service;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RemoteDataService extends DataService {

    private final String url;

    public RemoteDataService(String url) {
        this.url = url;
    }

    /**
     * Request data from the url
     *
     * @return Response
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

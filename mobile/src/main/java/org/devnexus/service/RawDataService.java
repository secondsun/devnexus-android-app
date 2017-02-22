package org.devnexus.service;

import android.content.Context;
import android.support.annotation.RawRes;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Read file from a raw file
 */
public class RawDataService extends DataService {

    private Context context;
    private
    @RawRes
    int resId;

    /**
     * Default constructor
     *
     * @param context Application context
     * @param resId Raw file
     */
    public RawDataService(Context context, @RawRes int resId) {
        this.context = context;
        this.resId = resId;
    }

    /**
     * Read the raw file from system
     *
     * @return String of the file content
     * @throws IOException
     */
    @Override
    public String getData() throws IOException {
        InputStream inputStream = context.getResources().openRawResource(resId);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        int ctr = inputStream.read();
        while (ctr != -1) {
            byteArrayOutputStream.write(ctr);
            ctr = inputStream.read();
        }
        inputStream.close();

        return byteArrayOutputStream.toString();
    }

}

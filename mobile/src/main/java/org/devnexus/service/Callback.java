package org.devnexus.service;

import java.io.Serializable;
import java.util.List;

public interface Callback<T> extends Serializable {

    void onPreExecute();

    void onFinish();

    void onSuccess(List<T> data);

    void onFailure(Exception e);

}

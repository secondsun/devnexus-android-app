package org.devnexus.handler;

import java.util.List;

public abstract class JsonHandler<T> {

    public abstract List<T> parse(String response);

}

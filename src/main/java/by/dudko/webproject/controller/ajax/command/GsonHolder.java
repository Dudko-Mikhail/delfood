package by.dudko.webproject.controller.ajax.command;

import com.google.gson.Gson;

public final class GsonHolder { // TODO add configuration if need it
    private static final Gson gson = new Gson();

    public static Gson getGson() {
        return gson;
    }

    private GsonHolder() {
    }
}

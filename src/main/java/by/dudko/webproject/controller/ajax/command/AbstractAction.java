package by.dudko.webproject.controller.ajax.command;

import com.google.gson.Gson;

public abstract class AbstractAction implements ActionCommand {
    protected final Gson gson = GsonHolder.getGson();
}

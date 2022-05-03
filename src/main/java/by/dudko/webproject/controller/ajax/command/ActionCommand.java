package by.dudko.webproject.controller.ajax.command;

import by.dudko.webproject.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface ActionCommand {
    void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, CommandException;
}

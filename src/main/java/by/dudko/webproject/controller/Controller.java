package by.dudko.webproject.controller;

import by.dudko.webproject.controller.command.Command;
import by.dudko.webproject.controller.command.CommandType;
import by.dudko.webproject.exception.CommandException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "mainServlet", urlPatterns = {"/controller"})
public class Controller extends HttpServlet {
    private static final Logger logger = LogManager.getLogger();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String commandText = request.getParameter(RequestParameter.COMMAND);
        Optional<Command> command = CommandType.parseCommand(commandText);
        if (command.isEmpty()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        try {
            Router router = command.get().execute(request);
            String pagePath = router.getPagePath();
            if (router.getRouteType() == Router.RouteType.FORWARD) {
                request.getRequestDispatcher(pagePath).forward(request, response);
            } else {
                pagePath = addContextPath(request, router.getPagePath());
                response.sendRedirect(pagePath);
            }
        } catch (CommandException e) {
            logger.error("Exception during command execution", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private String addContextPath(HttpServletRequest request, String url) {
        String context = request.getContextPath();
        return String.format("%s/%s", context, url);
    }
}
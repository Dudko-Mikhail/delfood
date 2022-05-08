package by.dudko.webproject.controller;

import by.dudko.webproject.controller.command.Command;
import by.dudko.webproject.controller.command.CommandProvider;
import by.dudko.webproject.controller.command.CommandType;
import by.dudko.webproject.exception.CommandException;
import by.dudko.webproject.util.PathHelper;
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String commandName = request.getParameter(RequestParameter.COMMAND);
        Optional<CommandType> commandTypeOptional = CommandProvider.parseCommand(commandName);
        if (commandTypeOptional.isEmpty()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        try {
            CommandType command = commandTypeOptional.get();
            Router router = command.execute(request);
            String pagePath = router.getPagePath();
            switch (router.getRouteType()) {
                case REDIRECT -> {
                    pagePath = PathHelper.addContextPath(request, router.getPagePath());
                    response.sendRedirect(pagePath);
                }
                case FORWARD -> request.getRequestDispatcher(pagePath).forward(request, response);
                case ERROR -> response.sendError(router.getErrorCode());
                default -> response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } catch (CommandException e) {
            logger.error("Exception during command execution", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
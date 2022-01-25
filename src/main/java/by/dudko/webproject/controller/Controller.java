package by.dudko.webproject.controller;

import by.dudko.webproject.controller.command.Command;
import by.dudko.webproject.controller.command.CommandType;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "mainServlet", urlPatterns = {"/controller"})
public class Controller extends HttpServlet {

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
        if (command.isPresent()) {
           Router router = command.get().execute(request);
           String pagePath = router.getPagePath();
           if (router.getRouteType() == Router.RouteType.FORWARD) {
               request.getRequestDispatcher(pagePath).forward(request, response);
           }
           else {
               response.sendRedirect(pagePath);
           }
        }
        else { // todo Что делать, если нет команды
            var session = request.getSession();
            response.sendRedirect((String) session.getAttribute(SessionAttributes.PAGE));
        }
    }

}
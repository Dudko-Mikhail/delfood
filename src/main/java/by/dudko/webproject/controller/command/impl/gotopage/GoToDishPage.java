package by.dudko.webproject.controller.command.impl.gotopage;

import by.dudko.webproject.controller.PagePath;
import by.dudko.webproject.controller.RequestParameter;
import by.dudko.webproject.controller.Router;
import by.dudko.webproject.controller.SessionAttribute;
import by.dudko.webproject.controller.command.Command;
import by.dudko.webproject.controller.command.RequestAttribute;
import by.dudko.webproject.exception.CommandException;
import by.dudko.webproject.exception.ServiceException;
import by.dudko.webproject.model.entity.Dish;
import by.dudko.webproject.model.entity.Language;
import by.dudko.webproject.model.service.DishService;
import by.dudko.webproject.model.service.impl.DishServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class GoToDishPage implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException { // TODO what is better??? 1, 2, 3
        HttpSession session = request.getSession();
        Router router;
        try {
            int dishId = Integer.parseInt(request.getParameter(RequestParameter.DISH_ID));
            Language sessionLanguage = (Language) session.getAttribute(SessionAttribute.LANGUAGE);
            DishService dishService = DishServiceImpl.getInstance();
            Optional<Dish> dish = dishService.findDishByIdAndLanguage(dishId, sessionLanguage);
            if (dish.isEmpty()) {
                String message = String.format("Failed to find translation for dish with id %d into %s language",
                        dishId, sessionLanguage.getName());
                logger.error(message);
                return new Router(HttpServletResponse.SC_NOT_FOUND);
            }
            request.setAttribute(RequestAttribute.DISH, dish.get());
            router = new Router(Router.RouteType.FORWARD, PagePath.DISH_PAGE);
        } catch (NumberFormatException e) {
            logger.warn("Attempt to execute GoToDishPage command with invalid dish id");
            String currentPage = (String) session.getAttribute(SessionAttribute.PAGE);
            router = new Router(Router.RouteType.REDIRECT, currentPage);
        } catch (ServiceException e) {
            throw new CommandException("Failed to execute GoToDishPage command", e);
        }
        return router;
    }

    public Router execute2(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        Router router;
        try {
            int dishId = Integer.parseInt(request.getParameter(RequestParameter.DISH_ID));
            Language sessionLanguage = (Language) session.getAttribute(SessionAttribute.LANGUAGE);
            DishService dishService = DishServiceImpl.getInstance();
            Optional<Dish> dish = dishService.findDishByIdAndLanguage(dishId, sessionLanguage);
            if (dish.isPresent()) {
                request.setAttribute(RequestAttribute.DISH, dish.get());
                router = new Router(Router.RouteType.FORWARD, PagePath.DISH_PAGE);
            } else {
                String message = String.format("Failed to find translation for dish with id %d into %s language",
                        dishId, sessionLanguage.getName());
                logger.error(message);
                router = new Router(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (NumberFormatException e) {
            logger.warn("Attempt to execute GoToDishPage command with invalid dish id");
            String currentPage = (String) session.getAttribute(SessionAttribute.PAGE);
            router = new Router(Router.RouteType.REDIRECT, currentPage);
        } catch (ServiceException e) {
            throw new CommandException("Failed to execute GoToDishPage command", e);
        }
        return router;
    }

    public Router execute3(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        try {
            int dishId = Integer.parseInt(request.getParameter(RequestParameter.DISH_ID));
            Language sessionLanguage = (Language) session.getAttribute(SessionAttribute.LANGUAGE);
            DishService dishService = DishServiceImpl.getInstance();
            Optional<Dish> dish = dishService.findDishByIdAndLanguage(dishId, sessionLanguage);
            if (dish.isEmpty()) {
                String message = String.format("Failed to find translation for dish with id %d into %s language",
                        dishId, sessionLanguage.getName());
                logger.error(message);
                return new Router(HttpServletResponse.SC_NOT_FOUND);
            }
            request.setAttribute(RequestAttribute.DISH, dish.get());
            return  new Router(Router.RouteType.FORWARD, PagePath.DISH_PAGE);
        } catch (NumberFormatException e) {
            logger.warn("Attempt to execute GoToDishPage command with invalid dish id");
            String currentPage = (String) session.getAttribute(SessionAttribute.PAGE);
            return new Router(Router.RouteType.REDIRECT, currentPage);
        } catch (ServiceException e) {
            throw new CommandException("Failed to execute GoToDishPage command", e);
        }
    }
}
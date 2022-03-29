package by.dudko.webproject.controller.command.impl.gotopage;

import by.dudko.webproject.controller.PagePath;
import by.dudko.webproject.controller.RequestParameter;
import by.dudko.webproject.controller.Router;
import by.dudko.webproject.controller.SessionAttribute;
import by.dudko.webproject.controller.command.Command;
import by.dudko.webproject.controller.RequestAttribute;
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
    private static final DishService dishService = DishServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        Router router;
        try {
            int dishId = Integer.parseInt(request.getParameter(RequestParameter.DISH_ID));
            Language sessionLanguage = (Language) session.getAttribute(SessionAttribute.LANGUAGE);
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
            router = new Router(HttpServletResponse.SC_NOT_FOUND);
        } catch (ServiceException e) {
            throw new CommandException("Failed to execute GoToDishPage command", e);
        }
        return router;
    }
}
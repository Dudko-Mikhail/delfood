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
import by.dudko.webproject.model.service.DishCategoryService;
import by.dudko.webproject.model.service.DishService;
import by.dudko.webproject.model.service.impl.DishCategoryServiceImpl;
import by.dudko.webproject.model.service.impl.DishServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class GoToCategoryPage implements Command {
    private static final Logger logger = LogManager.getLogger();
    private final DishService dishService = DishServiceImpl.getInstance();
    private final DishCategoryService categoryService = DishCategoryServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        Router router;
        try {
            int categoryId = Integer.parseInt(request.getParameter(RequestParameter.CATEGORY_ID));
            Language sessionLanguage = (Language) session.getAttribute(SessionAttribute.LANGUAGE);
            Optional<String> categoryName = categoryService.findCategoryNameByIdAndLanguage(categoryId, sessionLanguage);
            if (categoryName.isEmpty()) {
                String message = String.format("Failed to find translation for category with id %d into %s language",
                        categoryId, sessionLanguage.getName());
                logger.error(message);
                return new Router(HttpServletResponse.SC_NOT_FOUND);
            }
            request.setAttribute(RequestAttribute.CATEGORY_NAME, categoryName.get());

            List<Dish> dishes = dishService.findDishesByCategoryIdAndLanguage(categoryId, sessionLanguage);
            request.setAttribute(RequestAttribute.DISHES, dishes);
            router = new Router(Router.RouteType.FORWARD, PagePath.CATEGORY_PAGE);
        } catch (NumberFormatException e) { // FIXME после redirect страница становиться пустой (нет ни имени категории, ни товаров)
            logger.warn("Attempt to execute GoToCategoryPage command with invalid category id");
            String currentPage = (String) session.getAttribute(SessionAttribute.PAGE);
            router = new Router(Router.RouteType.REDIRECT, currentPage);
        } catch (ServiceException e) {
            throw new CommandException("Failed to execute GoToCategoryPage command", e);
        }
        return router;
    }
}
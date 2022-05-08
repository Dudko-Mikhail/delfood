package by.dudko.webproject.controller.command.impl.gotopage;

import by.dudko.webproject.controller.PagePath;
import by.dudko.webproject.controller.RequestParameter;
import by.dudko.webproject.controller.Router;
import by.dudko.webproject.controller.SessionAttribute;
import by.dudko.webproject.controller.RequestAttribute;
import by.dudko.webproject.controller.command.RoutingCommand;
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

public class GoToCategoryPage implements RoutingCommand {
    private static final Logger logger = LogManager.getLogger();
    private static final String CATEGORY_NOT_FOUND = "Category not found";
    private static final DishService dishService = DishServiceImpl.getInstance();
    private static final DishCategoryService categoryService = DishCategoryServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException { // TODO refactor and change messages
        HttpSession session = request.getSession();
        try {
            int categoryId = Integer.parseInt(request.getParameter(RequestParameter.CATEGORY_ID));
            Language language = (Language) session.getAttribute(SessionAttribute.LANGUAGE);
            Optional<String> categoryName = categoryService.findCategoryNameByIdAndLanguageId(categoryId,
                    language.getId());
            if (categoryName.isEmpty()) {
                String message = String.format("Failed to find translation for category with id %d into %s language",
                        categoryId, language.getName());
                logger.warn(message);
                request.setAttribute(RequestAttribute.ERROR_MESSAGE, CATEGORY_NOT_FOUND);
                return new Router(HttpServletResponse.SC_NOT_FOUND);
            } else {
                request.setAttribute(RequestAttribute.CATEGORY_NAME, categoryName.get());
                List<Dish> dishes = dishService.findDishesByCategoryIdAndLanguageId(categoryId, language.getId());
                request.setAttribute(RequestAttribute.DISHES, dishes);
            }
        } catch (NumberFormatException e) {
            logger.warn("Attempt to execute GoToCategoryPage command with invalid (not numeric) category id");
            request.setAttribute(RequestAttribute.ERROR_MESSAGE, CATEGORY_NOT_FOUND);
            return new Router(HttpServletResponse.SC_NOT_FOUND);
        } catch (ServiceException e) {
            throw new CommandException("Failed to execute GoToCategoryPage command", e);
        }
        return new Router(Router.RouteType.FORWARD, PagePath.CATEGORY_PAGE);
    }
}
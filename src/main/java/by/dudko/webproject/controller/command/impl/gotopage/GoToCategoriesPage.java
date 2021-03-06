package by.dudko.webproject.controller.command.impl.gotopage;

import by.dudko.webproject.controller.PagePath;
import by.dudko.webproject.controller.Router;
import by.dudko.webproject.controller.SessionAttribute;
import by.dudko.webproject.controller.RequestAttribute;
import by.dudko.webproject.controller.command.RoutingCommand;
import by.dudko.webproject.exception.CommandException;
import by.dudko.webproject.exception.ServiceException;
import by.dudko.webproject.model.entity.DishCategory;
import by.dudko.webproject.model.entity.Language;
import by.dudko.webproject.model.service.DishCategoryService;
import by.dudko.webproject.model.service.impl.DishCategoryServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public class GoToCategoriesPage implements RoutingCommand {
    private static final DishCategoryService dishCategoryService = DishCategoryServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        Language language = (Language) session.getAttribute(SessionAttribute.LANGUAGE);
        try {
            List<DishCategory> categories = dishCategoryService.findAllCategoriesByLanguageId(language.getId());
            request.setAttribute(RequestAttribute.DISH_CATEGORIES, categories);
            return new Router(Router.RouteType.FORWARD, PagePath.CATEGORIES_PAGE);
        } catch (ServiceException e) {
            throw new CommandException("Failed to execute GoToCategoriesPage command", e);
        }
    }
}

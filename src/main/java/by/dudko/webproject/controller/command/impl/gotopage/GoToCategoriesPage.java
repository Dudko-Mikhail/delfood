package by.dudko.webproject.controller.command.impl.gotopage;

import by.dudko.webproject.controller.PagePath;
import by.dudko.webproject.controller.Router;
import by.dudko.webproject.controller.SessionAttribute;
import by.dudko.webproject.controller.command.Command;
import by.dudko.webproject.controller.command.RequestAttribute;
import by.dudko.webproject.exception.CommandException;
import by.dudko.webproject.exception.ServiceException;
import by.dudko.webproject.model.entity.DishCategory;
import by.dudko.webproject.model.entity.Language;
import by.dudko.webproject.model.service.impl.DishCategoryServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public class GoToCategoriesPage implements Command { // TODO refactor
    private static final DishCategoryServiceImpl dishCategoryService = DishCategoryServiceImpl.getInstance();
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        Language language = (Language) session.getAttribute(SessionAttribute.LANGUAGE);
        try {
            List<DishCategory> categories = dishCategoryService.findAllCategoriesByLanguage(language);
            request.setAttribute(RequestAttribute.DISH_CATEGORIES, categories);
            return new Router(Router.RouteType.REDIRECT, PagePath.CATEGORIES_PAGE);
        } catch (ServiceException e) {
            throw new CommandException("Failed to execute GoToCategoriesPage command", e);
        }

    }
}

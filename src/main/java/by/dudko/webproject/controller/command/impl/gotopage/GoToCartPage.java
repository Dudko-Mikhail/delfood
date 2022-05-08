package by.dudko.webproject.controller.command.impl.gotopage;

import by.dudko.webproject.controller.PagePath;
import by.dudko.webproject.controller.RequestAttribute;
import by.dudko.webproject.controller.Router;
import by.dudko.webproject.controller.SessionAttribute;
import by.dudko.webproject.controller.command.RoutingCommand;
import by.dudko.webproject.exception.CommandException;
import by.dudko.webproject.exception.ServiceException;
import by.dudko.webproject.model.entity.Dish;
import by.dudko.webproject.model.entity.Language;
import by.dudko.webproject.model.entity.OrderManager;
import by.dudko.webproject.model.service.DishService;
import by.dudko.webproject.model.service.impl.DishServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Set;

public class GoToCartPage implements RoutingCommand {
    private static final DishService dishService = DishServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        Language language = (Language) session.getAttribute(SessionAttribute.LANGUAGE);
        OrderManager manager = (OrderManager) session.getAttribute(SessionAttribute.ORDER_MANAGER);
        Set<Integer> itemsIds = manager.getItemsIdSet();
        try {
            List<Dish> orderDishes = dishService.findDishedByIdSetAndLanguageId(itemsIds, language.getId());
            request.setAttribute(RequestAttribute.DISHES, orderDishes);
        } catch (ServiceException e) {
            throw new CommandException("Failed to execute goToCartPage command");
        }
        return new Router(Router.RouteType.FORWARD, PagePath.CART_PAGE);
    }
}

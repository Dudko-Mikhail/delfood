package by.dudko.webproject.controller.command.impl;

import by.dudko.webproject.controller.PagePath;
import by.dudko.webproject.controller.RequestParameter;
import by.dudko.webproject.controller.Router;
import by.dudko.webproject.controller.SessionAttribute;
import by.dudko.webproject.controller.command.Command;
import by.dudko.webproject.exception.CommandException;
import by.dudko.webproject.exception.ServiceException;
import by.dudko.webproject.model.entity.Dish;
import by.dudko.webproject.model.entity.Language;
import by.dudko.webproject.model.entity.OrderItem;
import by.dudko.webproject.model.service.DishService;
import by.dudko.webproject.model.service.impl.DishServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;

public class AddOrderItemCommand implements Command {
    private static final DishService dishService = DishServiceImpl.getInstance();

    public Map<Integer, OrderItem> extractOrInitializeOrderItems(HttpSession session) {
        Map<Integer, OrderItem> orderItems = (Map<Integer, OrderItem>) session.getAttribute(SessionAttribute.ORDER_ITEMS);
        return orderItems != null ? orderItems : new HashMap<>();
    }

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        Map<Integer, OrderItem> orderItems = extractOrInitializeOrderItems(session);
        session.setAttribute(SessionAttribute.ORDER_ITEMS, orderItems);
        int amount = Integer.parseInt(request.getParameter(RequestParameter.AMOUNT));
        int dishId = Integer.parseInt(request.getParameter(RequestParameter.DISH_ID));
        Language language = (Language) session.getAttribute(SessionAttribute.LANGUAGE);
        OrderItem item = orderItems.get(dishId);
        if (item != null) {
            item.increaseAmount(amount);
        } else {
            try {
                Dish dish = findDish(dishId, language);
                orderItems.put(dishId, new OrderItem(dish, amount));
            } catch (ServiceException e) {
                throw new CommandException("Failed to execute addOrderItem command");
            }
        }
        return new Router(Router.RouteType.REDIRECT, request.getHeader("referer")); // FIXME
    }

    private Dish findDish(int dishId, Language language) throws ServiceException {
        return dishService.findDishByIdAndLanguage(dishId, language)
                .orElseThrow(() -> new ServiceException(
                        String.format("Cannot find dish with id %d and language %s", dishId, language)
                ));
    }
}

package by.dudko.webproject.controller.ajax.command.impl;

import by.dudko.webproject.controller.RequestParameter;
import by.dudko.webproject.controller.SessionAttribute;
import by.dudko.webproject.controller.ajax.ResponseParameters;
import by.dudko.webproject.controller.ajax.command.AbstractAction;
import by.dudko.webproject.controller.ajax.command.AjaxCommandType;
import by.dudko.webproject.exception.CommandException;
import by.dudko.webproject.exception.ServiceException;
import by.dudko.webproject.model.entity.Dish;
import by.dudko.webproject.model.entity.Language;
import by.dudko.webproject.model.entity.OrderItem;
import by.dudko.webproject.model.entity.OrderManager;
import by.dudko.webproject.model.service.DishService;
import by.dudko.webproject.model.service.impl.DishServiceImpl;
import by.dudko.webproject.util.PriceFormatter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ChangeOrderItemQuantityCommand extends AbstractAction {// TODO what should i do with NFE
    private static final DishService dishService = DishServiceImpl.getInstance();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, IOException {
        HttpSession session = request.getSession();
        Language language = (Language) session.getAttribute(SessionAttribute.LANGUAGE);
        int value = Integer.parseInt(request.getParameter(RequestParameter.VALUE));
        int itemId = Integer.parseInt(request.getParameter(RequestParameter.ITEM_ID));
        OrderManager manager = (OrderManager) session.getAttribute(SessionAttribute.ORDER_MANAGER);

        Optional<OrderItem> itemOptional = manager.getOrderItemById(itemId);
        if (itemOptional.isPresent()) {
            OrderItem item = itemOptional.get();
            if (manager.willBeRemoved(item, value)) {
                AjaxCommandType.REMOVE_ORDER_ITEM.execute(request, response);
            } else {
                manager.changeOrderItemQuantity(itemId, value);
                sendResponse(manager, item.getId(), language, response);
            }
        } else if (value > 0) {
            try {
                OrderItem item = createOrderItem(itemId, value, language);
                manager.addOrderItem(item);
                sendResponse(manager, item.getId(), language, response);
            } catch (ServiceException e) {
                throw new CommandException("Failed to create new order item", e);
            }
        }
    }

    private OrderItem createOrderItem(int dishId, int amount, Language language) throws ServiceException {
        Dish dish = findDish(dishId, language);
        return new OrderItem(dish, amount);
    }

    private Dish findDish(int dishId, Language language) throws ServiceException {
        return dishService.findDishByIdAndLanguageId(dishId, language.getId())
                .orElseThrow(() -> new ServiceException(
                        String.format("Cannot find dish with id %d and language %s", dishId, language)
                ));
    }

    private void sendResponse(OrderManager manager, int itemId,
                              Language language, HttpServletResponse response) throws IOException {
        Map<String, String> responseData = prepareResponseData(manager, itemId, language);
        response.setContentType("application/json");
        var writer = response.getWriter();
        String json = gson.toJson(responseData);
        writer.write(json);
    }

    private Map<String, String> prepareResponseData(OrderManager manager, int itemId, Language language) {
        Map<String, String> data = new HashMap<>();
        OrderItem item = manager.getOrderItemById(itemId).get();
        data.put(ResponseParameters.CART_PRICE, PriceFormatter.formatPrice(manager.getPrice(), language));
        data.put(ResponseParameters.ITEM_PRICE, PriceFormatter.formatPrice(item.getDiscountedPrice(), language));
        data.put(ResponseParameters.TOTAL_DISCOUNT, PriceFormatter.formatPrice(manager.getTotalDiscount(), language));
        return data;
    }
}

package by.dudko.webproject.controller.ajax.command.impl;

import by.dudko.webproject.controller.RequestParameter;
import by.dudko.webproject.controller.SessionAttribute;
import by.dudko.webproject.controller.ajax.ResponseParameters;
import by.dudko.webproject.controller.ajax.command.AbstractAction;
import by.dudko.webproject.exception.CommandException;
import by.dudko.webproject.model.entity.Language;
import by.dudko.webproject.model.entity.OrderManager;
import by.dudko.webproject.util.PriceFormatter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RemoveOrderItemCommand extends AbstractAction {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, IOException {
        HttpSession session = request.getSession();
        try {
            int itemId = Integer.parseInt(request.getParameter(RequestParameter.ITEM_ID));
            Language language = (Language) session.getAttribute(SessionAttribute.LANGUAGE);
            OrderManager manager = (OrderManager) session.getAttribute(SessionAttribute.ORDER_MANAGER);
            manager.removeOrderItem(itemId);
            sendResponse(manager, language, response);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private void sendResponse(OrderManager manager, Language language, HttpServletResponse response) throws IOException {
        Map<String, String> responseData = prepareResponseData(manager, language);
        String json = gson.toJson(responseData);
        response.setContentType("application/json");
        response.getWriter().write(json);
    }

    private Map<String, String> prepareResponseData(OrderManager manager, Language language) {
        Map<String, String> data = new HashMap<>();
        data.put(ResponseParameters.CART_PRICE, PriceFormatter.formatPrice(manager.getPrice(), language));
        data.put(ResponseParameters.TOTAL_DISCOUNT, PriceFormatter.formatPrice(manager.getTotalDiscount(), language));
        return data;
    }
}

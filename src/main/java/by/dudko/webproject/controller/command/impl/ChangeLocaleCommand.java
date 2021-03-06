package by.dudko.webproject.controller.command.impl;

import by.dudko.webproject.controller.RequestParameter;
import by.dudko.webproject.controller.SessionAttribute;
import by.dudko.webproject.controller.command.Command;
import by.dudko.webproject.controller.Router;
import by.dudko.webproject.model.entity.Language;
import by.dudko.webproject.util.i18n.LanguageProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class ChangeLocaleCommand implements Command {
    private static final LanguageProvider languageProvider = LanguageProvider.getInstance();

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String languageParameter = request.getParameter(RequestParameter.LANGUAGE);
        Language newLanguage = languageProvider.parseLanguage(languageParameter);
        session.setAttribute(SessionAttribute.LANGUAGE, newLanguage);
        String page = (String) session.getAttribute(SessionAttribute.PAGE);
        return new Router(Router.RouteType.REDIRECT, page);
    }
}

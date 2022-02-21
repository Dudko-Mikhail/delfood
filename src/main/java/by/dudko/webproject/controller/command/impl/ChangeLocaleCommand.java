package by.dudko.webproject.controller.command.impl;

import by.dudko.webproject.controller.RequestParameter;
import by.dudko.webproject.controller.SessionAttribute;
import by.dudko.webproject.controller.command.Command;
import by.dudko.webproject.controller.Router;
import by.dudko.webproject.model.entity.Language;
import by.dudko.webproject.util.LanguageProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class ChangeLocale implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String languageParameter = request.getParameter(RequestParameter.LANGUAGE);
        Language newLanguage = LanguageProvider.getInstance().parseLanguage(languageParameter);
        session.setAttribute(SessionAttribute.LANGUAGE, newLanguage);

        String referer = request.getHeader("referer");
        return new Router(Router.RouteType.REDIRECT, referer);
    }
}

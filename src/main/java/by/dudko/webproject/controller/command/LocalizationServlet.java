package by.dudko.webproject.controller.command;

import by.dudko.webproject.controller.SessionAttribute;
import by.dudko.webproject.model.entity.Language;
import by.dudko.webproject.util.LanguageProvider;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

@WebServlet(name = "localizationServlet", value = "/localization")
public class LocalizationServlet extends HttpServlet { // TODO use or remove
    private static final String BUNDLE_NAME = "localization/localized_data";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Language sessionLanguage;
        try {
            sessionLanguage = (Language) session.getAttribute(SessionAttribute.LANGUAGE);
        } catch (Exception e) {
            sessionLanguage = LanguageProvider.DEFAULT_LANGUAGE;
        }

        ResourceBundle bundle = findBundle(sessionLanguage);
        String messageKey = request.getParameter("key");
        String message;
        try {
            message = bundle.getString(messageKey);
        } catch (MissingResourceException e) {
            message = "";
        }
        response.setContentType("text/plain");
        PrintWriter writer = response.getWriter();
        writer.write(message);
    }

    private ResourceBundle findBundle(Language language) {
        String languageTag = language.getName().replace("_", "-");
        Locale locale = Locale.forLanguageTag(languageTag);
        return ResourceBundle.getBundle(BUNDLE_NAME, locale);
    }
}

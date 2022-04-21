package by.dudko.webproject.util;

import java.util.Map;

public class UrlRequestBuilder {
    private static final String PARAMETER_SEPARATOR = "&";
    private static final String REQUEST_START_SYMBOL = "?";

    private UrlRequestBuilder() {
    }

    public static String buildRequestString(String initialUrl, Map<String, String> requestParameters) {
        StringBuilder requestString = new StringBuilder(initialUrl);
        int parametersCount = requestParameters.size();
        int parameterIndex = 1;
        requestString.append(REQUEST_START_SYMBOL);
        for (Map.Entry<String, String> parameter : requestParameters.entrySet()) {
            appendRequestParameter(requestString, parameter);
            if (parameterIndex < parametersCount) {
                requestString.append(PARAMETER_SEPARATOR);
            }
            parameterIndex++;
        }
        return requestString.toString();
    }

    private static void  appendRequestParameter(StringBuilder requestString, Map.Entry<String, String> parameter) {
        requestString.append(parameter.getKey())
                .append("=")
                .append(parameter.getValue());
    }

}

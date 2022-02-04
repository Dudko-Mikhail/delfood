package by.dudko.webproject.controller;

public class Router {
    private int errorCode;
    private RouteType routeType;
    private String pagePath;

    public Router(RouteType routeType, String pagePath) {
        this.routeType = routeType;
        this.pagePath = pagePath;
    }

    public Router(int errorCode) {
        routeType = RouteType.ERROR;
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public RouteType getRouteType() {
        return routeType;
    }

    public void setRouteType(RouteType routeType) {
        this.routeType = routeType;
    }

    public String getPagePath() {
        return pagePath;
    }

    public void setPagePath(String pagePath) {
        this.pagePath = pagePath;
    }

    public enum RouteType {
        REDIRECT,
        FORWARD,
        ERROR
    }
}

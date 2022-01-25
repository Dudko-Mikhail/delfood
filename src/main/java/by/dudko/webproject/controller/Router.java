package by.dudko.webproject.controller;

public class Router {
    private RouteType routeType;
    private String pagePath;

    public Router(RouteType routeType, String pagePath) { // TODO возможно лишний
        this.routeType = routeType;
        this.pagePath = pagePath;
    }

    public Router() {}

    public RouteType getRouteType() {
        return routeType;
    }

    public void setRouteType(RouteType routeType) {
        this.routeType = routeType;
    }

    public String getPagePath() {
        return pagePath;
    }

    public void setPagePath(String page) {
        this.pagePath = page;
    }

    public enum RouteType {
        REDIRECT,
        FORWARD
    }
}

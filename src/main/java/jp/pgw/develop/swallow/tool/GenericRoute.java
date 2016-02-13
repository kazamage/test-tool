package jp.pgw.develop.swallow.tool;

import org.apache.camel.builder.RouteBuilder;

public class GenericRoute extends RouteBuilder {

    private final RouteDefinition routeDefinition;

    public GenericRoute(final RouteDefinition routeDefinition) {
        this.routeDefinition = routeDefinition;
    }

    @Override
    public void configure() throws Exception {
        routeDefinition.configure(this);
    }

}

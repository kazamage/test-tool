package jp.pgw.develop.swallow.tool;

import org.apache.camel.builder.RouteBuilder;

@FunctionalInterface
public interface RouteDefinition {

    void configure(RouteBuilder builder) throws Exception;

}

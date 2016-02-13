package jp.pgw.develop.swallow.tool;

import org.apache.camel.spring.boot.CamelSpringBootApplicationController;
import org.springframework.boot.Banner.Mode;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebAutoConfiguration;
import org.springframework.boot.autoconfigure.security.FallbackWebSecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@EnableAutoConfiguration(exclude = { FallbackWebSecurityAutoConfiguration.class, SpringDataWebAutoConfiguration.class })
@ImportResource({ "groovy/*.groovy", "spring/*.xml" })
public class Application {

    public static void main(final String[] args) {
        try (ConfigurableApplicationContext ctx = new SpringApplicationBuilder().bannerMode(Mode.OFF)
                .sources(Application.class).build().run(args)) {
            ctx.getBean(CamelSpringBootApplicationController.class).blockMainThread();
        }
    }

    public static void stop(final String[] args) {
        System.exit(0);
    }

}

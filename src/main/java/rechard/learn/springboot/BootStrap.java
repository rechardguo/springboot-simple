package rechard.learn.springboot;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Locale;

@SpringBootApplication
@EnableSwagger2
public class BootStrap {

    public static void main(String[] args) {
        SpringApplicationBuilder builder=new SpringApplicationBuilder(BootStrap.class);
        ConfigurableApplicationContext context=builder.run(args);
        context.getMessage("argument.required",null,Locale.CHINESE);
    }

}


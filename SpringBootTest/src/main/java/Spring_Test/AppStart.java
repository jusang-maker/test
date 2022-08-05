package Spring_Test;

import Spring_Test.Controller.Application2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;

/**
 *  @SpringBootApplication 来标注一个主程序类，说明这是一个Spring Boot应用
 */
//@EnableAutoConfiguration
@SpringBootApplication


//@EnableAsync

public class AppStart implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {
    @Override
    public void customize(ConfigurableServletWebServerFactory server) {
        server.setPort(8001);
    }
    

    public static void main(String[] args) {
            // Spring应用启动起来
            SpringApplication.run(Application2.class,args);
            System.out.println("访问地址：http://localhost:8001/login");
            System.out.println("http://localhost:8001/swagger-ui.html");
        }
}

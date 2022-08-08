package Spring_Test;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;


@Configuration
public class Swagger3Config {
    /**
     *   application中还配置了mvc，因为springboot2.6.1与swagger3不兼容
     */

    /**
     * ture 启用Swagger3.0， false 禁用（生产环境要禁用）
     */
    Boolean swaggerEnabled=true;
    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                // 是否开启
                .enable(swaggerEnabled)
                .select()
                // 扫描的路径使用@Api的controller
                //通过注解选定要生成文档的方法
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                // 指定路径处理PathSelectors.any()代表所有的路径
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("Swagger3接口文档")
                .description("论坛")
                //作者信息
                .contact(new Contact("廖佳德","http://localhost:8001/login", "2569341311@qq.com"))
                .version("1.0")
                .build();
    }
}

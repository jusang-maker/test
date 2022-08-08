package Spring_Test.Controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyMvcConfig  implements WebMvcConfigurer{
    /**
     * 配置拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册一个拦截器，检查会话，所有/开头的请求都经过此拦截器
        registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/**").
                excludePathPatterns("/login","/static/**","/user/**","/zhuce","/rest",
                //swagger3需要放行的界面
                "/swagger-resources/**","/swagger-ui/**", "/v3/**", "/error"
               );
    }

    /**
     * 配置资源映射
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {  //配置静态资源的映射
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:static/");
        registry.addResourceHandler("/templates/**")
                .addResourceLocations("classpath:templates/");
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:\\META-INF\\resources\\");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:\\META-INF\\resources\\webjars\\");

    }

    /**
     * 请求页面映射
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/templates/hello.html").setViewName("hello");
        registry.addViewController("/templates/login.html").setViewName("login");
    }

}

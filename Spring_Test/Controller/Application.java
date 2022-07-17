package Spring_Test.Controller;

import Spring_Test.entity.content;
import Spring_Test.entity.tiezi;
import Spring_Test.entity.user_Info;
import mysql_base.sql_save;
import org.mortbay.util.ajax.JSON;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@EnableAutoConfiguration     //启用自动配置
//@RestController
@Controller
@Configuration
public class Application implements WebMvcConfigurer {
    static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);  //建立定长为3的线程池
    static boolean status;

    @GetMapping("/")
    public String index() {
        return "";
    }

    /**
     * 配置静态资源的映射 没有这个会导致静态资源404，导致没有背景
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {  //配置静态资源的映射
        registry.addResourceHandler("/static/**").addResourceLocations("file:C:\\Users\\DELL\\OneDrive\\IDEA\\test1\\src\\main\\resources\\static\\");
    }

    @GetMapping("/hello")
    public String test() {  //RequestParam注解获取URl携带的参数
        return "hello";
    }

    @RequestMapping(value = "/zhuce", method = RequestMethod.POST)
    @ResponseBody  //要返回json数据时使用
    public String zhuce(@RequestBody user_Info user_info, @RequestParam Map<String, String> parameter) {
        String user_name = user_info.getUser_name();
        String passwd = user_info.getPasswd();
        fixedThreadPool.execute(new Thread() {   //创建匿名线程并提交到线程池
            @Override
            public void run() {
                try {
                    sql_save.register(user_name, passwd);
                    System.out.println("注册成功");
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }

        });
        parameter.put("msg", "用户:" + user_info.getUser_name() + " 注册成功");
        return JSON.toString(parameter);
    }

    @GetMapping("/houduan")
    public String houduan(ModelMap model) {
        user_Info user = new user_Info();
        user.setUser_name("ljd");
        model.addAttribute("msg", user);
        return "world";

    }

    @GetMapping("/hello2")
    public String test2() {  //RequestParam注解获取URl携带的参数
        return "login";
    }


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
//    @Async
    public String login(@RequestBody user_Info user, @RequestParam Map<String, String> parameter) throws Exception {
        String user_name = user.getUser_name();
        String passwd = user.getPasswd();
        status = sql_save.select(user_name, passwd);
        parameter.put("msg", "" + status);
        return JSON.toString(parameter);
    }

    @GetMapping("/luntan")
    public String luntan(ModelMap modelMap) throws Exception{
        LinkedList<tiezi> tiezi_list = sql_save.tiezi_get();
        for (tiezi tiezi : tiezi_list) {
//            System.out.println(tiezi.getUser());
//            System.out.println(tiezi.getContent());
        }

        modelMap.put("tiezi_list", tiezi_list); //把帖子列表传输到前端
        return "luntan";
    }

    @RequestMapping(value = "/publish", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> publish(@RequestBody content content, @RequestParam(value ="user") String user, Map<String, String> parameter) throws Exception{
        System.out.println("前端传来的内容为："+content.getContent());
        System.out.println(user);
        sql_save.tiezi_save(user,content.getContent());
        parameter.put("msg", "ok");
        return parameter;
    }

}





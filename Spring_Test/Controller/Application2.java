package Spring_Test.Controller;

import Spring_Test.Dao.UserMapper;
import Spring_Test.JsonResult;
import Spring_Test.entity.content;
import Spring_Test.entity.tiezi;
import Spring_Test.entity.user;
import Spring_Test.entity.user_Info;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.avro.data.Json;
import org.apache.http.HttpRequest;
import org.mortbay.util.ajax.JSON;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import scala.util.parsing.json.JSONObject;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.Resource;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Controller
@EnableAutoConfiguration
@MapperScan(basePackages = {"Spring_Test"})
@Import(MyMvcConfig.class)  //import注解可用于导入配置类
@Api(value = "论坛管理")
public class Application2 {
    @RequestMapping(value = "/rest",method = {RequestMethod.GET})
    @ApiOperation("测试函数")
    public JsonResult<user_Info> rest() {
        user_Info user = new user_Info();
        user.setUser_name("liaojiade");
        user.setPasswd("123456");
        return new JsonResult<>(user,"获取成功");
    }

    /**
     * 登录页面
     * @return
     */
    @GetMapping(value = "/login")
    @ApiOperation("登录页面")
    public String login() {
        return "login";
    }

    /**
     * 注册页面
     * @return
     */
    @ApiOperation("注册页面")
    @RequestMapping(value = "/zhuce",method = RequestMethod.GET)
    public String zhuce() {
        return "hello";

    }

    /**登录函数
     *
     */
    @Resource  //Bean注入
    private UserMapper userService; //导入接口
    @ApiOperation("登录函数")
    @ResponseBody
    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public String getUserByName(@RequestBody user_Info user_info,HttpSession session, Map<String, String> parameter) {
        //获取用户列表
        List<user> userList = userService.getUserByName();
        for (user user : userList) {
            //用户名密码验证
            if (user.getName().equals(user_info.getUser_name()) && user.getPasswd().equals(user_info.getPasswd())) {
                session.setAttribute("loginUser", user_info.getUser_name());
                System.out.println("登录成功");
                parameter.put("msg", "true");
                return JSON.toString(parameter);
            }
        }
        parameter.put("msg", "false");
        return JSON.toString(parameter);
    }

    /**
     * 注册函数
     * @param user
     * @param parameter
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/user/zhuce",method = {RequestMethod.POST})
    @ApiOperation(value = "注册函数")
    public String zhuce(@RequestBody user_Info user,@RequestParam Map<String,String> parameter) {
        userService.Add_user(user.getUser_name(),user.getPasswd());
        parameter.put("msg", "注册成功");
        return Json.toString(parameter);
    }

    /**
     * 论坛界面 (已设置分页)
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/luntan",method = {RequestMethod.GET})
    @ApiOperation("返回帖子列表")
    public ModelMap luntan(ModelMap modelMap, @RequestParam(value = "pageNum") int pageNum, HttpServletRequest request) throws Exception{
        //获取当前登录的用户名并且返回给前端
        String current_user= (String) request.getSession().getAttribute("loginUser");
        Integer pageSize = 2; //设置单页内容数量
        PageHelper.startPage(pageNum, pageSize);  //指定页码和数量
        Page<tiezi> tiezi_list=userService.select_tiezi();
        modelMap.put("tiezi_list", tiezi_list);
        modelMap.put("current_user", current_user);
        return modelMap;
    }

    /**
     * 帖子发布函数
     * @param content
     * @param parameter
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/publish",method = {RequestMethod.POST})
    @ApiOperation("发布帖子")
    public String publish(@RequestBody content content, @RequestParam Map<String, String> parameter, HttpServletRequest request){
        //从Session中获取当前登录的用户名
        Object user = request.getSession().getAttribute("loginUser");
        userService.Save_tiezi(user.toString(),content.getContent());
        parameter.put("msg", "ok");
        return Json.toString(parameter);
    }


}


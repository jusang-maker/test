package Spring_Test.Controller;

import Spring_Test.Dao.UserMapper;
import Spring_Test.JsonResult;
import Spring_Test.entity.*;
import Spring_Test.service.remark_Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@EnableAutoConfiguration
@MapperScan(basePackages = {"Spring_Test"})
@Import(MyMvcConfig.class)  //import注解可用于导入配置类
@ComponentScan(basePackages = {"Spring_Test.service"})
@Api(value = "论坛管理")
public class Application2 {
    @Autowired
    private remark_Service remarkService;
    @Resource
    //导入接口
    private UserMapper userService;

    @RequestMapping(value = "/rest", method = {RequestMethod.GET})
    @ApiOperation("测试函数")
    public JsonResult<UserInfo> rest() {
        UserInfo user = new UserInfo();
        user.setUser_name("liaojiade");
        user.setPasswd("123456");
        return new JsonResult(user, "获取成功");
    }

    /**
     * 登录页面
     *
     * @return
     */
    @GetMapping(value = "/login")
    @ApiOperation("登录页面")
    public String login() {
        return "login";
    }

    /**
     * 注册页面
     *
     * @return
     */
    @ApiOperation("注册页面")
    @RequestMapping(value = "/zhuce", method = RequestMethod.GET)
    public String zhuce() {
        return "hello";

    }

    /**
     * 登录函数
     */
    @ApiOperation("登录函数")
    @ResponseBody
    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public String getUserByName(@RequestBody UserInfo userInfo, HttpSession session, @RequestParam Map parameter) {
        //获取用户列表
        List<user> userList = userService.getUserByName();
        for (user user : userList) {
            //用户名密码验证
            if (user.getName().equals(userInfo.getUser_name()) && user.getPasswd().equals(userInfo.getPasswd())) {
                session.setAttribute("loginUser", userInfo.getUser_name());
                System.out.println("登录成功");
                parameter.put("msg", "true");
                return (JSON.toJSONString(parameter));
            }
        }
        parameter.put("msg", "false");
        return JSON.toJSONString(parameter);
    }

    /**
     * 注册函数
     * @param user
     * @param parameter
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/user/zhuce", method = {RequestMethod.POST})
    @ApiOperation(value = "注册函数")
    public String zhuce(@RequestBody UserInfo user, @RequestParam Map parameter) {
        userService.addUser(user.getUser_name(), user.getPasswd());
        parameter.put("msg", "注册成功");
        return JSON.toJSONString(parameter);
    }

    /**
     * 论坛界面 (已设置分页)
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/luntan", method = {RequestMethod.GET})
    @ApiOperation("返回帖子列表")
    public ModelMap luntan(ModelMap modelMap, @RequestParam(value = "pageNum") int pageNum, HttpServletRequest request) throws Exception {
        //获取当前登录的用户名并且返回给前端
        String current_user = (String) request.getSession().getAttribute("loginUser");
        //设置单页内容数量
        Integer pageSize = 2;
        //指定页码和数量
        PageHelper.startPage(pageNum, pageSize);
        //从数据库中获取帖子
        Page<tiezi> tiezi_list = userService.select_tiezi();
        List<Integer> tieziID_list = tiezi_list.stream().map(tiezi::getID).collect(Collectors.toList());
        //根据帖子ID获取其评论及其子评论
        List<remark> remark_list = remarkService.selectRemark(tieziID_list);
        //System.out.println(remark_list);
        modelMap.put("tiezi_list", tiezi_list);
        modelMap.put("current_user", current_user);
        modelMap.put("remark_list", remark_list);
        return modelMap;
    }

    /**
     * 帖子发布函数
     *
     * @param content
     * @param parameter
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/publish", method = {RequestMethod.POST})
    @ApiOperation("发布帖子")
    public String publish(@RequestBody content content, @RequestParam Map parameter, HttpServletRequest request) {
        //从Session中获取当前登录的用户名
        Object user = request.getSession().getAttribute("loginUser");
        userService.Save_tiezi(user.toString(), content.getContent());
        parameter.put("msg", "ok");
        return JSON.toJSONString(parameter);
    }

    ///**
    // * 评论查询函数
    // */
    //@ResponseBody
    //@RequestMapping(value = "/remark_select", method = {RequestMethod.GET})
    //public List<remark> remark_select() {
    //    Integer root_id = 1;
    //    List<remark> All_List = userService.select_remark();
    //    return All_List.stream().filter(remark -> Objects.equals(remark.getID(), root_id)).map(
    //            remark -> {
    //                remark.setChild_List(getChildrens(remark, All_List));
    //                return remark;
    //            }
    //    ).collect(Collectors.toList());
    //
    //}

    //private List<remark> getChildrens(remark root, List<remark> All_List) {
    //    List<remark> children = All_List.stream().filter(remark -> {
    //        return Objects.equals(remark.getParentID(), root.getID());
    //    }).map((remark) -> {
    //        remark.setChild_List(getChildrens(remark, All_List));
    //        return remark;
    //    }).collect(Collectors.toList());
    //    return children;
    //}

    /**
     * 评论存储函数
     */
    @ResponseBody
    @RequestMapping(value = "/saveRemark", method = {RequestMethod.POST})
    private String saveRemark(@RequestBody remark Remark, @RequestParam Map parameter, HttpServletRequest request) {
        System.out.println(Remark.getParentID());
        System.out.println(Remark.getText());
        try {
            Object user = request.getSession().getAttribute("loginUser");
            userService.savaRemark(user.toString(), Remark.getText(), Remark.getParentID());
            parameter.put("msg", "ok");
        } catch (Exception e) {
            parameter.put("msg", "Don't Login");
        }
        //System.out.println(user.toString());
        return JSON.toJSONString(parameter);
    }
}


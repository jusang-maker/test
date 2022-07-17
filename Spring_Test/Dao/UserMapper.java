package Spring_Test.Dao;

import Spring_Test.entity.*;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {
    //获取用户列表，用于登录验证
    @Select("SELECT * FROM java_user")
    List<user> getUserByName();

    //注册一个用户
    @Insert("INSERT INTO java_user(user,passwd) VALUE(#{user},#{passwd})")
    //使用Param注解指定每一个参数的对应关系
    void Add_user(@Param("user") String user, @Param("passwd") String passwd);

    //查询所有帖子，并且返回一个帖子列表
    @Select("SELECT * FROM tiezi")
    Page<tiezi> select_tiezi();

    //根据用户存储帖子
    @Insert("INSERT INTO tiezi(user,content) VALUE(#{user},#{content})")
    void Save_tiezi(@Param("user") String user,@Param("content") String content);
}

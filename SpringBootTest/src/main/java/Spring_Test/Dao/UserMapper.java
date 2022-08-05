package Spring_Test.Dao;

import Spring_Test.entity.remark;
import Spring_Test.entity.tiezi;
import Spring_Test.entity.user;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {
    //获取用户列表，用于登录验证
    @Select("SELECT * FROM java_user")
    List<user> getUserByName();

    //注册一个用户
    @Insert("INSERT INTO java_user(user,passwd) VALUE(#{user},#{passwd})")
    //使用Param注解指定每一个参数的对应关系
    void addUser(@Param("user") String user, @Param("passwd") String passwd);

    //查询所有帖子，并且返回一个帖子列表
    @Select("SELECT * FROM tiezi")
    Page<tiezi> select_tiezi();

    //根据用户存储帖子
    @Insert("INSERT INTO tiezi(user,content) VALUE(#{user},#{content})")
    void Save_tiezi(@Param("user") String user, @Param("content") String content);

    //获取所有评论(用于获取评论树)
    @Select("SELECT * FROM remark")
    List<remark> selectRemark();

    //评论存储函数
    @Insert("INSERT INTO remark(User,Text,ParentID) VALUES(#{User},#{Text},#{ParentID})")
    void savaRemark(@Param("User") String User,@Param("Text") String Text,@Param("ParentID") Integer ParentID);
}

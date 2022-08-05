package Spring_Test.service.impl;

import Spring_Test.Dao.JavaUserMapper;
import Spring_Test.entity.JavaUser;
import Spring_Test.service.JavaUserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author DELL
* @description 针对表【java_user】的数据库操作Service实现
* @createDate 2022-08-04 22:02:11
*/
@Service
public class JavaUserServiceImpl extends ServiceImpl<JavaUserMapper, JavaUser>
implements JavaUserService{
    @Resource
    private JavaUserMapper javaUserMapper;
    JavaUser javaUser = new JavaUser();
    @Override
    public void insert_Data() {
        javaUser.setUser("cx");
        javaUser.setPasswd("123");
        javaUserMapper.insert(javaUser);
        System.out.println("插入成功");
    }

}

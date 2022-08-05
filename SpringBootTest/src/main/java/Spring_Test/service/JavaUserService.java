package Spring_Test.service;

import Spring_Test.entity.JavaUser;
import com.baomidou.mybatisplus.service.IService;

/**
* @author DELL
* @description 针对表【java_user】的数据库操作Service
* @createDate 2022-08-04 22:02:11
*/
public interface JavaUserService extends IService<JavaUser> {
    void insert_Data();
}

package Spring_Test.service;

import Spring_Test.Dao.UserMapper;
import Spring_Test.Pojo.remark;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class GetRemark_Service {
    @Autowired
    UserMapper userService;

    @Bean
    public List<remark> selectRemark(List<Integer> tiezi_ID) {
        Integer root_id = 2;
        List<remark> All_List = userService.selectRemark();
        return All_List.stream().filter(remark -> tiezi_ID.contains(remark.getParentID())).map(
                remark -> {
                    remark.setChild_List(getChildrens(remark, All_List));
                    return remark;
                }
        ).sorted(Comparator.comparing(remark::getPublishTime).reversed()).collect(Collectors.toList());
    }

    private List<remark> getChildrens(remark root, List<remark> All_List) {
        return All_List.stream().filter(remark -> {
            return Objects.equals(remark.getParentID(), root.getID());
        }).map((remark) -> {
            remark.setChild_List(getChildrens(remark, All_List));
            return remark;
            //    按照日期对评论进行排序
        }).sorted(Comparator.comparing(remark::getPublishTime).reversed()).collect(Collectors.toList());
    }

}

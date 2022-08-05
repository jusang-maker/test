package Spring_Test.service;

import Spring_Test.Dao.UserMapper;
import Spring_Test.entity.remark;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class remark_Service {
    @Autowired
    UserMapper userService;

    @Bean
    public List<remark> selectRemark(List<Integer> tiezi_ID) {
        Integer root_id = 2;
        List<remark> All_List = userService.selectRemark();
        List<remark> root_remark = All_List.stream().filter(remark -> tiezi_ID.contains(remark.getParentID())).map(
                remark -> {
                    remark.setChild_List(getChildrens(remark, All_List));
                    return remark;
                }
        ).collect(Collectors.toList());
        return root_remark;

    }

    private List<remark> getChildrens(remark root, List<remark> All_List) {
        List<remark> children = All_List.stream().filter(remark -> {
            return Objects.equals(remark.getParentID(), root.getID());
        }).map((remark) -> {
            remark.setChild_List(getChildrens(remark, All_List));
            return remark;
        }).collect(Collectors.toList());
        return children;
    }

}

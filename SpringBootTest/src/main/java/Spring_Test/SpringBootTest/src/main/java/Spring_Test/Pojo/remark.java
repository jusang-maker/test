package Spring_Test.Pojo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
public class remark implements Serializable {
    @JsonProperty(value = "ID")
    private Integer ID;
    @JsonProperty(value = "User")
    private String User;
    @JsonProperty(value = "Text")
    private String Text;
    @JsonProperty(value = "ParentID")
    private Integer ParentID;
    @JSONField(format = "yyyy-mm-dd HH:mm:ss")
    private String PublishTime;

    private List<remark> Child_List;

    public remark(Integer id, String user, String text, Integer parentID) {
        this.ID = id;
        this.User = user;
        this.Text = text;
        this.ParentID = parentID;
    }

    public remark(Integer id, String text) {
        this.ID = id;
        this.Text=text;
    }
    public remark(Integer id, String user, String text, Integer parentID,List<remark> Child_List) {
        this.ID = id;
        this.User = user;
        this.Text = text;
        this.ParentID = parentID;
        this.Child_List=Child_List;
    }
}

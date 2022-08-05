package Spring_Test.entity;

import lombok.Data;

@Data
public class tiezi {
    private Integer ID;
    private String user;
    private String content;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

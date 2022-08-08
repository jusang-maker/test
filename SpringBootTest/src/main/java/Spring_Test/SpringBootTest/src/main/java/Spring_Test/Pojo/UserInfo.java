package Spring_Test.Pojo;

import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;


public class UserInfo {
    private String user_name;
    private String passwd;


    @Getter
    public String getUser_name() {
        return user_name;
    }
    @Setter
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
    @Getter
    public String getPasswd() {
        return passwd;
    }
    @Setter
    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
}

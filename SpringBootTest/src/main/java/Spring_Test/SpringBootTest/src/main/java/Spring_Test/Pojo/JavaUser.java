package Spring_Test.Pojo;

import java.io.Serializable;

/**
 * 
 * @TableName java_user
 */
public class JavaUser implements Serializable {
    /**
     * 
     */
    private String user;

    /**
     * 
     */
    private String passwd;

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public String getUser() {
        return user;
    }

    /**
     * 
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * 
     */
    public String getPasswd() {
        return passwd;
    }

    /**
     * 
     */
    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        JavaUser other = (JavaUser) that;
        return (this.getUser() == null ? other.getUser() == null : this.getUser().equals(other.getUser()))
            && (this.getPasswd() == null ? other.getPasswd() == null : this.getPasswd().equals(other.getPasswd()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getUser() == null) ? 0 : getUser().hashCode());
        result = prime * result + ((getPasswd() == null) ? 0 : getPasswd().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", user=").append(user);
        sb.append(", passwd=").append(passwd);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
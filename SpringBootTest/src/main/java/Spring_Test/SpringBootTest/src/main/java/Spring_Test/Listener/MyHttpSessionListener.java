package Spring_Test.Listener;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
@Component
public class MyHttpSessionListener implements HttpSessionListener {
    public Integer count = 0;
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        System.out.println("用户上线了");
        count++;
        se.getSession().getServletContext().setAttribute("count",count);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        System.out.println("用户退出");
        count--;
        se.getSession().getServletContext().setAttribute("count",count);
    }
}

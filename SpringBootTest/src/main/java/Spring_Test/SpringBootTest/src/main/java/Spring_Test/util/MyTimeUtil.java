package Spring_Test.util;

import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author DELL
 */
@Component
public class MyTimeUtil {
    public Date getNowTime() {
        Date nowData = new Date();

        return nowData;
    }
}

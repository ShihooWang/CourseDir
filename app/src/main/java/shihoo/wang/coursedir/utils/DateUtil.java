package shihoo.wang.coursedir.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by shihoo.wang on 2018/11/27.
 * Email shihu.wang@bodyplus.cc
 */

public class DateUtil {

    /**
     * 获取现在时间
     * 24小时制
     *
     * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
     */
    public static String getStringDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

}

package shihoo.wang.coursedir.utils;

/**
 * Created by shihoo.wang on 2018/6/20.
 * Email shihu.wang@bodyplus.cc
 */

public class FastClickUtil {

    private static final int MIN_CLICK_DELAY_TIME = 750; //  点击延时750ms
    private static long lastClickTime = 0;


    public static boolean isFastClick(){
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            return false;
        }
        return true;
    }
}

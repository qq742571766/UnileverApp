package cn.com.unilever.www.unileverapp.utils;

import java.text.SimpleDateFormat;

/**
 * @class
 * @name 林郝
 * @anthor QQ:742571766
 * @time 2017/7/4 11:11
 */
public class SystemTimeUtil {
    public static String getErrorDate() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String date = sDateFormat.format(new java.util.Date());
        return date;
    }

    public static String getCloseDate() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = sDateFormat.format(new java.util.Date());
        return date;
    }
}

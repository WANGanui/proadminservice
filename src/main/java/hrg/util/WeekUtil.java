package hrg.util;

import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by 82705 on 2017/5/27.
 */
public class WeekUtil {
    private static Logger logger = Logger.getLogger(WeekUtil.class);
    private final static String DATE_PATTERN_CN_SHORT = "yyyy-MM-dd";

    /**
     * 获取当前日期所在周一
     * @return
     */
    public static String getWeekBegin(){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN_CN_SHORT);
        cal.setTime(new Date());
        int d = 0;
        if(cal.get(Calendar.DAY_OF_WEEK)==1){
            d = -6;
        }else{
            d = 2-cal.get(Calendar.DAY_OF_WEEK);
        }
        cal.add(Calendar.DAY_OF_WEEK, d);
        //所在周开始日期(周一)
        String weekBegin = sdf.format(cal.getTime());
        return weekBegin;
    }

    /**
     * 获取当前日期所在周五
     * @return
     * @throws Exception
     */
    public static String getWeekEnd() throws Exception{
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN_CN_SHORT);
        cal.setTime(new Date());
        int d = 0;
        if(cal.get(Calendar.DAY_OF_WEEK)==1){
            d = -6;
        }else{
            d = 2-cal.get(Calendar.DAY_OF_WEEK);
        }
        cal.add(Calendar.DAY_OF_WEEK, d);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(cal.getTime());
        calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH) + 4);
        return sdf.format(calendar.getTime());
    }
}

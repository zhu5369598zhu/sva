package io.renren.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 工单编号工具类
 *
 * @author Mark sunlightcs@gmail.com
 * @since 2.0.0
 */
public class OrderUtils {

	
	/**
     * 缺陷工单编号
     */
    public static String orderDefectNumber() {
    	
    	String str = "WQGD";
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
        String newDate=sdf.format(new Date());
        Random random=new Random();
        String result="";
        for(int i=0;i<3;i++){
        result+=random.nextInt(10);
        }
        String orderNumber =str+newDate+result;
        
    	return  orderNumber;
    }
	
    /**
     * 班组工单编号
     */
    public static String orderNumber() {
    	
    	String str = "SWQ";
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
        String newDate=sdf.format(new Date());
        Random random=new Random();
        String result="";
        for(int i=0;i<3;i++){
        result+=random.nextInt(10);
        }
        String orderNumber =str+newDate+result;
        
    	return orderNumber;
    }
    
    
}

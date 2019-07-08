package io.renren.common.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 工单编号工具类
 *
 * @author Mark sunlightcs@gmail.com
 * @since 2.0.0
 */
@Configuration
public class OrderUtils {

    @Value("${company.userName: WQ}")
    private static String companyName;




	/**
     * 缺陷工单编号
     */
    public static String orderDefectNumber(Integer num) {
    	
    	String str = companyName+"QX";
        SimpleDateFormat sdf=new SimpleDateFormat("yyMMdd");
        String newDate=sdf.format(new Date());
        String newString = String.format("%03d", num+1);
        String orderNumber = str + newDate + newString;
    	return  orderNumber;
    }

    /**
     * 缺陷工单编号
     */
    public static String orderManagementNumber(Integer num) {

        String str = companyName+"GD";
        SimpleDateFormat sdf=new SimpleDateFormat("yyMMdd");
        String newDate=sdf.format(new Date());
        String newString = String.format("%03d", num+1);
        String orderNumber = str + newDate + newString;
        return  orderNumber;
    }


    /**
     * 班组工单编号
     */
    public static String orderNumber(Integer num) {

        String str =companyName+"BZ";
        SimpleDateFormat sdf=new SimpleDateFormat("yyMMdd");
        String newDate=sdf.format(new Date());
        String newString = String.format("%03d", num+1);
        String orderNumber = str + newDate + newString;
    	return orderNumber;
    }
    
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }
}

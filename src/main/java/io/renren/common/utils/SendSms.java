package io.renren.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.ClientException;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class SendSms {
	
   	private static String accessKeyId;
   	
   	private static String accessKeySecret;
   	
   	private	static String SignName;
   	
   	private	static String TemplateDeviceCode;
   	
   	private	static String TemplateOrderCode;
   	
	/*
	 * 推送规则 设备发送 短信接口。
	 * */
	public static String deviceSend(String phone, JSONObject jsonObject) {
	    if("".equals(SignName)){ // 没有开通短信接口

	    }else {
            DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
            IAcsClient client = new DefaultAcsClient(profile);

            CommonRequest request = new CommonRequest();
            request.setMethod(MethodType.POST);
            request.setDomain("dysmsapi.aliyuncs.com");
            request.setVersion("2017-05-25");
            request.setAction("SendSms");
            request.putQueryParameter("RegionId", "cn-hangzhou");
            request.putQueryParameter("PhoneNumbers", phone);
            request.putQueryParameter("SignName", SignName);
            request.putQueryParameter("TemplateCode", TemplateDeviceCode);
            request.putQueryParameter("TemplateParam", jsonObject.toJSONString());
            try {
                CommonResponse response = client.getCommonResponse(request);
                System.out.println(response.getData());
                return "ok";
            } catch (ServerException e) {
                e.printStackTrace();
                return "error";
            } catch (ClientException e) {
                e.printStackTrace();
                return "error";
            } catch (com.aliyuncs.exceptions.ClientException e) {
                e.printStackTrace();
            }
        }

        return "error";
    }
	/*
	 * 工单，日志发送接口
	 * */
	public static String ordersend(String phone, JSONObject jsonObject) {
	    if("".equals(SignName)){ // 没有开通短信接口

        }else {
            DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
            IAcsClient client = new DefaultAcsClient(profile);

            CommonRequest request = new CommonRequest();
            request.setMethod(MethodType.POST);
            request.setDomain("dysmsapi.aliyuncs.com");
            request.setVersion("2017-05-25");
            request.setAction("SendSms");
            request.putQueryParameter("RegionId", "cn-hangzhou");
            request.putQueryParameter("PhoneNumbers", phone);
            request.putQueryParameter("SignName", SignName);
            request.putQueryParameter("TemplateCode", TemplateOrderCode);
            request.putQueryParameter("TemplateParam", jsonObject.toJSONString());
            try {
                CommonResponse response = client.getCommonResponse(request);
                System.out.println(response.getData());
                return "ok";
            } catch (ServerException e) {
                e.printStackTrace();
                return "error";
            } catch (ClientException e) {
                e.printStackTrace();
                return "error";
            } catch (com.aliyuncs.exceptions.ClientException e) {
                e.printStackTrace();
            }
        }

        return "error";
    }
	
	
	@Value("${company.accessKeyId}")
	public  void setAccessKeyId(String accessKeyId) {
		this.accessKeyId = accessKeyId;
	}
	@Value("${company.accessKeySecret}")
	public  void setAccessKeySecret(String accessKeySecret) {
		this.accessKeySecret = accessKeySecret;
	}
	@Value("${company.SignName}")
	public void setSignName(String signName) {
		this.SignName = signName;
	}
	@Value("${company.TemplateDeviceCode}")
	public  void setTemplateDeviceCode(String templateDeviceCode) {
		this.TemplateDeviceCode = templateDeviceCode;
	}
	@Value("${company.TemplateOrderCode}")
	public  void setTemplateOrderCode(String templateOrderCode) {
		this.TemplateOrderCode = templateOrderCode;
	}
	
	
	
	
	
	
	
	
}

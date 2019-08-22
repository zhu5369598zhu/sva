package io.renren.common.utils;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

public class SendSms {
	static final String accessKeyId = "LTAI1jFQEmsk61wR";
   	static final String accessKeySecret = "2uLQpuedE2BZPKR2kuB3kf0SDTjDsV";
	public static String send(String phone) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", "北京国能华控科技有限公司");
        request.putQueryParameter("TemplateCode", "SMS_164385191");
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
        }
    }
	/*
	 * 推送规则 设备发送 短信接口。
	 * */
	public static String deviceSend(String phone, JSONObject jsonObject) {
		DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", "北京国能华控科技有限公司");
        request.putQueryParameter("TemplateCode", "SMS_172603667");
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
        }
		
	}
	/*
	 * 工单，日志发送接口
	 * */
	public static String ordersend(String phone, JSONObject jsonObject) {
		DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", "北京国能华控科技有限公司");
        request.putQueryParameter("TemplateCode", "SMS_172603704");
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
        }
		
	}
	
}

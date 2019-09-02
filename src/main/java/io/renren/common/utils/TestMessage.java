package io.renren.common.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class TestMessage {

	private static String corpId;
	
	private static String corpsecret;
	
	private static String agentid;
	
	public static String getAccessToken() {
		String access_token = "";
	  
		String access_token_url = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid={corpId}&corpsecret={corpsecret}";
		String requestUrl = access_token_url.replace("{corpId}",corpId).replace("{corpsecret}",corpsecret);
		try {
			String accessTokenStr = HttpUtils.sendGet(requestUrl);
			JSONObject accessToken = JSONObject.parseObject(accessTokenStr);
			System.out.println(accessToken);
			access_token = accessToken.getString("access_token");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return access_token;
	}
	
	@Value("${company.corpId}")
	public  void setCorpId(String corpId) {
		this.corpId = corpId;
	}
	
	@Value("${company.corpsecret}")
	public void setCorpsecret(String corpsecret) {
		this.corpsecret = corpsecret;
	}
	@Value("${company.agentid}")
	public void getAgentid(String agentid) {
		this.agentid = agentid;
	}


	// 工单 发送接口
	public static void ordersend(String touser, String content,String num) {
		if("".equals(corpId)){ // 没有开通微信推送接口

		}else {
			String access_token = TestMessage.getAccessToken();
			content = "尊敬的用户,"+content+"编号"+num;
			Text text = new Text();
			text.setContent(content);
			//1.获取json字符串：将message对象转换为json字符串
			JSONObject jsonMessage = new JSONObject();
			jsonMessage.put("touser",touser);
			jsonMessage.put("msgtype", "text");
			jsonMessage.put("agentid", agentid);
			jsonMessage.put("text", text);
			jsonMessage.put("safe", 0);
			System.out.println("jsonTextMessage:"+jsonMessage);
			//2.获取请求的url
			String sendMessage_url="https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token={ACCESS_TOKEN}";
			sendMessage_url=sendMessage_url.replace("{ACCESS_TOKEN}", access_token);
			try {
				//3.调用接口，发送消息
				String sendMessageStr=HttpUtils.post(sendMessage_url, jsonMessage, access_token);

			} catch (ParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	
	/*
	 * 推送规则 设备发送 微信接口。
	 * */
	public static void deviceSend(String touser, String content) {
		if("".equals(corpId)) { // 没有开通微信推送接口
		}else{
			String access_token = TestMessage.getAccessToken();
			Text text = new Text();
			text.setContent(content);
			//1.获取json字符串：将message对象转换为json字符串
			JSONObject jsonMessage = new JSONObject();
			jsonMessage.put("touser",touser);
			jsonMessage.put("msgtype", "text");
			jsonMessage.put("agentid", agentid);
			jsonMessage.put("text", text);
			jsonMessage.put("safe", 0);
			System.out.println("jsonTextMessage:"+jsonMessage);
			//2.获取请求的url
			String sendMessage_url="https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token={ACCESS_TOKEN}";
			sendMessage_url=sendMessage_url.replace("{ACCESS_TOKEN}", access_token);
			try {
				//3.调用接口，发送消息
				String sendMessageStr=HttpUtils.post(sendMessage_url, jsonMessage, access_token);

			} catch (ParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	
}

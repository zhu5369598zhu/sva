package io.renren.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 *  钉钉接口推送消息
 */
@Component
public class DingdingSend {

    public static String appkey;

    public static String appsecret;

    public static String dingdingagentid;

    @Value("${company.appkey}")
    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }
    @Value("${company.appsecret}")
    public void setAppsecret(String appsecret) {
        this.appsecret = appsecret;
    }
    @Value("${company.dingdingagentid}")
    public void setDingdingagentid(String dingdingagentid) {
        this.dingdingagentid = dingdingagentid;
    }

    public static void ordersend(String conent, String phone){
        String text = conent + ",发送时间"+ DateUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss");
        String token = DingdingSend.getToken();
        if(token == null){
            return;
        }
        ArrayList<String> deptList = getDeptList(token);
        for(int i=0; i< deptList.size(); i++){
            String deptId = deptList.get(i);
            ArrayList<String> users = getUsersByDeptId(token, deptId);
            for (int j=0; j<users.size(); j++){
                String userId = users.get(j);
                String mobile = getUser(token, userId);
                if(mobile.equals(phone)){ // 发送消息
                    //发送消息
                    String content = "{"
                            + "\"touser\": \""+ userId +"\","//发送用户ID，多个用,分割
                            + "\"toparty\": \"1\","//发送部门ID，多个用,分割
                            + "\"agentid\": \"" + dingdingagentid + "\","
                            + "\"msgtype\": \"text\","
                            + "\"text\": {\"content\": \""+ text +"\"}"
                            + "}";
                    String url = "https://oapi.dingtalk.com/message/send?access_token="+token;
                    String rt = null;
                    try {
                        rt = httpsRequest(url, "GET", content);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println(rt);
                    return;
                }
            }
        }
    }
    /**
     * @Description:获得token
     * @Method:getToken
     * @Authod:zhangshaoyang
     * @Date:2018/3/19 下午3:00
     */
    private static String getToken(){
        try {
            String result = HttpUtils.sendGet("https://oapi.dingtalk.com/gettoken?appkey="+ appkey + "&appsecret=" + appsecret);
            JSONObject json = JSONObject.parseObject(result);
            if (json != null && "0".equals(json.get("errcode").toString())) {
                return json.get("access_token").toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * @Description:获得部门集合
     * @Method:getToken
     * @Authod:zhangshaoyang
     * @Date:2018/3/19 下午3:00
     */
    private static ArrayList<String> getDeptList(String token){
        ArrayList<String> list = new ArrayList<>();
        try{

            String result = HttpUtils.sendGet("https://oapi.dingtalk.com/department/list?access_token="+ token);
            JSONObject jsonObject = JSON.parseObject(result);
            String ok = jsonObject.get("errmsg").toString();

            if("ok".equals(ok)){
                String department = jsonObject.get("department").toString();
                List<JSONObject> jsonObjectList = JSON.parseArray(department, JSONObject.class);
                for (JSONObject jsonObject2 : jsonObjectList) {
                    String id = jsonObject2.get("id").toString();
                    list.add(id);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * @Description:根据部门ID获取用户集合
     * @Method:getDDSimpleUsersByDeptId
     * @Authod:zhang_cq
     * @Date:2018/3/20
     * @param accessToken
     * @param departmentId
     */
    public static ArrayList<String> getUsersByDeptId(String accessToken, String departmentId){
        ArrayList<String> list = new ArrayList<>();
        try {
            String result = HttpUtils.sendGet("https://oapi.dingtalk.com/user/simplelist?access_token="+ accessToken + "&department_id="+ departmentId);
            JSONObject jsonObject = JSON.parseObject(result);
            String ok = jsonObject.get("errmsg").toString();
            if("ok".equals(ok)){
                String userlist = jsonObject.get("userlist").toString();
                List<JSONObject> jsonObjectList = JSON.parseArray(userlist, JSONObject.class);
                for (JSONObject jsonObject2 : jsonObjectList) {
                    String id = jsonObject2.get("userid").toString();
                    list.add(id);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static String getUser(String accessToken, String userid){

        try {
            String result = HttpUtils.sendGet("https://oapi.dingtalk.com/user/get?access_token="+ accessToken + "&userid="+ userid);
            JSONObject jsonObject = JSON.parseObject(result);
            String mobile = jsonObject.get("mobile").toString();
            return mobile;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private static String httpsRequest(String requestUrl, String requestMethod, String outputStr) throws Exception {
        HttpsURLConnection conn = null;
        BufferedReader bufferedReader = null;
        try {
            URL url = new URL(requestUrl);
            conn = (HttpsURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod(requestMethod);
            conn.setRequestProperty("content-type", "application/json");
            if (null != outputStr) {
                OutputStream outputStream = conn.getOutputStream();
                outputStream.write(outputStr.getBytes("utf-8"));
                outputStream.close();
            }
            bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            return buffer.toString();
        } catch (Exception e) {
            throw e;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {

                }
            }
        }
    }


}

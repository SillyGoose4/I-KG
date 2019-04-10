package cn.nnu.jyjs.knowledgegraph.tools;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * 调用百度百科api完善关系
 * create by wangj
 * in 3/27/2019
 * LATEST UPGRADE :
 *  无用的百度图谱，真让人难过
 */
public class BaiduApi {

    public static String EntityUrl = "https://aip.baidubce.com/rpc/2.0/kg/v1/cognitive/entity_annotation";


    /**
     * 获取权限token
     * @return 返回示例：
     * {
     * "access_token": "24.460da4889caad24cccdb1fea17221975.2592000.1491995545.282335-1234567",
     * "expires_in": 2592000
     * }
     */
    public static String getAuth() {
        // 官网获取的 API Key 更新为你注册的
        String clientId = "QXVVReax3r4t2yrk06NFAOhc";
        // 官网获取的 Secret Key 更新为你注册的
        String clientSecret = "wqQFF8SioLyQnpXUxNysHxdVDyfloSNr";
        return getAuth(clientId, clientSecret);
    }

    /**
     * 获取API访问token
     * 该token有一定的有效期，需要自行管理，当失效时需重新获取.
     * @param ak - 百度云官网获取的 API Key
     * @param sk - 百度云官网获取的 Securet Key
     * @return assess_token 示例：
     * "24.460da4889caad24cccdb1fea17221975.2592000.1491995545.282335-1234567"
     */
    public static String getAuth(String ak, String sk) {
        // 获取token地址
        String authHost = "https://aip.baidubce.com/oauth/2.0/token?";
        String getAccessTokenUrl = authHost
                // 1. grant_type为固定参数
                + "grant_type=client_credentials"
                // 2. 官网获取的 API Key
                + "&client_id=" + ak
                // 3. 官网获取的 Secret Key
                + "&client_secret=" + sk;
        try {
            URL realUrl = new URL(getAccessTokenUrl);
            // 打开和URL之间的连接
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            // 获取所有响应头字段
            //Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            //for (String key : map.keySet()) {
            //    System.err.println(key + "--->" + map.get(key));
            //}
            // 定义 BufferedReader输入流来读取URL的响应
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String result = "";
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            /**
             * 返回结果示例
             */
            //System.err.println("result:" + result);
            JSONObject jsonObject = new JSONObject(result);
            String access_token = jsonObject.getString("access_token");
            return access_token;
        } catch (Exception e) {
            System.err.printf("获取token失败！");
            e.printStackTrace(System.err);
        }
        return null;
    }

    public static JSONObject request(String content){
        try {
            EntityUrl += "?access_token="+getAuth();
            System.out.println(EntityUrl);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("data",content);
            URL url = new URL(EntityUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type","application/json");
            connection.setRequestProperty("Charset","UTF-8");
            connection.setDoOutput(true);     //需要输出
            connection.setDoInput(true);      //需要输入
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.connect();

            OutputStreamWriter dos = new OutputStreamWriter(connection.getOutputStream());
            System.out.println(jsonObject.toString());
            dos.write(jsonObject.toString());
            dos.flush();
            dos.close();
            int resultCode = connection.getResponseCode();
            if(HttpURLConnection.HTTP_OK == resultCode){
                StringBuffer sb = new StringBuffer();
                String line;
                JSONObject js ;
                BufferedReader response = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while((line = response.readLine()) != null){
                    sb.append(line);
                }
                response.close();
                js=new JSONObject(sb.toString());
                return js;
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }
}

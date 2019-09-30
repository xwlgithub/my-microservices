package com.itxwl.crt;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
//import org.apache.http.impl.client.CloseableHttpClient;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
//import javax.net.ssl.HostnameVerifier;
//import javax.net.ssl.SSLContext;
//import java.security.KeyManagementException;
//import java.security.KeyStoreException;
//import java.security.NoSuchAlgorithmException;
//import java.security.cert.CertificateException;
//import java.security.cert.X509Certificate;
//import org.apache.http.conn.ssl.NoopHostnameVerifier;
//import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.ssl.SSLContextBuilder;
//import org.apache.http.ssl.TrustStrategy;
public class TestHttpsCrt {
    public static void main(String[] args) throws Exception {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("ext_type", "get_order_status");
        map.put("ext_order_list", "['1908304643','1908304642','1908304641']");
        map.put("ext_plan_machine", "123abc");
        String json = JSON.toJSONString(map);

        System.out.println("Body = " + json);


        HttpClient client = new HttpClient();
       // CloseableHttpClient client=createSSLClientDefult();
        client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");

        String url = "https://2q10838f56.51mypc.cn:33029/extend.api.php";
        PostMethod method = new PostMethod(url);
        String restult = "";
        JSONObject jsonObject = new JSONObject();
        try {
            StringRequestEntity entity = new StringRequestEntity(json, "application/json", "UTF-8");
            method.setRequestEntity(entity);
            int resp = client.executeMethod(method);
            System.out.println("Resp = " + resp);

            InputStream inputStream = method.getResponseBodyAsStream();
            restult = inputStream.toString();
            //restult=String.valueOf(inputStream.read());
            jsonObject = JSONObject.parseObject(restult);
            String result = jsonObject.toJSONString();
            System.out.println("Result = " + result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            method.releaseConnection();
        }
    }

//    private static CloseableHttpClient createSSLClientDefult() {
//        try {
//            //使用 loadTrustMaterial() 方法实现一个信任策略，信任所有证书
//            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
//                // 信任所有
//                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
//                    return true;
//                }
//            }).build();
//            //NoopHostnameVerifier类:  作为主机名验证工具，实质上关闭了主机名验证，它接受任何
//            //有效的SSL会话并匹配到目标主机。
//            HostnameVerifier hostnameVerifier = NoopHostnameVerifier.INSTANCE;
//            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
//            return HttpClients.custom().setSSLSocketFactory(sslsf).build();
//        } catch (KeyManagementException e) {
//            e.printStackTrace();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (KeyStoreException e) {
//            e.printStackTrace();
//        }
//        return HttpClients.createDefault();
//    }
    }


import com.alibaba.fastjson.JSONObject;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import org.apache.http.HttpEntity;

/**
 * @author zfyy04
 * @date 2022/6/30 8:56 PM
 */
public class TestMain {

    @Test
    public void test_httpclient_get() throws IOException {
        HttpGet httpGet = new HttpGet("http://localhost:8003/ping");
        httpGet.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.45 Safari/537.36");

        CloseableHttpClient aDefault = HttpClients.createDefault();
        CloseableHttpResponse execute = aDefault.execute(httpGet);
        StatusLine statusLine = execute.getStatusLine();
        System.out.println(statusLine.getStatusCode());
        org.apache.http.HttpEntity entity = execute.getEntity();
        System.out.println(EntityUtils.toString(entity));
        EntityUtils.consume(entity);
        aDefault.close();
    }

    @Test
    public void test_httpclient_post_json() throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://localhost:8003/pingpost");
        httpPost.addHeader("Content-Type", "application/json");
        httpPost.setEntity(new StringEntity(JSONObject.toJSONString(mapData()), ContentType.create("application/json", "utf-8")));
        CloseableHttpResponse execute = httpClient.execute(httpPost);
        org.apache.http.HttpEntity entity = execute.getEntity();
        System.out.println(EntityUtils.toString(entity));
        EntityUtils.consume(entity);
        execute.close();
    }

    @Test
    public void test_httpclient_post_url() throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://localhost:8003/pingposturl");
        httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
        httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        List<NameValuePair> nvps = new ArrayList<>();
        Map map = mapData();
        if(map!=null){
            map.forEach((k,v)->{
                nvps.add(new BasicNameValuePair(String.valueOf(k), String.valueOf(v)));
            });
        }
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
        CloseableHttpResponse execute = httpClient.execute(httpPost);
        org.apache.http.HttpEntity entity = execute.getEntity();
        System.out.println(EntityUtils.toString(entity));
        EntityUtils.consume(entity);
        execute.close();
    }

    /**
     * httpclient方式上传文件
     * @throws Exception
     */
    @Test
    public void test_httpclient_post_file() throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://localhost:8003/upload");

        File file = new File("/Users/zfyy04/Desktop/Sublime插件安装.txt");
//        FileInputStream fileInputStream = new FileInputStream(file);

        FileBody fileBody = new FileBody(file, ContentType.MULTIPART_FORM_DATA, file.getName());
        StringBody comment = new StringBody("A binary file of some kind", ContentType.TEXT_PLAIN);

        //构造文件上传对象
        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
        multipartEntityBuilder.setCharset(Consts.UTF_8);
        multipartEntityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        multipartEntityBuilder.setContentType(ContentType.MULTIPART_FORM_DATA);
//        multipartEntityBuilder.addBinaryBody("file", fileInputStream, ContentType.MULTIPART_FORM_DATA, file.getName());
//        multipartEntityBuilder.addPart("name",comment);
        multipartEntityBuilder.addPart("file",fileBody);
//                            .addBinaryBody("fileName", new File("/Users/zfyy04/Desktop/Sublime插件安装.txt"));
//        multipartEntityBuilder.addBinaryBody("file", new File("/Users/zfyy04/Desktop/Sublime插件安装.txt"));
        org.apache.http.HttpEntity entity = multipartEntityBuilder.build();
        httpPost.setEntity(entity);

        CloseableHttpResponse execute = httpClient.execute(httpPost);
        org.apache.http.HttpEntity responseEntity = execute.getEntity();
        System.out.println(EntityUtils.toString(responseEntity));
        EntityUtils.consume(responseEntity);
        execute.close();
    }


    /**
     * restTemplate方式上传文件
     */
    @Test
    public void test_resttemplate_post_file() throws Exception {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();
        FileSystemResource resource = new FileSystemResource(new File("/Users/zfyy04/Desktop/Sublime插件安装.txt"));
        requestBody.add("file", resource);
        HttpEntity<MultiValueMap> requestEntity = new HttpEntity<>(requestBody, requestHeaders);
        ResponseEntity<String> responseEntity = httpRestTemplate().postForEntity("http://localhost:8003/upload", requestEntity, String.class);
        System.out.println(responseEntity.getBody());

    }

    /**
     * 声明 RestTemplate
     */
    public RestTemplate httpRestTemplate() {
        ClientHttpRequestFactory factory = httpRequestFactory();
        RestTemplate restTemplate = new RestTemplate(factory);
        // 可以添加消息转换
        //restTemplate.setMessageConverters(...);
        // 可以增加拦截器
        //restTemplate.setInterceptors(...);
        return restTemplate;
    }

    public RestTemplate httpsRestTemplate() {
        RestTemplate restTemplate = new RestTemplate(httpsRequestFactory());
        // 可以添加消息转换
        //restTemplate.setMessageConverters(...);
        // 可以增加拦截器
        //restTemplate.setInterceptors(...);
        return restTemplate;
    }

    public ClientHttpRequestFactory httpRequestFactory() {
        return new OkHttp3ClientHttpRequestFactory(okHttpConfigClient());
    }

    public ClientHttpRequestFactory httpsRequestFactory() {
        return new OkHttp3ClientHttpRequestFactory(okHttpsConfigClient());
    }

    public OkHttpClient okHttpConfigClient() {
        return new OkHttpClient().newBuilder().connectionPool(pool()).hostnameVerifier((hostname, session) -> true)
                // 设置代理
//              .proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 8888)))
                // 拦截器
//                .addInterceptor()
                .build();
    }

    public OkHttpClient okHttpsConfigClient() {
        X509TrustManager trustManager = null;
        SSLContext sslContext = null;
        try {
            trustManager = new X509TrustManager() {
                @Override
                public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                }

                @Override
                public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            };
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{trustManager}, new SecureRandom());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new OkHttpClient().newBuilder().connectionPool(pool()).hostnameVerifier((hostname, session) -> true).sslSocketFactory(sslContext.getSocketFactory(), trustManager)
                // 设置代理
//              .proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 8888)))
                // 拦截器
//                .addInterceptor()
                .build();
    }

    public ConnectionPool pool() {
        return new ConnectionPool();
    }


    public Map mapData(){
        Map map = new HashMap<>();
        map.put("name", "hah");
        return map;
    }
}

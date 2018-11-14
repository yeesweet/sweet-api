package com.sweet.api.util;


import com.alibaba.fastjson.JSON;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public enum HttpUtil {
  INSTANCE;
  private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

  CloseableHttpClient client = null;

  HttpUtil() {
    SSLContext sslContext = null;
    try {
      sslContext = SSLContexts.custom()
              .loadTrustMaterial(null, new TrustSelfSignedStrategy())
              .build();
    } catch (Exception e) {
      e.printStackTrace();
    }
    SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.getDefaultHostnameVerifier());
    Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
            .register("https", sslsf)
            .register("http", PlainConnectionSocketFactory.getSocketFactory())
            .build();
    PoolingHttpClientConnectionManager clientConnectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
    clientConnectionManager.setMaxTotal(50);
    clientConnectionManager.setDefaultMaxPerRoute(25);
    RequestConfig defaultRequestConfig = RequestConfig.custom().setConnectTimeout(1000).setSocketTimeout(2000).setConnectionRequestTimeout(2000).build();
    client = HttpClients.custom().setConnectionManager(clientConnectionManager).setDefaultRequestConfig(defaultRequestConfig).build();
  }

  public <T> T postForm(List<NameValuePair> request, Map<String, String> headers, String url, Class<T> clazz, int connectTime, int socketTimeout) throws IOException {
    String body = postForm(request, headers, url, connectTime, socketTimeout);
    return JSON.parseObject(body,clazz);
  }

  public String postJson(String request, Map<String, String> headers, String url, int connectTime, int socketTimeout) throws IOException {
    HttpPost httpPost = new HttpPost(url);
    if (headers != null) {
      headers.forEach((k, v) -> {
        httpPost.setHeader(k, v);
      });
    }
    RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(connectTime).setSocketTimeout(socketTimeout).build();
    httpPost.setConfig(requestConfig);
    StringEntity entity = new StringEntity(request, ContentType.create("application/json", "utf-8"));
    httpPost.setEntity(entity);
    return execute(httpPost);
  }

  public String postForm(List<NameValuePair> request, Map<String, String> headers, String url, int connectTime, int socketTimeout) throws IOException {
    HttpPost httpPost = new HttpPost(url);
    if (headers != null) {
      headers.forEach((k, v) -> {
        httpPost.setHeader(k, v);
      });
    }
    RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(connectTime).setSocketTimeout(socketTimeout).build();
    httpPost.setConfig(requestConfig);
    UrlEncodedFormEntity entity = new UrlEncodedFormEntity(request,"utf-8");
    httpPost.setEntity(entity);
    return execute(httpPost);
  }

  public <T> T postForm(List<NameValuePair> request, Map<String, String> headers, String url, Class<T> clazz) throws IOException {
    String body = postForm(request, headers, url);
    return JSON.parseObject(body,clazz);
  }

  public String postForm(List<NameValuePair> request, Map<String, String> headers, String url) throws IOException {
    HttpPost httpPost = new HttpPost(url);
    if (headers != null) {
      headers.forEach((k, v) -> {
        httpPost.setHeader(k, v);
      });
    }
    httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
    UrlEncodedFormEntity entity = new UrlEncodedFormEntity(request,"utf-8");
    httpPost.setEntity(entity);
    return execute(httpPost);
  }

  public String postJson(String request, Map<String, String> headers, String url) throws IOException {
    return postJson(request,headers,url,2000,2000);
  }


  public <T> T get(Map<String, String> headers, String url, Class<T> clazz, int connectTime, int socketTimeout) throws IOException {
    String body = getString(headers, url, connectTime,socketTimeout);
    return JSON.parseObject(body,clazz);
  }

  public String getString(Map<String, String> headers, String url, int connectTime, int socketTimeout) throws IOException {
    HttpGet httpGet = new HttpGet(url);
    if (headers != null) {
      headers.forEach((k, v) -> {
        httpGet.setHeader(k, v);
      });
    }
    RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(connectTime).setSocketTimeout(socketTimeout).build();
    httpGet.setConfig(requestConfig);
    return execute(httpGet);
  }

  public <T> T get(Map<String, String> headers, String url, Class<T> clazz) throws IOException {
    String body = getString(headers, url);
    return JSON.parseObject(body,clazz);
  }

  public String getString(Map<String, String> headers, String url) throws IOException {
    HttpGet httpGet = new HttpGet(url);
    if (headers != null) {
      headers.forEach((k, v) -> {
        httpGet.setHeader(k, v);
      });
    }
    return execute(httpGet);
  }

  public  String postXML(String url, String xml) throws IOException {
    HttpPost httpPost = new HttpPost(url);
    StringEntity postEntity = new StringEntity(xml, "UTF-8");
    httpPost.addHeader("Content-Type", "text/xml");
    httpPost.setEntity(postEntity);
    return execute(httpPost);
  }

  private String execute(HttpRequestBase request) throws IOException {
    long start = System.currentTimeMillis();
    return client.execute(request, new ResponseHandler<String>() {
      @Override
      public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
        long end = System.currentTimeMillis();
        long period = end-start;
        logger.info("Execute http request,{} time: {} ms",request.getRequestLine().toString(),period);
        InputStream is = null;
        ByteArrayOutputStream baos = null;
        try {
          is = response.getEntity().getContent();
          baos = new ByteArrayOutputStream();
          byte[] buff = new byte[1024];
          int count = 0;
          while ((count = is.read(buff)) != -1) {
            baos.write(buff, 0, count);
          }
          byte[] result = baos.toByteArray();
          return new String(result, "utf-8");
        } finally {
          is.close();
          baos.close();
        }
      }
    });
  }
}

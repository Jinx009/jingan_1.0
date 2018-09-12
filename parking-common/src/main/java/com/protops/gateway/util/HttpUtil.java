package com.protops.gateway.util;

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.*;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;


public class HttpUtil {

    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);
    private static final String FILE_NAME = "httputil.properties";
    private static final HttpConnectionManager connectionManager;
    private static final HttpClient client;

    static {

        InputStream in = HttpUtil.class.getResourceAsStream("/" + FILE_NAME);

        Properties p = new Properties();
        try {
            p.load(in);
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }

        UTF_8 = p.getProperty("http.content.encoding");

        HttpConnectionManagerParams params = new HttpConnectionManagerParams();
        params.setConnectionTimeout(Integer.parseInt(p.getProperty("http.connection.timeout")));
        params.setSoTimeout(Integer.parseInt(p.getProperty("http.so.timeout")));
        params.setStaleCheckingEnabled(Boolean.parseBoolean(p.getProperty("http.stale.check.enabled")));
        params.setTcpNoDelay(Boolean.parseBoolean(p.getProperty("http.tcp.no.delay")));
        params.setDefaultMaxConnectionsPerHost(Integer.parseInt(p.getProperty("http.default.max.connections.per.host")));
        params.setMaxTotalConnections(Integer.parseInt(p.getProperty("http.max.total.connections")));
        params.setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(0, false));

        connectionManager = new MultiThreadedHttpConnectionManager();

        connectionManager.setParams(params);

        client = new HttpClient(connectionManager);

    }

    public static String UTF_8;

    public static String post(String url, String encoding, String content) throws Exception {
        try {
            return new String(post(url, content.getBytes(encoding)), encoding);
        } catch (UnsupportedEncodingException e) {
        }
        return null;
    }

    public static String post(String url, String encoding, String content, Map<String, String> headers) throws Exception {
        try {
            byte[] ret = post(url, content.getBytes(encoding), headers);
            return new String(ret, encoding);
        } catch (Exception e) {
            return null;
        }
    }


    public static String post(String url, String content) throws Exception {
        return post(url, HttpUtil.UTF_8, content);
    }

    public static String post(String url, String content, Map<String, String> headers) throws Exception {
        return post(url, HttpUtil.UTF_8, content, headers);
    }

    public static byte[] post(String url, byte[] content, Map<String, String> headers) throws Exception {
        logger.info("Request message:[" + new String(content) + "] to " + url);
        byte[] ret = postWithSpecHeader(url, new ByteArrayRequestEntity(content), headers);
        if (ret != null) {
            logger.info("Response message:[" + new String(ret) + "] from " + url);
        }
        return ret;
    }


    public static byte[] post(String url, InputStream content, int contentLength) throws Exception {
        return post(url, new InputStreamRequestEntity(content, contentLength));
    }

    public static byte[] post(String url, byte[] content) throws Exception {
        logger.info("Request message:[" + new String(content) + "] to " + url);
        byte[] ret = post(url, new ByteArrayRequestEntity(content));
        if (ret != null) {
            logger.info("Response message:[" + new String(ret) + "] from " + url);
        }
        return ret;
    }

    public static String post(String url, Map<String, String> paramsMap) throws Exception {

        PostMethod method = new PostMethod(url);
        method.addRequestHeader("Connection", "Keep-Alive");
        method.getParams().setCookiePolicy(CookiePolicy.IGNORE_COOKIES);
        method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(0, false));

        try {
            if (paramsMap != null) {
                NameValuePair[] namePairs = new NameValuePair[paramsMap.size()];
                int i = 0;
                for (Map.Entry<String, String> param : paramsMap.entrySet()) {
                    NameValuePair pair = new NameValuePair(param.getKey(), param.getValue());
                    namePairs[i++] = pair;
                }
                method.setRequestBody(namePairs);
                HttpMethodParams param = method.getParams();
                param.setContentCharset(UTF_8);
            }
            client.executeMethod(method);
            return method.getResponseBodyAsString();
        } catch (SocketTimeoutException e) {
            logger.warn("Timeout exception happened while sending to url [" + url + "], Error message :\n" + e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            logger.warn("Connect exception happened while sending to url [" + url + "], Error message :\n" + e.getMessage(), e);
            logger.warn("[" + url + "] " + e.getMessage() + " <- " + e.getClass().getName());
            throw e;
        } finally {
            method.releaseConnection();
        }
    }

    public static byte[] post(String url, RequestEntity requestEntity) throws Exception {

        PostMethod method = new PostMethod(url);
        method.addRequestHeader("Connection", "Keep-Alive");
        method.getParams().setCookiePolicy(CookiePolicy.IGNORE_COOKIES);
        method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(0, false));
        method.setRequestEntity(requestEntity);

        try {

            int statusCode = client.executeMethod(method);

            if (statusCode != HttpStatus.SC_OK) {
                logger.warn("Abnormal HTTP Status: " + method.getStatusLine());
                return null;
            }

            return method.getResponseBody();

        } catch (SocketTimeoutException e) {
            logger.warn("Timeout exception happened while sending to url [" + url + "], Error message :\n" + e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            logger.warn("Connect exception happened while sending to url [" + url + "], Error message :\n" + e.getMessage(), e);
            logger.warn("[" + url + "] " + e.getMessage() + " <- " + e.getClass().getName());
            throw e;
        } finally {
            method.releaseConnection();
        }
    }

    public static byte[] postWithSpecHeader(String url, RequestEntity requestEntity, Map<String, String> headers) throws Exception {

        PostMethod method = new PostMethod(url);
        method.addRequestHeader("Connection", "Keep-Alive");
        method.getParams().setCookiePolicy(CookiePolicy.IGNORE_COOKIES);
        method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(0, false));
        method.setRequestEntity(requestEntity);

        Set<Entry<String, String>> entries = headers.entrySet();
        for (Entry<String, String> entry : entries) {
            method.addRequestHeader(entry.getKey(), entry.getValue());
        }


        try {

            int statusCode = client.executeMethod(method);

            if (statusCode != HttpStatus.SC_OK) {
                logger.warn("Abnormal HTTP Status: " + method.getStatusLine());
                return null;
            }

            return method.getResponseBody();

        } catch (SocketTimeoutException e) {
            logger.warn("Timeout exception happened while sending to url [" + url + "], Error message :\n" + e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            logger.warn("Connect exception happened while sending to url [" + url + "], Error message :\n" + e.getMessage(), e);
            logger.warn("[" + url + "] " + e.getMessage() + " <- " + e.getClass().getName());
            throw e;
        } finally {
            method.releaseConnection();
        }
    }

    public static byte[] get(String url) {

        GetMethod method = new GetMethod(url);
        method.addRequestHeader("Connection", "Keep-Alive");
        method.getParams().setCookiePolicy(CookiePolicy.IGNORE_COOKIES);
        method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(0, false));

        try {

            int statusCode = client.executeMethod(method);

            if (statusCode != HttpStatus.SC_OK) {
                logger.warn("Abnormal HTTP Status: {}", method.getStatusLine());
                return null;
            }

            return method.getResponseBody();

        } catch (Exception e) {
            logger.warn("exception", e);
        } finally {
            method.releaseConnection();
        }

        return null;
    }

    public static String get(String url, String encoding) throws Exception {
        try {
            return new String(get(url), encoding);
        } catch (UnsupportedEncodingException e) {
        }
        return null;
    }
}
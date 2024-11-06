package com.haverson.common.core.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * url处理工具类
 *
 * @author jiangwei
 */
@Slf4j
public class UrlUtil extends org.springframework.web.util.UriUtils {

    /**
     * url 编码，同js decodeURIComponent
     *
     * @param source  url
     * @param charset 字符集
     * @return 编码后的url
     */
    public static String encodeURL(String source, Charset charset) {
        return UrlUtil.encode(source, charset.name());
    }

    /**
     * url 解码
     *
     * @param source  url
     * @param charset 字符集
     * @return 解码url
     */
    public static String decodeURL(String source, Charset charset) {
        return UrlUtil.decode(source, charset.name());
    }

    /**
     * 获取url路径
     *
     * @param uriStr 路径
     * @return url路径
     */
    public static String getPath(String uriStr) {
        URI uri;

        try {
            uri = new URI(uriStr);
        } catch (URISyntaxException var3) {
            throw new RuntimeException(var3);
        }
        return uri.getPath();
    }

    public static String encodeImageToBase64(URL url) throws Exception {
        /*System.out.println("图片的路径为:" + url.toString());*/
        HttpURLConnection conn = null;
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(5 * 1000);
        InputStream inStream = conn.getInputStream();
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        byte[] data = outStream.toByteArray();
        BASE64Encoder encoder = new BASE64Encoder();
        String base64 = encoder.encode(data);
        //返回Base64编码过的字节数组字符串
        return base64.replace("\r\n", "");
    }


    /**
     * 根据获取源请求ip地址
     *
     * @return
     */
    public static String getOriginIp() {
        return getOriginIp(((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest());
    }

    /**
     * 根据获取源请求ip地址
     *
     * @param request - HttpServletRequest
     * @return
     */
    public static String getOriginIp(HttpServletRequest request) {
        try {
            String originIp = request.getHeader("X-Forwarded-For");
            if (StringUtil.isBlank(originIp)) {
                originIp = request.getRemoteAddr();
                if (StringUtil.isNotBlank(originIp)) {
                    return originIp.contains(",") ? originIp.split(",")[0] : originIp;
                } else {
                    log.info("未能获取源请求ip地址！");
                    return "N/A";
                }
            }
            return originIp;
        } catch (Exception e) {
            log.info("获取源请求ip地址出错！", e);
            return "N/A";
        }
    }

    /**
     * 根据获取源请求ip地址
     *
     * @param request - org.springframework.http.server.reactive.ServerHttpRequest
     * @return
     */
    public static String getOriginIp(org.springframework.http.server.reactive.ServerHttpRequest request) {
        try {
            String originIp = request.getHeaders().getFirst("X-Forwarded-For");
            if (StringUtil.isBlank(originIp)) {
                originIp = request.getRemoteAddress().getAddress().getHostAddress();
                if (StringUtil.isNotBlank(originIp)) {
                    return originIp.contains(",") ? originIp.split(",")[0] : originIp;
                } else {
                    log.info("未能获取源请求ip地址！");
                    return "N/A";
                }
            }
            return originIp;
        } catch (Exception e) {
            log.info("获取源请求ip地址出错！", e);
            return "N/A";
        }
    }

    /**
     * 根据获取源请求ip地址
     *
     * @param request - org.springframework.http.server.ServerHttpRequest request
     * @return
     */
    public static String getOriginIp(org.springframework.http.server.ServerHttpRequest request) {
        try {
            String originIp = request.getHeaders().getFirst("X-Forwarded-For");
            if (StringUtil.isBlank(originIp)) {
                originIp = request.getRemoteAddress().getAddress().getHostAddress();
                if (StringUtil.isNotBlank(originIp)) {
                    return originIp.contains(",") ? originIp.split(",")[0] : originIp;
                } else {
                    log.info("未能获取源请求ip地址！");
                    return "N/A";
                }
            }
            return originIp;
        } catch (Exception e) {
            log.info("获取源请求ip地址出错！", e);
            return "N/A";
        }
    }


}

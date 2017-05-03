package com.zhaoql.core.utils;


import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map.Entry;


public class WebUtils {
    public static final String DEFAULT_CHARACTER_ENCODING = "ISO-8859-1";

    /**
     * @param req
     * @return
     */
    public static String getRemoteAddr(HttpServletRequest req) {
        String ip = req.getHeader("X-Forwarded-For");
        if (StringUtils.isNotBlank(ip)) {
            String[] ips = StringUtils.split(ip, ',');
            if (ips != null) {
                for (String tmpip : ips) {
                    if (StringUtils.isBlank(tmpip))
                        continue;
                    tmpip = tmpip.trim();
                    if (isIPAddr(tmpip) && !tmpip.startsWith("10.") && !tmpip.startsWith("192.168.")
                            && !"127.0.0.1".equals(tmpip)) {
                        return tmpip.trim();
                    }
                }
            }
        }
        ip = req.getHeader("x-real-ip");
        if (isIPAddr(ip))
            return ip;
        ip = req.getRemoteAddr();
        if (ip.indexOf('.') == -1)
            ip = "127.0.0.1";
        return ip;
    }

    /**
     * 判断字符串是否是一个IP地址
     *
     * @param addr
     * @return
     */
    public static boolean isIPAddr(String addr) {
        if (StringUtils.isEmpty(addr))
            return false;
        String[] ips = StringUtils.split(addr, '.');
        if (ips.length != 4)
            return false;
        try {
            int ipa = Integer.parseInt(ips[0]);
            int ipb = Integer.parseInt(ips[1]);
            int ipc = Integer.parseInt(ips[2]);
            int ipd = Integer.parseInt(ips[3]);
            return ipa >= 0 && ipa <= 255 && ipb >= 0 && ipb <= 255 && ipc >= 0 && ipc <= 255 && ipd >= 0 && ipd <= 255;
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * 判断是否为ajax
     *
     * @param request
     * @return
     */
    public static boolean isAjax(HttpServletRequest request) {
        return (request.getHeader("X-Requested-With") != null
                && "XMLHttpRequest".equals(request.getHeader("X-Requested-With").toString()));
    }

    /**
     * 执行response
     *
     * @param httpResponse
     * @param jsonString
     */
    public static void sendJson(HttpServletResponse httpResponse, String jsonString) {
        OutputStream os = null;
        try {
            httpResponse.setCharacterEncoding("utf-8");
            httpResponse.setHeader("Content-type", "application/json;charset=UTF-8");
            os = httpResponse.getOutputStream();
            os.write(jsonString.getBytes("utf-8"));
        } catch (IOException e) {

        } finally {
            if (os != null) {
                try {
                    os.flush();
                    os.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

    }

    public static void sendJson(HttpServletResponse httpResponse, String jsonString, org.springframework.http.HttpStatus httpStatus) {
        OutputStream os = null;
        try {
            httpResponse.setStatus(httpStatus.value());
            httpResponse.setCharacterEncoding("utf-8");
            httpResponse.setHeader("Content-type", "application/json;charset=UTF-8");
            os = httpResponse.getOutputStream();
            os.write(jsonString.getBytes("utf-8"));
        } catch (IOException e) {

        } finally {
            if (os != null) {
                try {
                    os.flush();
                    os.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 执行response
     *
     * @param httpResponse
     * @param
     */
    public static void sendXml(HttpServletResponse httpResponse, String xmlString) {
        OutputStream os = null;
        try {
            httpResponse.setCharacterEncoding("utf-8");
            httpResponse.setHeader("Content-type", "application/xml;charset=UTF-8");
            os = httpResponse.getOutputStream();
            os.write(xmlString.getBytes("utf-8"));
        } catch (IOException e) {

        } finally {
            if (os != null) {
                try {
                    os.flush();
                    os.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 跳转页面
     *
     * @param
     * @param response
     * @param unauthorizedUrl
     * @throws IOException
     */
    public static void redirectURL(HttpServletResponse response, String unauthorizedUrl) throws IOException {
        response.sendRedirect(unauthorizedUrl);
    }


    public static String getPathWithinApplication(HttpServletRequest request) {
        String contextPath = getContextPath(request);
        String requestUri = getRequestUri(request);
        if (StringUtils.startsWithIgnoreCase(requestUri, contextPath)) {
            // Normal case: URI contains context path.
            String path = requestUri.substring(contextPath.length());
            return (StringUtils.isNotBlank(path) ? path : "/");
        } else {
            return requestUri;
        }
    }

    /**
     * 获取请求参数
     * getRequestParams:(这里用一句话描述这个方法的作用). <br/>
     * TODO(这里描述这个方法适用条件 – 可选).<br/>
     * TODO(这里描述这个方法的执行流程 – 可选).<br/>
     * TODO(这里描述这个方法的使用方法 – 可选).<br/>
     * TODO(这里描述这个方法的注意事项 – 可选).<br/>
     *
     * @param request
     * @return
     * @author 半轴
     * @date: 2016年8月4日 上午10:04:20
     */
    public static String getRequestParams(HttpServletRequest request) {
        StringBuilder params = new StringBuilder(request.getMethod() + "  parameters [");
        for (Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
            params.append(entry.getKey()).append("=").append(StringUtils.join(entry.getValue(), ",")).append(",");
        }
        params.append("]");
        return params.toString();
    }

    public static String getRequestUri(HttpServletRequest request) {
        String uri = request.getRequestURI();
        return normalize(decodeAndCleanUriString(request, uri), true);
    }

    private static String decodeAndCleanUriString(HttpServletRequest request, String uri) {
        uri = decodeRequestString(request, uri);
        int semicolonIndex = uri.indexOf(';');
        return (semicolonIndex != -1 ? uri.substring(0, semicolonIndex) : uri);
    }

    public static String getContextPath(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        if ("/".equals(contextPath)) {
            contextPath = "";
        }
        return decodeRequestString(request, contextPath);
    }

    public static String decodeRequestString(HttpServletRequest request, String source) {
        String enc = determineEncoding(request);
        try {
            return URLDecoder.decode(source, enc);
        } catch (UnsupportedEncodingException ex) {
            return URLDecoder.decode(source);
        }
    }

    protected static String determineEncoding(HttpServletRequest request) {
        String enc = request.getCharacterEncoding();
        if (enc == null) {
            enc = DEFAULT_CHARACTER_ENCODING;
        }
        return enc;
    }

    private static String normalize(String path, boolean replaceBackSlash) {

        if (path == null)
            return null;

        // Create a place for the normalized path
        String normalized = path;

        if (replaceBackSlash && normalized.indexOf('\\') >= 0)
            normalized = normalized.replace('\\', '/');

        if (normalized.equals("/."))
            return "/";

        // Add a leading "/" if necessary
        if (!normalized.startsWith("/"))
            normalized = "/" + normalized;

        // Resolve occurrences of "//" in the normalized path
        while (true) {
            int index = normalized.indexOf("//");
            if (index < 0)
                break;
            normalized = normalized.substring(0, index) + normalized.substring(index + 1);
        }

        // Resolve occurrences of "/./" in the normalized path
        while (true) {
            int index = normalized.indexOf("/./");
            if (index < 0)
                break;
            normalized = normalized.substring(0, index) + normalized.substring(index + 2);
        }

        // Resolve occurrences of "/../" in the normalized path
        while (true) {
            int index = normalized.indexOf("/../");
            if (index < 0)
                break;
            if (index == 0)
                return (null); // Trying to go outside our context
            int index2 = normalized.lastIndexOf('/', index - 1);
            normalized = normalized.substring(0, index2) + normalized.substring(index + 3);
        }
        return (normalized);

    }
}

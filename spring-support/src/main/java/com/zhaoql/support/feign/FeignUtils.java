package com.zhaoql.support.feign;

import com.google.common.collect.Lists;
import org.springframework.http.HttpHeaders;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FeignUtils {
    static HttpHeaders getHttpHeaders(Map<String, Collection<String>> headers) {
        HttpHeaders httpHeaders = new HttpHeaders();
        for (Map.Entry<String, Collection<String>> entry : headers.entrySet()) {
            httpHeaders.put(entry.getKey(), new ArrayList<>(entry.getValue()));
        }
        return httpHeaders;
    }

    static Map<String, Collection<String>> getHeaders(HttpHeaders httpHeaders) {
        LinkedHashMap<String, Collection<String>> headers = new LinkedHashMap<>();
        for (Map.Entry<String, List<String>> entry : httpHeaders.entrySet()) {
            headers.put(entry.getKey(), entry.getValue());
        }
        headers.put(HttpHeaders.CONTENT_TYPE, Lists.newArrayList(
                httpHeaders.getContentType().getType().concat("/").concat(httpHeaders.getContentType().getSubtype())));
        return headers;
    }
}

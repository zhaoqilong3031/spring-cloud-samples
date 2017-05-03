package com.zhaoql.support.feign;

import feign.RequestTemplate;
import feign.codec.EncodeException;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.Collection;

public class CustomEncode implements feign.codec.Encoder {
    private ObjectFactory<HttpMessageConverters> messageConverters;

    public CustomEncode(ObjectFactory<HttpMessageConverters> messageConverters) {
        this.messageConverters = messageConverters;
    }

    @Override
    public void encode(Object requestBody, Type bodyType, RequestTemplate request) throws EncodeException {
        if (requestBody != null) {
            Class<?> requestType = requestBody.getClass();
            Collection<String> contentTypes = request.headers().get(HttpHeaders.CONTENT_TYPE);
            MediaType requestContentType = null;
            if (contentTypes != null && !contentTypes.isEmpty()) {
                String type = contentTypes.iterator().next();
                requestContentType = MediaType.valueOf(type);
            }
            for (HttpMessageConverter<?> messageConverter : this.messageConverters.getObject().getConverters()) {
                if (messageConverter.canWrite(requestType, requestContentType)) {

                    FeignOutputMessage outputMessage = new FeignOutputMessage(request);
                    try {
                        @SuppressWarnings("unchecked")
                        HttpMessageConverter<Object> copy = (HttpMessageConverter<Object>) messageConverter;
                        copy.write(requestBody, requestContentType, outputMessage);
                    } catch (IOException ex) {
                        throw new EncodeException("Error converting request body", ex);
                    }
                    request.headers(null);
                    request.headers(FeignUtils.getHeaders(outputMessage.getHeaders()));
                    request.body(outputMessage.getOutputStream().toByteArray(), Charset.forName("UTF-8")); // TODO:
                    return;
                }
            }
            String message = "Could not write request: no suitable HttpMessageConverter " + "found for request type ["
                    + requestType.getName() + "]";
            if (requestContentType != null) {
                message += " and content type [" + requestContentType + "]";
            }
            throw new EncodeException(message);
        }
    }

    private class FeignOutputMessage implements HttpOutputMessage {

        private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        private final HttpHeaders httpHeaders;

        private FeignOutputMessage(RequestTemplate request) {
            httpHeaders = FeignUtils.getHttpHeaders(request.headers());
        }

        @Override
        public OutputStream getBody() throws IOException {
            return this.outputStream;
        }

        @Override
        public HttpHeaders getHeaders() {
            return this.httpHeaders;
        }

        public ByteArrayOutputStream getOutputStream() {
            return this.outputStream;
        }
    }
}
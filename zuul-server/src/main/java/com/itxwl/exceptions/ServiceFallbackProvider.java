package com.itxwl.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.route.ZuulFallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by wiselyman on 2017/5/4.
 */
@RestController
@Component
@SuppressWarnings("all")
public class ServiceFallbackProvider implements ZuulFallbackProvider {
    private static Logger logger= LoggerFactory.getLogger(ServiceFallbackProvider.class);
    @Override
    public String getRoute() {
        return "*";
    }

    @Override
    public ClientHttpResponse fallbackResponse() {
        return new ClientHttpResponse() {
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return HttpStatus.OK;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return 200;
            }

            @Override
            public String getStatusText() throws IOException {
                return "OK";
            }

            @Override
            public void close() {

            }

            /**
             * 监控服务是否启动抛出异常后台
             * @return
             * @throws MyException
             */
            @Override
            public InputStream getBody(){
                ByteArrayInputStream byteArrayInputStream=null;
                    byteArrayInputStream = new ByteArrayInputStream("服务重启或错误，请稍后重试!".getBytes());
                    if (!StringUtils.isEmpty(byteArrayInputStream)){
                        logger.info("服务重启或错误，请稍后重试!");
                    }

                return byteArrayInputStream;
                // return new ByteArrayInputStream("服务重启或错误，请稍后重试!".getBytes());
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
                return headers;
            }
        };
    }

}

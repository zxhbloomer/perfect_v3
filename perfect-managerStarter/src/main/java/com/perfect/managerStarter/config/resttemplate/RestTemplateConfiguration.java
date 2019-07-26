package com.perfect.managerstarter.config.resttemplate;

import com.perfect.common.config.messageconverter.CallbackMappingJackson2HttpMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;

@Configuration
public class RestTemplateConfiguration {
//    @Bean
//    public RestTemplate restTemplate(){
//        RestTemplate restTemplate = new RestTemplate();
////        restTemplate.getInterceptors().add(new LoggingClientHttpRequestInterceptorUtil());
//        return restTemplate;
//    }

    @Value("${RestTemplate-ConnectTimeout}")
    private int RestTemplate_ConnectTimeout;
    @Value("${RestTemplate-ReadTimeout}")
    private int RestTemplate_ReadTimeout;

    @Autowired
    private RestTemplateBuilder builder;

    @Bean
    @ConditionalOnMissingBean({RestTemplate.class})
    public RestTemplate restTemplate(ClientHttpRequestFactory factory) {
        //return builder.build();
        RestTemplate restTemplate = new RestTemplate(factory);

        //换上fastjson
        List<HttpMessageConverter<?>> httpMessageConverterList= restTemplate.getMessageConverters();
        Iterator<HttpMessageConverter<?>> iterator=httpMessageConverterList.iterator();
        if(iterator.hasNext()){
            HttpMessageConverter<?> converter=iterator.next();
            //原有的String是ISO-8859-1编码 去掉
            if(converter instanceof StringHttpMessageConverter){
                iterator.remove();
            }

            //由于系统中默认有jackson 在转换json时自动会启用  但是我们不想使用它 可以直接移除或者将fastjson放在首位
            /*if(converter instanceof GsonHttpMessageConverter || converter instanceof MappingJackson2HttpMessageConverter){
                iterator.remove();
            }*/

        }
        httpMessageConverterList.add(new StringHttpMessageConverter(Charset.forName("utf-8")));
        httpMessageConverterList.add(0,new CallbackMappingJackson2HttpMessageConverter());

        return restTemplate;

    }

    @Bean
    @ConditionalOnMissingBean({ClientHttpRequestFactory.class})
    public ClientHttpRequestFactory requestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        //ms
        factory.setConnectTimeout(RestTemplate_ConnectTimeout);
        factory.setReadTimeout(RestTemplate_ReadTimeout);
        return factory;
    }
}

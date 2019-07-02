package com.perfect.config.page;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class PageConfig implements WebMvcConfigurer {

    private static final String PAGE_PARAMETER_NAME = "page";
    private static final String SIZE_PARAMETER_NAME = "limit";

    @Value("${DEFAULT_PAGING_INDEX}")
    private int DEFAULT_PAGING_INDEX;

    @Value("${DEFAULT_PAGING_SIZE}")
    private int DEFAULT_PAGING_SIZE;

    @Value("${DEFAULT_MAX_PAGE_SIZE}")
    private int DEFAULT_MAX_PAGE_SIZE;

    @Value("${DEFAULT_PAGING_FALLBACK}")
    private int DEFAULT_PAGING_FALLBACK;

//    @Override
//    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
//        PageableHandlerMethodArgumentResolver resolver = new PageableHandlerMethodArgumentResolver();
//
//        resolver.setMaxPageSize(PMP_MAX_PAGE_SIZE);
//        argumentResolvers.add(resolver);
//    }


    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        PageableHandlerMethodArgumentResolver resolver = new PageableHandlerMethodArgumentResolver();
        int defaultPage = DEFAULT_PAGING_FALLBACK;
        int defaultPageSize = DEFAULT_PAGING_SIZE;
        int maxPageSize = DEFAULT_MAX_PAGE_SIZE;


        resolver.setOneIndexedParameters(true);
        resolver.setPrefix(StringUtils.EMPTY);
        resolver.setPageParameterName(PAGE_PARAMETER_NAME);
        resolver.setSizeParameterName(SIZE_PARAMETER_NAME);
        resolver.setMaxPageSize(maxPageSize);
        resolver.setFallbackPageable(PageRequest.of(defaultPage, defaultPageSize));

        argumentResolvers.add(resolver);
    }

//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController(Urls.LOGIN).setViewName(Views.SIGN_IN);
//        registry.addViewController(Urls.CREATE_DOCUMENT).setViewName(Views.DOCUMENT_EDIT);
//        registry.addRedirectViewController(Urls.ROOT, Urls.INDEX);
//    }

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(localeChangeInterceptor());
//    }
//
//    @Bean
//    public LocaleChangeInterceptor localeChangeInterceptor() {
//        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
//        localeChangeInterceptor.setParamName(LANG_PARAMETER_NAME);
//        return localeChangeInterceptor;
//    }
}

package com.perfect.config.security.ssl;

import org.springframework.context.annotation.Configuration;

@Configuration
public class SSLConfig {
    // 不使用ssl时，注释掉
    //    @Bean
    //    public Connector connector() {
    //        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
    //        connector.setScheme("http");
    //        connector.setPort(8080);
    //        connector.setSecure(false);
    //        connector.setRedirectPort(443);
    //        return connector;
    //    }
    //
    //    @Bean
    //    public TomcatServletWebServerFactory tomcatServletWebServerFactory(Connector connector) {
    //        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
    //            @Override
    //            protected void postProcessContext(Context context) {
    //                SecurityConstraint securityConstraint = new SecurityConstraint();
    //                securityConstraint.setUserConstraint("CONFIDENTIAL");
    //                SecurityCollection collection = new SecurityCollection();
    //                collection.addPattern("/*");
    //                securityConstraint.addCollection(collection);
    //                context.addConstraint(securityConstraint);
    //            }
    //        };
    //        tomcat.addAdditionalTomcatConnectors(connector);
    //        return tomcat;
    //    }
}

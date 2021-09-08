package me.seo.studywebmvc.config;

import me.seo.studywebmvc.dto.Person;
import me.seo.studywebmvc.interceptor.AnotherInterceptor;
import me.seo.studywebmvc.interceptor.GreetingInterceptor;
import me.seo.studywebmvc.interceptor.VisitTimeInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;


// spring mvc 인터페이스 확장
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // order를 정하지 않으면 add한 순서대로 적용
        // order정할 경우 낮은 값이 높은 우선순위
        // url 패턴 적용가능
        registry.addInterceptor(new VisitTimeInterceptor()).order(-2);
        registry.addInterceptor(new GreetingInterceptor()).order(0);
        registry.addInterceptor(new AnotherInterceptor())
                .addPathPatterns("/hi/*")
                .order(-1);
    }


    // 리소스 핸들러 default spring 자동 설정 but 임의로 하고 싶을 경우
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/mobile/**")
                .addResourceLocations("classpath:/mobile/")
                .setCacheControl(CacheControl.maxAge(10, TimeUnit.MINUTES))
                .resourceChain(true); // cache를 쓸지 말지 기능
                // classpath: classpath 기준으로 찾는다
                // but 아무것도 주지 않을 경우
                    // src/main/webapp 에서 찾는다

                //어떤 요청에 해당하는 resource 찾는 것 resolver
                    //캐싱, 인코딩(gzip, brotli), WebJar,
                //transformer 응답으로 내보낼 resource를 변경하는 방법
                    //캐싱, CSS 링크, HTML5 AppCache, ...

    }
    // message 컨버터 추가
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        WebMvcConfigurer.super.extendMessageConverters(converters);
    }
    /*
    이 경우 기본 메시지 컨버터들 사용하지 않음, 비추
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        WebMvcConfigurer.super.configureMessageConverters(converters);
    }
     */
    // 마샬러 등록
    @Bean
    public Jaxb2Marshaller jaxb2Marshaller(){
        Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        // root element를 알려주어야 한다
        jaxb2Marshaller.setPackagesToScan(Person.class.getPackageName());
        return jaxb2Marshaller;
    }
}

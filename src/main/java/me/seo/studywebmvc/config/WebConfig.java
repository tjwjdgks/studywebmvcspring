package me.seo.studywebmvc.config;

import me.seo.studywebmvc.interceptor.AnotherInterceptor;
import me.seo.studywebmvc.interceptor.GreetingInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


// spring mvc 인터페이스 확장
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // order를 정하지 않으면 add한 순서대로 적용
        // order정할 경우 낮은 값이 높은 우선순위
        // url 패턴 적용가능
        registry.addInterceptor(new GreetingInterceptor()).order(0);
        registry.addInterceptor(new AnotherInterceptor())
                .addPathPatterns("/hi/*")
                .order(-1);
    }
}

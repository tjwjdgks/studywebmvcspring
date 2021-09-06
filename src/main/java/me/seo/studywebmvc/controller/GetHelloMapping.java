package me.seo.studywebmvc.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// custom 에노테이션
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME) // runtime 까지 유지
@RequestMapping(method = RequestMethod.GET, value="/hellomapping") // mmeta 애노테이션
public @interface GetHelloMapping {
}

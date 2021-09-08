package me.seo.studywebmvc.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;


// 전역 컨트롤러
// 범위를 지정할 수 있음
@ControllerAdvice// (assignableTypes = {EventController.class, ApiController.class}) EventController와 ApiController에만 적용
public class BaseController {
}

package me.seo.studywebmvc.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UserForm {

    @Min(0)
    private Integer id;
    @NotBlank
    private String name;
}

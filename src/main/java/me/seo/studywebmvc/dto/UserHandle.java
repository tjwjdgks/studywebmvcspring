package me.seo.studywebmvc.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UserHandle {
    public interface  ValidateLimit{};
    public interface  ValidateName{};
    @Min(value = 0,groups = ValidateLimit.class)
    private Integer Id;
    @NotBlank(groups = ValidateName.class)
    private String name;
}

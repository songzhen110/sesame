package com.coder.common.validation;

import com.coder.common.annotation.Mobile;
import com.coder.common.util.validation.ValidationUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MobileValidator implements ConstraintValidator<Mobile, String> {

    @Override
    public void initialize(Mobile annotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // 校验手机
        return ValidationUtils.isMobile(value);
    }

}
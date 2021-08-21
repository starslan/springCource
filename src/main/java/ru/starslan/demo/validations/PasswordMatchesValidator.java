package ru.starslan.demo.validations;

import ru.starslan.demo.annotations.PasswordMatches;
import ru.starslan.demo.payload.request.SignRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        SignRequest signRequest = (SignRequest) value;
        return signRequest.getPassword().equals(signRequest.getConformPassword());
    }


    @Override
    public void initialize(PasswordMatches constraintAnnotation) {

    }
}

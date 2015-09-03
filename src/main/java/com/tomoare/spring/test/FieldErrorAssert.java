package com.tomoare.spring.test;

import java.util.Locale;
import javax.annotation.Nonnull;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import org.junit.Assert;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author tomoare
 */
public class FieldErrorAssert {

    private final MessageSource messageSource;

    private final String objectName;

    private final BindingResult result;

    private String fieldName;

    private String message;

    private String annotation;

    private int errorCount;

    public FieldErrorAssert(
            @Nonnull final MessageSource messageSource,
            @Nonnull final ModelAndView mav,
            @Nonnull final String objectName) {
        this.messageSource = messageSource;
        this.objectName = objectName;
        this.result = (BindingResult) mav.getModel().get("org.springframework.validation.BindingResult." + objectName);
    }

    public FieldErrorAssert setFieldName(String fieldName) {
        this.fieldName = fieldName;
        return this;
    }

    public FieldErrorAssert setMessage(String message) {
        this.message = message;
        return this;
    }

    public FieldErrorAssert setAnnotation(Class annotation) {
        this.annotation = annotation.getSimpleName();
        return this;
    }

    public FieldErrorAssert setAnnotation(String annotation) {
        this.annotation = annotation;
        return this;
    }

    public int getErrorCount() {
        return errorCount;
    }

    public FieldErrorAssert execAssert() {
        FieldError error = result.getFieldError(fieldName);
        Assert.assertThat(error.getCodes()[0], is(equalTo(annotation + "." + objectName + "." + fieldName)));
        Assert.assertThat(messageSource.getMessage(error, Locale.JAPANESE), is(equalTo(message)));
        errorCount++;
        return this;
    }

    public FieldErrorAssert clear() {
        this.fieldName = null;
        this.message = null;
        this.annotation = null;
        return this;
    }

    public FieldErrorAssert resetErrorCount() {
        errorCount = 0;
        return this;
    }

}

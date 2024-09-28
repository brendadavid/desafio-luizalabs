package com.desafio.luizalabs.config;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CustomResponseHandler extends DefaultErrorAttributes {

    private static final String MESSAGE = "message";

    @Override
    public Map<String, Object> getErrorAttributes(
            WebRequest webRequest,
            ErrorAttributeOptions options)
    {
        Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, options);

        var message = errorAttributes.get(MESSAGE);

        errorAttributes.clear();
        errorAttributes.put(MESSAGE, message);

        return errorAttributes;
    }
}

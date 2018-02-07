package com.propellerads.jupiter.annotation;

import com.propellerads.jupiter.extension.EnvironmentExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith(EnvironmentExtension.class)
public @interface Environment {

    EnvType value();

    enum EnvType {
        STAGING, PROD
    }

}

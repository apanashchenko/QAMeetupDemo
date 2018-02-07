package com.propellerads.jupiter.extension;

import com.propellerads.config.Config;
import com.propellerads.jupiter.annotation.Inject;
import org.junit.jupiter.api.extension.ExtensionConfigurationException;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DIFieldsExtension implements TestInstancePostProcessor {

    @Override
    public void postProcessTestInstance(Object testInstance, ExtensionContext context) throws Exception {
        List<Field> fields =  Arrays.stream(testInstance.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Inject.class))
                .collect(Collectors.toList());

        fields.stream()
                .filter(field -> field.getType() == Config.class)
                .forEach(field -> injectConfig(testInstance, field));
    }

    private void injectConfig(Object instance, Field field) {
        field.setAccessible(true);
        try {
            field.set(instance, Config.getInstance());
        } catch (IllegalAccessException e) {
            throw new ExtensionConfigurationException("Failed to inject parameter " + e.getMessage());
        }
    }
}

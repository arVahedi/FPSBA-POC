package com.db.lib.utility.mapping;

import com.db.lib.exception.ObjectMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@UtilityClass
public class ObjectMapperUtility {

    @SafeVarargs
    public void convertMapToObject(Map<String, Object> valuesMap, Object destinationObject, Class<? extends Annotation>... ignoreAnnotations) {

        Object sourceObject = new ObjectMapper().convertValue(valuesMap, destinationObject.getClass());
        Method[] destinationMethods = destinationObject.getClass().getMethods();
        List<Class<? extends Annotation>> ignoreAnnotationsList = Arrays.asList(ignoreAnnotations);

        valuesMap.forEach((key, value) -> {
            try {
                String setterMethodName = "set" + Character.toUpperCase(key.charAt(0)) + key.substring(1);
                String getterMethodName = "get" + Character.toUpperCase(key.charAt(0)) + key.substring(1);
                Optional<Method> setterMethod = Arrays.stream(destinationMethods).filter(method -> method.getName().equals(setterMethodName)).findFirst();
                Optional<Method> getterMethod = Arrays.stream(destinationMethods).filter(method -> method.getName().equals(getterMethodName)).findFirst();

                if (getterMethod.isPresent() && setterMethod.isPresent()) {
                    if (!ignoreAnnotationsList.isEmpty()) {
                        for (final Class<? extends Annotation> annotation : ignoreAnnotationsList) {
                            if (setterMethod.get().isAnnotationPresent(annotation) || getterMethod.get().isAnnotationPresent(annotation)) {
                                return;
                            }
                        }
                    }

                    if (value != null && Map.class.isAssignableFrom(value.getClass())) {
                        Object innerObject = getterMethod.get().invoke(destinationObject);
                        if (innerObject == null) {
                            innerObject = getterMethod.get().getReturnType().newInstance();
                            setterMethod.get().invoke(destinationObject, innerObject);
                        }
                        convertMapToObject((Map<String, Object>) value, innerObject);
                    } else {
                        setterMethod.get().invoke(destinationObject, getterMethod.get().invoke(sourceObject));
                    }
                }
            } catch (Exception ex) {
                throw new ObjectMappingException(ex.getMessage(), ex);
            }
        });
    }
}
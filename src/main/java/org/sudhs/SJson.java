package org.sudhs;

import org.sudhs.exception.JsonParseException;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Simple Json Parser implementation using Java reflection.
 *
 * author: sudhs
 */
public class SJson {
    /**
     * Converts json to java Object
     *
     * @param jsonString json string to be converted to object
     * @param className The class to which Json needs converted to.
     * @param <T>       The class
     * @return The object representation of json based on class provided.
     * @throws JsonParseException
     */

    public <T> T toObject(String jsonString, Class<T> className) throws JsonParseException {
        // 1. Iterate through class structure and get the values from the Json directly.
        T object ;
        try {
            object = className.newInstance();
        } catch(IllegalAccessException | InstantiationException e) {
            throw new JsonParseException(e.getMessage(), e.getCause());
        }

        for (Field field : className.getDeclaredFields()) {
            int indexOfFieldInJson = jsonString.indexOf(field.getName()) + field.getName().length()+1;
            int startIndexOfValue = jsonString.indexOf("\"", indexOfFieldInJson) + 1;
            int endIndexOfValue = jsonString.indexOf("\"", startIndexOfValue) ;
            Object value = jsonString.substring(startIndexOfValue, endIndexOfValue);
            System.out.println("Field names :: " + field.getName() + ":: , with Type :: " + field.getType() + ":: and with value :: " + value);

            if(isNotPrimitiveOrWrapper(field.getType())) {
                System.out.println("Lets handle Non Primitive Objects");
                value = toObject(jsonString, field.getType());
            }

            try {
                Method writeMethod = new PropertyDescriptor(field.getName(), className).getWriteMethod();
                writeMethod.invoke(object, value);
            } catch (IntrospectionException | IllegalAccessException | InvocationTargetException e) {
                throw new JsonParseException(e.getMessage(), e.getCause());
            }

        }
        System.out.println("Finally Object is :: " + object.toString());
        return object;
    }

    private boolean isNotPrimitiveOrWrapper(Class<?> t) {
        return  !(t.isPrimitive() ||
                t.equals(Integer.class) ||
                t.equals(String.class) ||
                t.equals(Boolean.class) ||
                t.equals(Character.class) ||
                t.equals(Byte.class) ||
                t.equals(Short.class) ||
                t.equals(Double.class) ||
                t.equals(Long.class) ||
                t.equals(Float.class));
    }

}

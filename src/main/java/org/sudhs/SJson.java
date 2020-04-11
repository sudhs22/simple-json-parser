package org.sudhs;

import org.sudhs.exception.JsonParseException;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Simple Json Parser implementation using Java reflection.
 * This is only going to take care of JSON having underlying objects which are only String.
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
        // 1. Parsed JSON in HashMap
        String[] jsonArray = jsonString
                                .substring(
                                        1,
                                        jsonString.indexOf("}"))
                                .split(",");
        System.out.println("jsonArray : " + Arrays.toString(jsonArray));
        Map<String, String> jsonKeyValue = Arrays
                                            .stream(jsonArray)
                                            .collect(Collectors.toMap(
                                                    x -> x.split(":")[0].replaceAll("\"", ""),
                                                    x-> x.split(":")[1].replace("\"", ""))
                                            );
        System.out.println(" The key value pairs of JSON ::" + jsonKeyValue);


        // 2. Got Map, now create Object, Set the setters methods.
        T object ;
        try {
            object = className.newInstance();
        } catch(IllegalAccessException | InstantiationException e) {
            throw new JsonParseException(e.getMessage(), e.getCause());
        }

        for (Field field : className.getDeclaredFields()) {
            try {
                Method method = new PropertyDescriptor(field.getName(), className).getWriteMethod();
                method.invoke(object, jsonKeyValue.get(field.getName()));
            } catch (IntrospectionException | IllegalAccessException | InvocationTargetException e) {
                throw new JsonParseException(e.getMessage(), e.getCause());
            }
        }
        return object;
    }

}

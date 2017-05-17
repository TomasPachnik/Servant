package sk.tomas.servant.core;

import sk.tomas.servant.annotation.Inject;
import sk.tomas.servant.annotation.Bean;
import sk.tomas.servant.annotation.Config;
import sk.tomas.servant.exception.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Tomas Pachnik on 27-Apr-17.
 */
public class Core {

    private static Map<String, Object> beans;

    public static void addConfiguration(Class<?> objectClass) {
        checkMap();
        try {
            build(objectClass);
            fill();
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | ServantException e) {
            e.printStackTrace();
        }
    }

    public static void AddToContext(Object o) {
        addToContext(o, null);
    }

    public static void addToContext(Object o, String name) {
        checkMap();
        if (name == null || name.isEmpty()) {
            beans.put(o.getClass().getSimpleName(), o);
        } else {
            beans.put(name, o);
        }
        try {
            fill();
        } catch (IllegalAccessException | BeanNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void build(Class<?> objectClass) throws WrongConfigClassException, InstantiationException, IllegalAccessException,
            MultipleBeansWithSameNameException, IllegalArgumentException, InvocationTargetException, CannotCreateBeanExcetion {
        checkConfigClass(objectClass);
        Object object = objectClass.newInstance();

        for (Method method : object.getClass().getMethods()) {
            if (method.isAnnotationPresent(Bean.class)) {
                Object generatedObject = method.invoke(object);

                String name;
                if (method.getAnnotation(Bean.class).value().equals("")) {
                    name = method.getName();
                } else {
                    name = method.getAnnotation(Bean.class).value();
                }
                if (generatedObject == null) {
                    throw new CannotCreateBeanExcetion(name);
                }
                beans.put(name, generatedObject);
            }
        }
    }

    private static void checkConfigClass(Class<?> objectClass) throws WrongConfigClassException {
        if (objectClass == null || !objectClass.isAnnotationPresent(Config.class)) {
            throw new WrongConfigClassException();
        }
    }

    private static void fill() throws IllegalAccessException, BeanNotFoundException {
        for (Map.Entry<String, Object> entry : beans.entrySet()) {
            for (Field field : entry.getValue().getClass().getDeclaredFields()) {
                if (field.isAnnotationPresent(Inject.class)) {
                    field.setAccessible(true);
                    if (field.getAnnotation(Inject.class).value().equals("")) {
                        field.set(entry.getValue(), getByName(field.getName()));
                    } else {
                        field.set(entry.getValue(), getByName(field.getAnnotation(Inject.class).value()));
                    }
                }
            }
        }
    }

    public static Object getByName(String name) throws BeanNotFoundException {
        if (beans.containsKey(name)) {
            return beans.get(name);
        } else {
            throw new BeanNotFoundException(name);
        }
    }

    public static void updateByName(String name, Object obj) throws ServantException {
        if (beans.containsKey(name)) {
            if (beans.get(name).getClass().equals(obj.getClass())) {
                beans.put(name, obj);
            } else
                throw new WrongObjectTypeException(name, beans.get(name).getClass(), obj.getClass());
        } else {
            throw new BeanNotFoundException(name);
        }
    }

    private static void checkMap() {
        if (beans == null) {
            beans = new HashMap<>();
        }
    }

}

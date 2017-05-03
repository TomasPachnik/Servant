package sk.tomas.servant.core.impl;

import sk.tomas.servant.annotation.Inject;
import sk.tomas.servant.annotation.Bean;
import sk.tomas.servant.annotation.Config;
import sk.tomas.servant.core.Core;
import sk.tomas.servant.exception.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Tomas Pachnik on 27-Apr-17.
 */
public class CoreImpl implements Core {

    private Map<String, Object> beans;

    public CoreImpl(Class<?> objectClass) throws ServantException {
        beans = new HashMap<>();
        try {
            build(objectClass);
            fill();
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new ServantException("Could not start Servant!", e);
        }
    }

    private void build(Class<?> objectClass) throws WrongConfigClassException, InstantiationException, IllegalAccessException,
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

    private void checkConfigClass(Class<?> objectClass) throws WrongConfigClassException {
        if (objectClass == null || !objectClass.isAnnotationPresent(Config.class)) {
            throw new WrongConfigClassException();
        }
    }

    private void fill() throws IllegalAccessException, BeanNotFoundException {
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

    @Override
    public Object getByName(String name) throws BeanNotFoundException {
        if (beans.containsKey(name)) {
            return beans.get(name);
        } else {
            throw new BeanNotFoundException(name);
        }
    }

}

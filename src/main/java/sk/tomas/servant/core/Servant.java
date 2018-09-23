package sk.tomas.servant.core;

import sk.tomas.servant.annotation.Inject;
import sk.tomas.servant.annotation.Bean;
import sk.tomas.servant.annotation.Config;
import sk.tomas.servant.annotation.PackageScan;
import sk.tomas.servant.exception.*;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;

/**
 * Created by Tomas Pachnik on 27-Apr-17.
 */
public class Servant {

    private static Map<String, Object> map;

    public static void addConfiguration(Class<?> objectClass) {
        checkMap();
        try {
            build(objectClass);
            fill();
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | ServantException | NoSuchMethodException | IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void addToContext(Object o) {
        addToContext(o, null);
    }

    public static void addToContext(Object o, String name) {
        checkMap();
        if (name == null || name.isEmpty()) {
            map.put(o.getClass().getSimpleName(), o);
        } else {
            map.put(name, o);
        }
        try {
            fill();
        } catch (IllegalAccessException | BeanNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void build(Class<?> objectClass) throws WrongConfigClassException, InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, CannotCreateBeanExcetion, NoSuchMethodException, IOException, ClassNotFoundException {
        checkConfigClass(objectClass);
        Object generatedObject = objectClass.newInstance();
        if (generatedObject == null) {
            throw new CannotCreateBeanExcetion(objectClass.getName());
        }
        scanPackage(objectClass);
        buildFromConfig(generatedObject);
    }

    private static void buildFromConfig(Object object) throws CannotCreateBeanExcetion, InvocationTargetException, IllegalAccessException {
        for (Method method : object.getClass().getMethods()) {
            if (method.isAnnotationPresent(Bean.class)) {
                Object generatedObject = method.invoke(object);

                String name = method.getAnnotation(Bean.class).value();
                if ("".equals(name)) {
                    name = method.getName();
                }
                if (generatedObject == null) {
                    throw new CannotCreateBeanExcetion(name);
                }
                map.put(name, generatedObject);
            }
        }
    }

    private static void scanPackage(Class<?> objectClass) throws IOException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, CannotCreateBeanExcetion {
        if (objectClass != null && objectClass.isAnnotationPresent(PackageScan.class)) {
            if (!objectClass.getAnnotation(PackageScan.class).value().equals("")) {
                Class[] classes = getClasses(objectClass.getAnnotation(PackageScan.class).value());
                for (Class clazz : classes) {
                    if (clazz.isAnnotationPresent(Bean.class)) {
                        Object generatedObject = clazz.newInstance();

                        String name = ((Bean) clazz.getAnnotation(Bean.class)).value();
                        if ("".equals(name)) {
                            name = clazz.getSimpleName().toLowerCase();
                        }
                        if (generatedObject == null) {
                            throw new CannotCreateBeanExcetion(name);
                        }
                        map.put(name, clazz.newInstance());
                    }
                }
            }
        }
    }

    private static Class[] getClasses(String packageName)
            throws ClassNotFoundException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert classLoader != null;
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        ArrayList<Class> classes = new ArrayList<>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        return classes.toArray(new Class[classes.size()]);
    }

    private static List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class> classes = new ArrayList<>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }

    /**
     * check if class itself is marked with @Config, or one of her annotations as marked with a @Config
     *
     * @param objectClass
     * @throws WrongConfigClassException
     */
    private static void checkConfigClass(Class<?> objectClass) throws WrongConfigClassException {
        if (objectClass != null) {
            if (objectClass.isAnnotationPresent(Config.class)) {
                return;
            }
            for (Annotation annotation : objectClass.getAnnotations()) {
                if (annotation.annotationType().isAnnotationPresent(Config.class)) {
                    return;
                }
            }
        }
        throw new WrongConfigClassException();
    }

    private static void fill() throws IllegalAccessException, BeanNotFoundException {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
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
        if (map.containsKey(name)) {
            return map.get(name);
        } else {
            throw new BeanNotFoundException(name);
        }
    }

    public static void updateByName(String name, Object obj) throws ServantException {
        if (map.containsKey(name)) {
            if (map.get(name).getClass().equals(obj.getClass())) {
                map.put(name, obj);
            } else
                throw new WrongObjectTypeException(name, map.get(name).getClass(), obj.getClass());
        } else {
            throw new BeanNotFoundException(name);
        }
    }

    private static void checkMap() {
        if (map == null) {
            map = new HashMap<>();
        }
    }

}

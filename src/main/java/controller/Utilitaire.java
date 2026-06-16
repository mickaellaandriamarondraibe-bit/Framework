package controller;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class Utilitaire {

    public static List<Class<?>> getClasses(String packageName)
            throws IOException, ClassNotFoundException {

        List<Class<?>> classes = new ArrayList<>();
        String packagePath = packageName.replace('.', '/');
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Enumeration<URL> resources = classLoader.getResources(packagePath);

        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            File directory = new File(resource.getFile());
            if (directory.exists()) {
                File[] files = directory.listFiles();
                if (files != null) {
                    for (File file : files) {
                        if (file.getName().endsWith(".class")) {
                            String simpleName = file.getName()
                                    .substring(0, file.getName().length() - 6);
                            String fullName = packageName + "." + simpleName;
                            Class<?> clazz = Class.forName(fullName);
                            classes.add(clazz);
                        }
                    }
                }
            }
        }
        return classes;
    }

    public static List<Class<?>> getClassesWithAnnotation(String packageName, String annotationName)
            throws IOException, ClassNotFoundException {
        List<Class<?>> result = new ArrayList<>();
        Class<?> annotationClass = Class.forName(annotationName);
        for (Class<?> clazz : getClasses(packageName)) {
            if (clazz.isAnnotationPresent((Class<? extends Annotation>) annotationClass)) {
                result.add(clazz);
            }
        }

        return result;
    }
}
package java_reflect_examples.UMLGenerateur;

import java.lang.reflect.*;
import java.util.*;

class UMLAnalyzer {

    public static List<UMLClass> analyzeClasses(Class<?>... classes) {
        List<UMLClass> umlClasses = new ArrayList<>();

        for (Class<?> cls : classes) {
            UMLClass umlClass = new UMLClass(cls.getSimpleName());

            // Analyse des attributs
            for (Field field : cls.getDeclaredFields()) {
                String cardinality = determineCardinality(field);
                umlClass.attributes.add(new UMLAttribute(
                        field.getName(),
                        field.getType().getSimpleName(),
                        cardinality
                ));
            }

            // Héritage
            if (cls.getSuperclass() != null && cls.getSuperclass() != Object.class) {
                umlClass.superclass = cls.getSuperclass().getSimpleName();
            }

            // Interfaces
            for (Class<?> interfaceCls : cls.getInterfaces()) {
                umlClass.interfaces.add(interfaceCls.getSimpleName());
            }

            // Relations
            for (Field field : cls.getDeclaredFields()) {
                String cardinality = determineCardinality(field);
                if (Collection.class.isAssignableFrom(field.getType())) {
                    ParameterizedType genericType = (ParameterizedType) field.getGenericType();
                    Class<?> genericClass = (Class<?>) genericType.getActualTypeArguments()[0];
                    umlClass.relations.add(new UMLRelation(
                            cls.getSimpleName(),
                            genericClass.getSimpleName(),
                            "association",
                            cardinality
                    ));
                } else if (!field.getType().isPrimitive() && !field.getType().getName().startsWith("java.")) {
                    umlClass.relations.add(new UMLRelation(
                            cls.getSimpleName(),
                            field.getType().getSimpleName(),
                            "association",
                            cardinality
                    ));
                }
            }

            umlClasses.add(umlClass);
        }

        return umlClasses;
    }

    private static String determineCardinality(Field field) {
        Class<?> fieldType = field.getType();

        if (Collection.class.isAssignableFrom(fieldType)) {
            return "*";
        } else if (!fieldType.isPrimitive() && !Modifier.isFinal(field.getModifiers())) {
            return "0..1";
        } else {
            return "1";
        }
    }
}

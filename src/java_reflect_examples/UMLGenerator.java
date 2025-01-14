package java_reflect_examples;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UMLGenerator {

    public static void generateYUML(Class<?> cls) {
        StringBuilder yumlSpec = new StringBuilder();
        List<String> relations = new ArrayList<>();
        List<String> inheritance = new ArrayList<>();

        analyzeClass(cls, relations, inheritance);

        // Héritage
        for (String inherit : inheritance) {
            yumlSpec.append(inherit).append("\n");
        }

        // Relations
        for (String relation : relations) {
            yumlSpec.append(relation).append("\n");
        }

        // Impression du résultat
        System.out.println(yumlSpec.toString());
    }

    private static void analyzeClass(Class<?> cls, List<String> relations, List<String> inheritance) {
      /*
        // Héritage
        Class<?> superclass = cls.getSuperclass();
        if (superclass != null && superclass != Object.class) {
            inheritance.add("[" + superclass.getSimpleName() + "]^[" + cls.getSimpleName() + "]");
        }

        // Interfaces implémentées
        for (Class<?> interfaceCls : cls.getInterfaces()) {
            inheritance.add("[" + interfaceCls.getSimpleName() + "]^-.-[" + cls.getSimpleName() + "]");
        }
*/
        // Champs
        for (Field field : cls.getDeclaredFields()) {
            Class<?> fieldType = field.getType();
            String cardinality = determineCardinality(field);

            // Cas : Champ est une collection (List, Set, etc.)
            if (Collection.class.isAssignableFrom(fieldType)) {
                ParameterizedType genericType = (ParameterizedType) field.getGenericType();
                Class<?> genericClass = (Class<?>) genericType.getActualTypeArguments()[0];
                relations.add("[" + cls.getSimpleName() + "]" + field.getName() + "-" + cardinality + ">[" + genericClass.getSimpleName() + "]");
            }
            // Cas : Champ est une autre classe utilisateur
            else if (!fieldType.isPrimitive() && !fieldType.getName().startsWith("java.")) {
                relations.add("[" + cls.getSimpleName() + "]" + field.getName() + "-" + cardinality + ">[" + fieldType.getSimpleName() + "]");
            }
        }

        for (Class<?> innerClass : cls.getDeclaredClasses()) {
            System.out.println(innerClass.getSimpleName());
            analyzeClass(innerClass, relations, inheritance);
        }


    }

    private static String determineCardinality(Field field) {
        Class<?> fieldType = field.getType();

        // Collection types
        if (Collection.class.isAssignableFrom(fieldType)) {
            return "*";
        }

        // ( 0..1)
        if (!fieldType.isPrimitive() && !Modifier.isFinal(field.getModifiers())) {
            return "0..1";
        }

        // Default to 1
        return "1";
    }

    public static void main(String[] args) {
        generateYUML(Room.class);
    }
}

class Room {
    List<Place> places; //  *
    Place mainPlace;    //  1
    final Place optionalPlace = null; // 0..1
}

class Place {
    String name;        //  1
    Room Room01;    //  0..1
}


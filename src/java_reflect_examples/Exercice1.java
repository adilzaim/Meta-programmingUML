package java_reflect_examples;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

interface interf1 {
}

public class Exercice1 {

    static class Inner1 {
    }

    static interface innerInterf1 {
    }

    private class Inner2 {
        class InnerInner1 implements innerInterf1, interf1 {
            private int value;

            public int getValue() {
                return value;
            }

            public void setValue(int value) {
                this.value = value;
            }
        }

        class InnerInner2 extends InnerInner1 {
        }
    }

    public static void classRepresentation(Class<?> cls) {
        StringBuilder sb = new StringBuilder();
        generateClassRepresentation(cls, sb, "");
        System.out.println(sb.toString());
    }

    private static void generateClassRepresentation(Class<?> cls, StringBuilder sb, String indent) {

        // Déclaration de la classe ou de l'interface
        if (cls.isInterface()) {
            sb.append(indent).append("interface ").append(cls.getSimpleName());
        } else {
            sb.append(indent).append(Modifier.toString(cls.getModifiers()))
                    .append(" class ").append(cls.getSimpleName());
        }

        // Héritage et implémentation d'interfaces
        Class<?>[] interfaces = cls.getInterfaces();
        if (interfaces.length > 0) {
            sb.append(" implements ");
            for (int i = 0; i < interfaces.length; i++) {
                sb.append(interfaces[i].getSimpleName());
                if (i < interfaces.length - 1) sb.append(", ");
            }
        }

        Class<?> superclass = cls.getSuperclass();
        if (superclass != null && superclass != Object.class) {
            sb.append(" extends ").append(superclass.getSimpleName());
        }

        sb.append(" {\n");

        // Champs (fields)
        Field[] fields = cls.getDeclaredFields();
        if (fields.length > 0) {
            sb.append(indent).append("    // Fields\n");
            for (Field field : fields) {
                sb.append(indent).append("    ")
                        .append(Modifier.toString(field.getModifiers()))
                        .append(" ").append(field.getType().getSimpleName())
                        .append(" ").append(field.getName()).append(";\n");
            }
        }

        // Constructeurs
        Constructor<?>[] constructors = cls.getDeclaredConstructors();
        if (constructors.length > 0) {
            sb.append(indent).append("    // Constructors\n");
            for (Constructor<?> constructor : constructors) {
                sb.append(indent).append("    ")
                        .append(Modifier.toString(constructor.getModifiers()))
                        .append(" ").append(cls.getSimpleName())
                        .append("(");
                Class<?>[] paramTypes = constructor.getParameterTypes();
                for (int i = 0; i < paramTypes.length; i++) {
                    sb.append(paramTypes[i].getSimpleName());
                    if (i < paramTypes.length - 1) sb.append(", ");
                }
                sb.append(");\n");
            }
        }

        // Méthodes
        Method[] methods = cls.getDeclaredMethods();
        if (methods.length > 0) {
            sb.append(indent).append("    // Methods\n");
            for (Method method : methods) {
                sb.append(indent).append("    ")
                        .append(Modifier.toString(method.getModifiers()))
                        .append(" ").append(method.getReturnType().getSimpleName())
                        .append(" ").append(method.getName())
                        .append("(");
                Class<?>[] paramTypes = method.getParameterTypes();
                for (int i = 0; i < paramTypes.length; i++) {
                    sb.append(paramTypes[i].getSimpleName());
                    if (i < paramTypes.length - 1) sb.append(", ");
                }
                sb.append(");\n");
            }
        }

        // Classes internes
        Class<?>[] innerClasses = cls.getDeclaredClasses();
        if (innerClasses.length > 0) {
            sb.append(indent).append("    // Inner Classes\n");
            for (Class<?> innerClass : innerClasses) {
                generateClassRepresentation(innerClass, sb, indent + "    ");
            }
        }

        sb.append(indent).append("}\n");
    }

    public static void main(String[] args) {
        classRepresentation(Exercice1.class);
    }
}

package java_reflect_examples.UMLGenerateurVisiteur;

import java.lang.reflect.*;
import java.util.*;
import java.lang.reflect.*;
import java.util.*;

import java.lang.reflect.*;
import java.util.*;

class YuModelBuilder {

    public static List<YuElement> buildModel(Class<?>... classes) {
        List<YuElement> elements = new ArrayList<>();

        for (Class<?> cls : classes) {
            YuClass yuClass = new YuClass(cls.getSimpleName(), Modifier.isStatic(cls.getModifiers()), cls.isInterface());

            if (cls.getSuperclass() != null && cls.getSuperclass() != Object.class) {
                yuClass.superclass = cls.getSuperclass().getSimpleName();
            }

            for (Class<?> interfaceCls : cls.getInterfaces()) {
                yuClass.implementedInterfaces.add(interfaceCls.getSimpleName());
            }

            boolean hasAssociations = false;
            for (Field field : cls.getDeclaredFields()) {
                YuAssoc assoc = createAssociation(yuClass, field);
                if (assoc != null) {
                    yuClass.associations.add(assoc);
                    elements.add(assoc);
                    hasAssociations = true;
                }
            }
            if (!hasAssociations) {
                elements.add(new YuNoAssoc(yuClass));
            }
            elements.add(yuClass);
        }
        return elements;
    }

    private static YuAssoc createAssociation(YuClass source, Field field) {
        if (isAssociation(field)) {
            YuClass target = determineTargetClass(field);
            YuCardinality cardinality = determineCardinality(field);

            if (isComposition(field)) {
                return new YuComposition(source, target, cardinality);
            } else if (isAggregation(field)) {
                return new YuAggregation(source, target, cardinality);
            } else {
                return new YuSimpleAssoc(source, target, cardinality);
            }
        }
        return null;
    }

    private static boolean isAssociation(Field field) {
        return Collection.class.isAssignableFrom(field.getType()) ||
                (!field.getType().isPrimitive() && !field.getType().getName().startsWith("java."));
    }

    private static YuClass determineTargetClass(Field field) {
        if (Collection.class.isAssignableFrom(field.getType())) {
            ParameterizedType genericType = (ParameterizedType) field.getGenericType();
            Class<?> genericClass = (Class<?>) genericType.getActualTypeArguments()[0];
            return new YuClass(genericClass.getSimpleName(), false, genericClass.isInterface());
        } else {
            return new YuClass(field.getType().getSimpleName(), false, field.getType().isInterface());
        }
    }

    private static boolean isComposition(Field field) {
        return field.getName().contains("composed");
    }

    private static boolean isAggregation(Field field) {
        return field.getName().contains("aggregated");
    }

    private static YuCardinality determineCardinality(Field field) {
        if (Collection.class.isAssignableFrom(field.getType())) {
            return new YuCardinality(0, Integer.MAX_VALUE);
        } else if (!field.getType().isPrimitive() && !Modifier.isFinal(field.getModifiers())) {
            return new YuCardinality(0, 1);
        } else {
            return new YuCardinality(1, 1);
        }
    }
}

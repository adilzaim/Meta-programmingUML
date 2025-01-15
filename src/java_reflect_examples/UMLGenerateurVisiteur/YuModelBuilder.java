package java_reflect_examples.UMLGenerateurVisiteur;

import java.lang.reflect.*;
import java.util.*;

class YuModelBuilder {

    public static List<YuElement> buildModel(Class<?>... classes) {
        List<YuElement> elements = new ArrayList<>();

        for (Class<?> cls : classes) {
            // Création de la classe YuClass
            YuClass yuClass = new YuClass(cls.getSimpleName(), Modifier.isStatic(cls.getModifiers()), cls.isInterface());

            // Détecter la classe parente (héritage)
            if (cls.getSuperclass() != null && cls.getSuperclass() != Object.class) {
                yuClass.superclass = cls.getSuperclass().getSimpleName();
            }

            // Détecter les interfaces implémentées
            for (Class<?> interfaceCls : cls.getInterfaces()) {
                yuClass.implementedInterfaces.add(interfaceCls.getSimpleName());
            }

            boolean hasAssociations = false;

            // Parcourir les champs pour détecter les relations
            for (Field field : cls.getDeclaredFields()) {
                YuAssoc assoc = createAssociation(yuClass, field);
                if (assoc != null) {
                    yuClass.associations.add(assoc); // Ajouter la relation à la classe
                    elements.add(assoc); // Ajouter la relation au modèle
                    hasAssociations = true;
                }
            }

            // Ajouter un YuNoAssoc si la classe n'a pas de relations
            if (!hasAssociations) {
                elements.add(new YuNoAssoc(yuClass));
            }

            // Ajouter la classe au modèle
            elements.add(yuClass);
        }

        return elements;
    }

    /**
     * Crée une relation entre deux classes en fonction du champ.
     */
    private static YuAssoc createAssociation(YuClass source, Field field) {
        if (isAssociation(field)) {
            YuClass target = determineTargetClass(field); // Déterminer la classe cible
            YuCardinality cardinality = determineCardinality(field); // Déterminer la cardinalité

            // Déterminer le type de relation
            if (isComposition(field)) {
                return new YuComposition(source, target, cardinality); // Composition
            } else if (isAggregation(field)) {
                return new YuAggregation(source, target, cardinality); // Agrégation
            } else {
                return new YuSimpleAssoc(source, target, cardinality); // Association simple
            }
        }
        return null;
    }

    /**
     * Vérifie si un champ est une association.
     */
    private static boolean isAssociation(Field field) {
        return Collection.class.isAssignableFrom(field.getType()) || // Collections
                (!field.getType().isPrimitive() && !field.getType().getName().startsWith("java.")); // Objets non primitifs
    }

    /**
     * Détermine la classe cible d'une relation.
     */
    private static YuClass determineTargetClass(Field field) {
        if (Collection.class.isAssignableFrom(field.getType())) {
            // Si c'est une collection, obtenir le type générique
            ParameterizedType genericType = (ParameterizedType) field.getGenericType();
            Class<?> genericClass = (Class<?>) genericType.getActualTypeArguments()[0];
            return new YuClass(genericClass.getSimpleName(), false, genericClass.isInterface());
        } else {
            // Sinon, utiliser directement le type du champ
            return new YuClass(field.getType().getSimpleName(), false, field.getType().isInterface());
        }
    }

    /**
     * Vérifie si un champ représente une composition.
     */
    private static boolean isComposition(Field field) {
        return field.getName().contains("composed"); // Exemple basé sur le nom du champ
    }

    /**
     * Vérifie si un champ représente une agrégation.
     */
    private static boolean isAggregation(Field field) {
        return field.getName().contains("aggregated"); // Exemple basé sur le nom du champ
    }

    /**
     * Détermine la cardinalité d'une relation.
     */
    private static YuCardinality determineCardinality(Field field) {
        if (Collection.class.isAssignableFrom(field.getType())) {
            return new YuCardinality(0, Integer.MAX_VALUE); // 0..*
        } else if (!field.getType().isPrimitive() && !Modifier.isFinal(field.getModifiers())) {
            return new YuCardinality(0, 1); // 0..1
        } else {
            return new YuCardinality(1, 1); // 1
        }
    }
}

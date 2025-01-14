package java_reflect_examples.UMLGenerateur;

import java.util.List;

class YUMLGenerator {

    public static String generateYUML(List<UMLClass> umlClasses) {
        StringBuilder yuml = new StringBuilder();

        for (UMLClass umlClass : umlClasses) {
            // Héritage
            if (umlClass.superclass != null) {
                yuml.append("[").append(umlClass.superclass).append("]^[").append(umlClass.name).append("]\n");
            }
            for (String interfaceName : umlClass.interfaces) {
                yuml.append("[").append(interfaceName).append("]^-.-[").append(umlClass.name).append("]\n");
            }

            // Relations
            for (UMLRelation relation : umlClass.relations) {
                yuml.append("[").append(relation.source).append("]")
                        .append("-").append(relation.cardinality).append(">")
                        .append("[").append(relation.target).append("]\n");
            }
        }

        return yuml.toString();
    }
}

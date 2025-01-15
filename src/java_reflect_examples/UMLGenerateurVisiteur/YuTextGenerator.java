package java_reflect_examples.UMLGenerateurVisiteur;

import java.util.HashSet;
import java.util.Set;
class YuTextGenerator implements YuVisitor {
    StringBuilder yumlOutput = new StringBuilder();
    Set<String> generatedElements = new HashSet<>(); // Pour éviter les doublons

    @Override
    public void visit(YuClass yuClass) {
        if (generatedElements.contains(yuClass.name)) return; // Pour éviter les doublons
        generatedElements.add(yuClass.name);

        yumlOutput.append("[").append(yuClass.name);
        if (yuClass.isInterface) {
            yumlOutput.append("<<interface>>");
        }
        yumlOutput.append("]\n");

        if (yuClass.superclass != null) {
            yumlOutput.append("[").append(yuClass.superclass).append("]^[").append(yuClass.name).append("]\n");
        }

        for (String implementedInterface : yuClass.implementedInterfaces) {
            yumlOutput.append("[").append(implementedInterface).append("]^-.-[").append(yuClass.name).append("]\n");
        }
    }

    @Override
    public void visit(YuBiAssoc biAssoc) {
        String relation = "[" + biAssoc.source.name + "]-" +
                biAssoc.cardinality + "<->[" + biAssoc.target.name + "]";
        if (generatedElements.contains(relation)) return;
        generatedElements.add(relation);

        yumlOutput.append(relation).append("\n");
    }

    @Override
    public void visit(YuSimpleAssoc simpleAssoc) {
        String relation = "[" + simpleAssoc.source.name + "]-" +
                simpleAssoc.cardinality + "->[" + simpleAssoc.target.name + "]";
        if (generatedElements.contains(relation)) return;
        generatedElements.add(relation);

        yumlOutput.append(relation).append("\n");
    }

    @Override
    public void visit(YuAggregation aggregation) {
        String relation = "[" + aggregation.source.name + "]-" +
                aggregation.cardinality + "o->[" + aggregation.target.name + "]";
        if (generatedElements.contains(relation)) return;
        generatedElements.add(relation);

        yumlOutput.append(relation).append("\n");
    }

    @Override
    public void visit(YuComposition composition) {
        String relation = "[" + composition.source.name + "]-" +
                composition.cardinality + "*>[" + composition.target.name + "]";
        if (generatedElements.contains(relation)) return;
        generatedElements.add(relation);

        yumlOutput.append(relation).append("\n");
    }

    @Override
    public void visit(YuNoAssoc noAssoc) {
        if (generatedElements.contains(noAssoc.source.name)) return;
        generatedElements.add(noAssoc.source.name);

        yumlOutput.append("[").append(noAssoc.source.name).append("]\n");
    }

    public String getOutput() {
        return yumlOutput.toString();
    }
}

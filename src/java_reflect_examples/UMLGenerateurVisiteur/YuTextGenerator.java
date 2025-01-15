package java_reflect_examples.UMLGenerateurVisiteur;

import java.util.HashSet;
import java.util.Set;

class YuTextGenerator implements YuVisitor {
    StringBuilder yumlOutput = new StringBuilder();
    Set<String> generatedElements = new HashSet<>(); // Pour éviter les doublons

    @Override
    public void visit(YuClass yuClass) {
        if (generatedElements.contains(yuClass.name)) return; // Éviter la duplication
        generatedElements.add(yuClass.name);

        // Ajouter la classe au diagramme
        yumlOutput.append("[").append(yuClass.name).append("]\n");
    }

    @Override
    public void visit(YuBiAssoc biAssoc) {
        // Association bidirectionnelle
        String relation = "[" + biAssoc.source.name + "]-" +
                biAssoc.cardinality + "<->[" + biAssoc.target.name + "]";
        if (generatedElements.contains(relation)) return;
        generatedElements.add(relation);

        yumlOutput.append(relation).append("\n");
    }

    @Override
    public void visit(YuSimpleAssoc simpleAssoc) {
        // Association simple
        String relation = "[" + simpleAssoc.source.name + "]-" +
                simpleAssoc.cardinality + ">[" + simpleAssoc.target.name + "]";
        if (generatedElements.contains(relation)) return;
        generatedElements.add(relation);

        yumlOutput.append(relation).append("\n");
    }

    @Override
    public void visit(YuAggregation aggregation) {
        // Agrégation : <>-books*>
        String relation = "[" + aggregation.source.name + "]<>" +
                "-books*>[" + aggregation.target.name + "]";
        if (generatedElements.contains(relation)) return;
        generatedElements.add(relation);

        yumlOutput.append(relation).append("\n");
    }

    @Override
    public void visit(YuComposition composition) {
        // Composition : ++-0..*>
        String relation = "[" + composition.source.name + "]++" +
                "-0..*>[" + composition.target.name + "]";
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

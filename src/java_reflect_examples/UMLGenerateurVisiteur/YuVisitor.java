package java_reflect_examples.UMLGenerateurVisiteur;

interface YuVisitor {
    void visit(YuClass yuClass);
    void visit(YuBiAssoc biAssoc);
    void visit(YuSimpleAssoc simpleAssoc);
    void visit(YuAggregation aggregation);
    void visit(YuComposition composition);
    void visit(YuNoAssoc noAssoc);
}

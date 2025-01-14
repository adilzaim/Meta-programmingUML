package java_reflect_examples.UMLGenerateurVisiteur;

import java.util.ArrayList;
import java.util.List;
class YuClass implements YuElement {
    String name;
    boolean isStatic;
    boolean isInterface;
    String superclass;
    List<String> implementedInterfaces = new ArrayList<>();
    List<YuAssoc> associations = new ArrayList<>();

    public YuClass(String name, boolean isStatic, boolean isInterface) {
        this.name = name;
        this.isStatic = isStatic;
        this.isInterface = isInterface;
    }

    @Override
    public void accept(YuVisitor visitor) {
        visitor.visit(this);
    }
}

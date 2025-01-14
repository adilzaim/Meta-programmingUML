package java_reflect_examples.UMLGenerateur;

import java.util.ArrayList;
import java.util.List;

class UMLClass {
    String name;
    List<UMLAttribute> attributes = new ArrayList<>();
    List<UMLRelation> relations = new ArrayList<>();
    List<String> interfaces = new ArrayList<>();
    String superclass;

    public UMLClass(String name) {
        this.name = name;
    }
}

class UMLAttribute {
    String name;
    String type;
    String cardinality;

    public UMLAttribute(String name, String type, String cardinality) {
        this.name = name;
        this.type = type;
        this.cardinality = cardinality;
    }
}

class UMLRelation {
    String source;
    String target;
    String type; // "association", "inheritance", "aggregation", "composition"
    String cardinality;

    public UMLRelation(String source, String target, String type, String cardinality) {
        this.source = source;
        this.target = target;
        this.type = type;
        this.cardinality = cardinality;
    }
}

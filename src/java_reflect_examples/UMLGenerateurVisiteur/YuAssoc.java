package java_reflect_examples.UMLGenerateurVisiteur;

abstract class YuAssoc implements YuElement {
    YuClass source;
    YuClass target;
    YuCardinality cardinality;

    public YuAssoc(YuClass source, YuClass target, YuCardinality cardinality) {
        this.source = source;
        this.target = target;
        this.cardinality = cardinality;
    }
}

class YuBiAssoc extends YuAssoc {
    public YuBiAssoc(YuClass source, YuClass target, YuCardinality cardinality) {
        super(source, target, cardinality);
    }

    @Override
    public void accept(YuVisitor visitor) {
        visitor.visit(this);
    }
}

class YuSimpleAssoc extends YuBiAssoc {
    public YuSimpleAssoc(YuClass source, YuClass target, YuCardinality cardinality) {
        super(source, target, cardinality);
    }

    @Override
    public void accept(YuVisitor visitor) {
        visitor.visit(this);
    }
}

class YuAggregation extends YuBiAssoc {
    public YuAggregation(YuClass source, YuClass target, YuCardinality cardinality) {
        super(source, target, cardinality);
    }

    @Override
    public void accept(YuVisitor visitor) {
        visitor.visit(this);
    }
}

class YuComposition extends YuBiAssoc {
    public YuComposition(YuClass source, YuClass target, YuCardinality cardinality) {
        super(source, target, cardinality);
    }

    @Override
    public void accept(YuVisitor visitor) {
        visitor.visit(this);
    }
}

class YuNoAssoc extends YuAssoc {
    public YuNoAssoc(YuClass source) {
        super(source, null, null);
    }

    @Override
    public void accept(YuVisitor visitor) {
        visitor.visit(this);
    }
}

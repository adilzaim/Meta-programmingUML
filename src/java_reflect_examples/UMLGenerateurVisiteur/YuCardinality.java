package java_reflect_examples.UMLGenerateurVisiteur;
class YuCardinality {
    int min;
    int max;

    public YuCardinality(int min, int max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public String toString() {
        if (min == max) {
            return String.valueOf(min);
        } else if (max == Integer.MAX_VALUE) {
            return min + "..*";
        } else {
            return min + ".." + max;
        }
    }
}


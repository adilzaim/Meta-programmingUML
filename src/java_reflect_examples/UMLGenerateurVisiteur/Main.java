package java_reflect_examples.UMLGenerateurVisiteur;


import java.util.List;

class ville{

}
class Library extends ville{
    List<Book> books; // Cardinalité 0..*
    Address address;  // Cardinalité 1
}

class Book {
    String title;       // Cardinalité 1
    Publisher publisher; // Cardinalité 0..1
}

class Address {
    String street;      // Cardinalité 1
    String city;        // Cardinalité 1
}

class Publisher {
    String name;        // Cardinalité 1
}

public class Main {
    public static void main(String[] args) {
        List<YuElement> model = YuModelBuilder.buildModel(Library.class, Book.class, Address.class, Publisher.class);

        YuTextGenerator generator = new YuTextGenerator();
        for (YuElement element : model) {
            element.accept(generator);
        }

        System.out.println(generator.getOutput());
    }
}



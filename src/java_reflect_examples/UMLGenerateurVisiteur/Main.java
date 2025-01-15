package java_reflect_examples.UMLGenerateurVisiteur;


import java.util.List;

class Ville{

}
class Library extends Ville{
    List<Book> aggregatedBooks; // card 0..*
    Address composedAddress;  // card  1
}

class Book {
    String title;       // card 1
    Publisher publisher; // card 0..1
}

class Address {
    String street;      // card 1
    String city;        // card1
}

class Publisher {
    String name;        //card 1
}
public class Main {
    public static void main(String[] args) {
        List<YuElement> model = YuModelBuilder.buildModel(
                Library.class,
                Book.class,
                Address.class,
                Ville.class,
                Publisher.class
        );

        YuTextGenerator generator = new YuTextGenerator();
        for (YuElement element : model) {
            element.accept(generator);
        }

        System.out.println(generator.getOutput());
    }
}



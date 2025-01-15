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
        // Étape 1 : Analyser les classes et construire le modèle UML
        List<YuElement> model = YuModelBuilder.buildModel(
                Library.class,
                Book.class,
                Address.class,
                Ville.class,
                Publisher.class
        );

        // Étape 2 : Parcourir le modèle avec le visiteur
        YuTextGenerator generator = new YuTextGenerator();
        for (YuElement element : model) {
            element.accept(generator);
        }

        // Étape 3 : Afficher le résultat yUML
        System.out.println(generator.getOutput());
    }
}



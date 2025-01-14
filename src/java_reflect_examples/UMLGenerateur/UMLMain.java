package java_reflect_examples.UMLGenerateur;

import java.util.List;

class Library {
    List<Book> books;
    Address address;
}

class Book {
    String title;
    Publisher publisher;
}

class Address {
    String street;
}

class Publisher {
    String name;
}

public class UMLMain {
    public static void main(String[] args) {
        // Analyse des classes
        List<UMLClass> umlClasses = UMLAnalyzer.analyzeClasses(Library.class, Book.class, Address.class, Publisher.class);

        // Génération du code yUML
        String yumlCode = YUMLGenerator.generateYUML(umlClasses);

        // Affichage
        System.out.println(yumlCode);
    }
}

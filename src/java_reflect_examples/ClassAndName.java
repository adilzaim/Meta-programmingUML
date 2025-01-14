package java_reflect_examples;

import static org.junit.Assert.assertTrue;

import java.util.Map;

public class ClassAndName {

	// from https://javahowtodoit.wordpress.com/2014/09/09/java-lang-class-what-is-the-difference-between-class-getname-class-getcanonicalname-and-class-getsimplename/
	/*
		getName() – returns the name of the entity (class, interface, array class, primitive type, or void) represented by this Class object, as a String.
		getCanonicalName() – returns the canonical name of the underlying class as defined by the Java Language Specification.
		getSimpleName() – the simple name of the underlying class as given in the source code
	*/
	public static void main(String [] args) {
		// Primitive type
		assertTrue(int.class.getName().equals("int"));          // -> int
		assertTrue(int.class.getCanonicalName().equals("int"));	// -> int
		assertTrue(int.class.getSimpleName().equals("int"));	// -> int
		
		 
		// Standard class
		System.out.println(Integer.class.getName());          // -> java.lang.Integer
		System.out.println(Integer.class.getCanonicalName()); // -> java.lang.Integer
		System.out.println(Integer.class.getSimpleName());    // -> Integer
		 
		// Inner class
		System.out.println(Map.Entry.class.getName());          // -> java.util.Map$Entry
		System.out.println(Map.Entry.class.getCanonicalName()); // -> java.util.Map.Entry
		try {
			assertTrue(Class.forName(Map.Entry.class.getName()) == Map.Entry.class);
		} catch (ClassNotFoundException e1) {
			assertTrue(false);
		}
		System.out.println(Map.Entry.class.getSimpleName());    // -> Entry     

		try {
			System.out.println(Class.forName(Map.Entry.class.getName()));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		 
		// Anonymous inner class
		Class<?> anonymousInnerClass = new Cloneable() {}.getClass();
		System.out.println(anonymousInnerClass.getName());          // -> somepackage.SomeClass$1
		System.out.println(anonymousInnerClass.getCanonicalName()); // -> null
		System.out.println(anonymousInnerClass.getSimpleName());    // -> // An empty string
		 
		try {
			System.out.println(Class.forName(anonymousInnerClass.getName()));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		 
		// Array of primitives
		Class<?> primitiveArrayClass = new int[0].getClass();
		System.out.println(primitiveArrayClass.getName());          // -> [I
		System.out.println(primitiveArrayClass.getCanonicalName()); // -> int[]
		System.out.println(primitiveArrayClass.getSimpleName());    // -> int[]
		
		try {
			System.out.println(Class.forName(primitiveArrayClass.getName()));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		 

		 
		// Array of objects
		Class<?> objectArrayClass = new Integer[0].getClass();
		System.out.println(objectArrayClass.getName());          // -> [Ljava.lang.Integer;
		System.out.println(objectArrayClass.getCanonicalName()); // -> java.lang.Integer[]
		System.out.println(objectArrayClass.getSimpleName());    // -> Integer[]

		try {
			System.out.println(Class.forName(objectArrayClass.getName()));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		 
	}
	
	
}

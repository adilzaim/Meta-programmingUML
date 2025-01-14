package java_reflect_examples;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;


class ManagingClassTest {
	
	class A { }
	
	@Test
	void testA() {
		A a = new A();
		assertTrue(A.class == a.getClass());
		Class<A> claff = A.class;
		Class<?> clamm = A.class;
		assertTrue(claff == clamm);
		assertFalse(claff.getName().equals("A"));
		System.out.println(claff.getName());
		assertTrue(claff.getName().equals("java_reflect_examples.ManagingClassTest$A"));
	}
	@Test
	void testPrimitive() {
		int i = 2;
		assertTrue(int.class.getName().equals("int"));
		Integer n = i;
		assertTrue(n.getClass() == Integer.class);
		assertTrue(n.getClass().getName().equals("java.lang.Integer"));
		assertTrue(char[][].class.getName().equals("[[C"));
	}
	@Test
	void testClassForName() throws ClassNotFoundException {
System.out.println(A.class.getName());
		assertTrue(A.class == Class.forName("java_reflect_examples.ManagingClassTest$A"));
		try {
			// Class.forName cannot be used for primitive types
			Class.forName("int");
		} catch (ClassNotFoundException e) {
			assertTrue(true);
		}
		assertTrue(int[].class == Class.forName("[I"));
		assertTrue(Integer.class == Class.forName("java.lang.Integer"));
	}

	class B {
		class Inner1 extends A {}
	}

	@Test
	void testUnicityA() {
		B b = new B();
		B.Inner1 in1 = b.new Inner1();
		Class<?> sup = B.Inner1.class.getSuperclass();
		Class<?> supbis = in1.getClass().getSuperclass();
		assertTrue(sup == A.class);
		assertTrue(supbis == A.class);
		Set<Class<?>> s = new HashSet<>();
		s.add(sup);
		s.add(supbis);
		assertTrue(s.size() == 1);
	}
	
	
	@Test
	void testPrimitiveTypes() {
		assertTrue(int.class.getTypeName().equals("int"));
		assertTrue(double.class.getTypeName().equals("double"));
		assertTrue(void.class.getTypeName().equals("void"));		
	}
	
	enum Color { white, black, red };
	
	@Test
	void testReferenceType() {
		B b = new B();
		System.out.println(b.getClass().getTypeName());
		assertTrue(b.getClass().getTypeName().equals("java_reflect_examples.ManagingClassTest$B"));
		Color c = Color.white;		
		assertTrue(c.getClass().getTypeName().equals("java_reflect_examples.ManagingClassTest$Color"));
		int [] itab = new int[5];
		assertTrue(itab.getClass().getTypeName().equals("int[]"));
		List<B> lbs = new ArrayList<B>();
		Class<?> arrcls = ArrayList.class;
		assertTrue(arrcls == lbs.getClass());
		assertTrue(arrcls.getTypeName().equals("java.util.ArrayList"));
	}

	class P<E> extends ArrayList<E> {		
	}
	
	@Test
	void testParameterizedType() {
		assertTrue(P.class.getSuperclass() == (((ParameterizedType)P.class.getGenericSuperclass()).getRawType()));
		assertTrue((((ParameterizedType)P.class.getGenericSuperclass()).getActualTypeArguments()[0].getTypeName().equals("E")));
	}	
	
}

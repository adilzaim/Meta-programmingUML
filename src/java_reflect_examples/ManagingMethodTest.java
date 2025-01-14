package java_reflect_examples;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

class ManagingMethodTest {

	private class A {
		public void p() {
		}

		protected void r() {
		}

		private void v() {
		}
	}

	private class B extends A {
		private int i;

		public int getI() {
			return i;
		}

		private void setI(int i) {
			this.i = i;
		}
	}

	@Test
	void testGetDeclaredMethods() {
		Class<?> clsA = A.class;
		Method[] meths = clsA.getDeclaredMethods();
		assertTrue(meths.length == 3);
		List<String> names = Arrays.asList("p", "r", "v");
		for (Method m : meths) {
			assertTrue(names.contains(m.getName()));
		}
	}

	@Test
	void testGetMethods() {
		int objnb = A.class.getSuperclass().getMethods().length;
		int anb = A.class.getMethods().length;
		assertTrue(anb == objnb + 1); // excludes protected and private of A
		int anb2 = A.class.getDeclaredMethods().length;
		assertTrue(anb2 == 3); // A only but with private protected
		int bnb = B.class.getMethods().length;
		assertTrue(bnb == anb + 1); // excludes protected and private of B
	}

	@Test
	void testMethodInvoking() {
		B b = new B();
		try {
			Method m = B.class.getDeclaredMethod("setI", int.class);
			m.invoke(b, 1);
			assertTrue(b.getI() == 1);
			m = B.class.getDeclaredMethod("getI");
			int i = (int) m.invoke(b);
			assertTrue(i == 1);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			assertTrue(false);
		}
	}

	@Test
	void testMethodSearching() {
		B b = new B();
		try {
			Field fi = B.class.getDeclaredField("i");
			Class<?> fit = fi.getType();
			String name = "setI";
			List<Method> founds = new ArrayList<>();
			for (Method m : B.class.getDeclaredMethods())
				if (name.equals(m.getName()))
					if (m.getParameters().length == 1) {
						Class<?> pt0 = m.getParameterTypes()[0];
						if (pt0 == fit)
							founds.add(m);
					}
			assert (founds.size() == 1);
			Method setter = founds.get(0);
			setter.invoke(b, 1);
			assertTrue(b.getI() == 1);
		} catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchFieldException e) {
			assertTrue(false);
		}
	}


}

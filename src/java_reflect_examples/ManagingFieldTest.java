package java_reflect_examples;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import org.junit.jupiter.api.Test;

class ManagingFieldTest {
	
	private class A { }
	
	class F {
		public int i;
		private int i2;
		public int getI() { return i; }
		public int getI2() { return i2; }
	}
	
	@Test
	void testField1() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		F f = new F();
		Class<?> cls = f.getClass();
		Field fi = cls.getField("i");
		fi.set(f, 2);
		assertTrue(f.getI() == (int)fi.get(f));
		assertTrue(f.getI() == 2);
		
		Field fi2 = cls.getDeclaredField("i2");
		// i2 is private
		fi2.setAccessible(true);
		fi2.set(f, 2);
		assertTrue(f.getI2() == (int)fi2.get(f));
		assertTrue(f.getI2() == 2);
	}
	
	class F2 {
		private int [] itab;
	}
	
	void testField2() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		F2 f = new F2();
		Class<?> cls = f.getClass();
		Field fi = cls.getDeclaredField("itab");
		// itab is private
		fi.setAccessible(true);
		fi.set(f, new int [4]);
		int [] tab = (int[]) fi.get(f);
		tab[0] = 1;
		assertTrue(((int[]) fi.get(f))[0] == 1);
	}
	
	void testField3() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Class<?> cls = F.class;
		Field fi2 = cls.getDeclaredField("i2");
		int m = fi2.getModifiers();
		assertTrue(Modifier.isPrivate(m));
		
	}	
}

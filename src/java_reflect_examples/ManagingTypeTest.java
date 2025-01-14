package java_reflect_examples;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.List;

import org.junit.jupiter.api.Test;

class ManagingTypeTest {

	class F<E> {
		public int i;
		public int [] itab;
		public E e;
		public E [] etab;
		public List<E> listE;
		public Object o = null;
	}
	
	@Test
	void testPrimitiveType() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field fi = F.class.getField("i");
		Class<?> fit = fi.getType();
		Type fit2 = fi.getGenericType();
		assertTrue(fit == fit2);
		assertTrue(fit.isPrimitive());
		assertTrue(fit.getName().equals("int"));
	}
	
	@Test
	void testGenericArrayOfPrimitiveType() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field fi = F.class.getField("itab");
		Class<?> fit = fi.getType();
		Type fit2 = fi.getGenericType();
		assertTrue(fit == fit2);
		assertTrue(fit.isArray());
		assertTrue(fit.getComponentType().isPrimitive() );
		assertTrue(fit.getComponentType().getName().equals("int"));
	}
	
	@Test
	void testTypeVariable () throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field fi = F.class.getField("e");
		Class<?> fit = fi.getType();
		Type fit2 = fi.getGenericType();
		assertTrue(fit != fit2);
		assertTrue(fit == Object.class);
		assertTrue(fit2 instanceof TypeVariable);
		assertTrue(fit2.getTypeName().equals("E"));
	}

	@Test
	void testGenericArrayOfTypeVariable () throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field fi = F.class.getField("etab");
		Class<?> fit = fi.getType();
		Type fit2 = fi.getGenericType();
		assertTrue(fit != fit2);
		assertTrue(fit.isArray());
		GenericArrayType fitgarr = (GenericArrayType) fit2;
		assertTrue(fitgarr.getGenericComponentType() instanceof TypeVariable);
		Type ti =  fitgarr.getGenericComponentType();
		assertTrue(ti.getTypeName().equals("E"));
	}
	@Test
	void testParameterizedType () throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field fi = F.class.getField("listE");
		Class<?> fit = fi.getType();
		Type fit2 = fi.getGenericType();
		assertTrue(fit != fit2);
		ParameterizedType fitgarr = (ParameterizedType) fit2;
		assertTrue(fitgarr.getActualTypeArguments()[0] instanceof TypeVariable);
		Type ti =  fitgarr.getActualTypeArguments()[0];
		assertTrue(ti.getTypeName().equals("E"));
	}
	
}

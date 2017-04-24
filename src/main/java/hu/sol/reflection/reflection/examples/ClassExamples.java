package hu.sol.reflection.reflection.examples;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hu.sol.reflection.reflection.bean.ExampleClass;

import static java.lang.System.out;

import java.lang.annotation.Annotation;

public class ClassExamples {

	public static void classDeclarationSpy(String className) {
		try {
			Class<?> currentClass = Class.forName(className);

			out.format("Class:%n  %s%n%n%n", currentClass.getCanonicalName());
			out.format("Modifiers:%n  %s%n%n%n", Modifier.toString(currentClass.getModifiers()));

			out.format("Type Parameters:%n");
			TypeVariable<?>[] typeVariables = currentClass.getTypeParameters();
			if (typeVariables.length != 0) {
				out.format("  ");
				for (TypeVariable<?> t : typeVariables)
					out.format("%s ", t.getName());
				out.format("%n%n");
			} else {
				out.format("  -- No Type Parameters --%n%n");
			}

			out.format("Implemented Interfaces:%n");
			Type[] intfs = currentClass.getGenericInterfaces();
			if (intfs.length != 0) {
				for (Type intf : intfs)
					out.format("  %s%n", intf.toString());
				out.format("%n");
			} else {
				out.format("  -- No Implemented Interfaces --%n%n");
			}

			out.format("Inheritance Path:%n");
			List<Class<?>> l = new ArrayList<>();
			printAncestor(currentClass, l);
			if (l.size() != 0) {
				for (Class<?> cl : l)
					out.format("  %s%n", cl.getCanonicalName());
				out.format("%n");
			} else {
				out.format("  -- No Super Classes --%n%n");
			}

			out.format("Annotations:%n");
			Annotation[] ann = currentClass.getAnnotations();
			if (ann.length != 0) {
				for (Annotation a : ann)
					out.format("  %s%n", a.toString());
				out.format("%n");
			} else {
				out.format("  -- No Annotations --%n%n");
			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public static void classFieldsExample(Class currentClass) {
		out.println("------------------------------------------");
		out.println("Mezőkkel kapcsolatos példák");
		out.println("------------------------------------------");

		Field[] fields;
		// publikus mezőket adja vissza:
		out.println("\nPublikus mezők:");
		fields = currentClass.getFields();
		logFields(fields);
		
		// private és publikus mezőket is visszaadja:
		out.println("\nPrivate + publikus mezők:");
		fields = currentClass.getDeclaredFields();
		logFields(fields);
		
		// példányosítás new operátor nélkül:
		ExampleClass example;
		try {
			example = ExampleClass.class.newInstance();
		} catch (InstantiationException | IllegalAccessException e1) {
			throw new RuntimeException();
		}
		// private mező getter/setter nélküli elérése:
		try {
			Field privField = example.getClass().getDeclaredField("privNumber");
			privField.setAccessible(true);
			privField.set(example, 10);
			out.println(privField.get(example));
		} catch (SecurityException | NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException();
		}
		
	}
	
	public static void classMethodExamples(Class currentClass) {
		out.println("\nMetódusok:");
		
		// private és publikus metódusok: 
		Method[] allMethods = currentClass.getDeclaredMethods();
		Arrays.asList(allMethods).forEach((method) -> {
			out.format("%s%n", method.toGenericString());

			out.format( "%24s: %s%n", "ReturnType", method.getReturnType());
			out.format( "%24s: %s%n", "GenericReturnType", method.getGenericReturnType());

			Class<?>[] pType  = method.getParameterTypes();
			Type[] gpType = method.getGenericParameterTypes();
			for (int i = 0; i < pType.length; i++) {
			    out.format( "%24s: %s%n","ParameterType", pType[i]);
			    out.format( "%24s: %s%n","GenericParameterType", gpType[i]);
			}

			Class<?>[] xType  = method.getExceptionTypes();
			Type[] gxType = method.getGenericExceptionTypes();
			for (int i = 0; i < xType.length; i++) {
			    out.format( "%24s: %s%n","ExceptionType", xType[i]);
			    out.format( "%24s: %s%n","GenericExceptionType", gxType[i]);
			}
			
			//metódus meghívása:
			if (method.getName().equals("method1")) {
				try {
					method.invoke(currentClass.newInstance(), "Reflection-nel meghívott metódus.");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static void arrayExamples(Class currentClass)  {
		out.println("\nTömb:");
		Arrays.asList(currentClass.getDeclaredFields()).forEach((field) -> {
			
			if(field.getType().isArray()) {
				out.println(field.getName() + " field is ans array");
			}
		});
	}
	
	private static void logFields(Field[] fields) {
		List<Field> fieldList = Arrays.asList(fields);

		fieldList.stream().forEach((field) -> {
			out.println(field.getName());
			out.println("Elérhető: " + field.isAccessible());
		});
		
		
	}

	private static void printAncestor(Class<?> c, List<Class<?>> l) {
		Class<?> ancestor = c.getSuperclass();
		if (ancestor != null) {
			l.add(ancestor);
			printAncestor(ancestor, l);
		}
	}
}

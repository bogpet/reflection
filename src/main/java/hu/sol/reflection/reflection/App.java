package hu.sol.reflection.reflection;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import hu.sol.reflection.reflection.bean.ExampleClass;
import hu.sol.reflection.reflection.examples.ClassExamples;

/**
 * Reflection Examples
 *
 */
public class App {
	
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		ClassExamples.classDeclarationSpy("java.util.HashMap");
		ClassExamples.classFieldsExample(ExampleClass.class);
		ClassExamples.classMethodExamples(ExampleClass.class);
		ClassExamples.arrayExamples(ExampleClass.class);
		ClassExamples.getClassExamples();
		ClassExamples.enumExamples(ExampleClass.class);
	}
}

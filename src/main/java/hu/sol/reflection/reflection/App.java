package hu.sol.reflection.reflection;

import java.util.ArrayList;

import hu.sol.reflection.reflection.bean.ExampleClass;
import hu.sol.reflection.reflection.examples.ClassExamples;

/**
 * Reflection Examples
 *
 */
public class App {
	
	public static void main(String[] args) {
		ClassExamples.classDeclarationSpy("java.util.HashMap");
		ClassExamples.classFieldsExample(ExampleClass.class);
		ClassExamples.classMethodExamples(ExampleClass.class);
		ClassExamples.arrayExamples(ExampleClass.class);
	}
}

package hu.sol.reflection.reflection.bean;

import java.util.List;

import hu.sol.reflection.interfaces.ExampleInterface;

public class ExampleClass implements ExampleInterface {
	private Integer privNumber;
	private String privString;
	
	public Integer pubNumber;
	public String pubString;
	
	private String[] stringArray;

	@Override
	public void method1(String message) {
		System.out.println(message);
	}
	
	private <T> void method2(List<T> list) {
		list.forEach((item) -> {
			System.out.println(item);
		});
	}
}

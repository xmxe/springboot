package com.mySpringBoot.test.enumtest;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Map.Entry;

import org.junit.Test;

public class Enumt {

	@Test
	public void test1() {
		for (EnumTest e : EnumTest.values()) {
			System.out.println(e.toString());
		}

		System.out.println("----------------我是分隔线------------------");

		EnumTest test = EnumTest.TUE;
		switch (test) {
		case MON:
			System.out.println("今天是星期一");
			break;
		case TUE:
			System.out.println("今天是星期二");
			break;
		// ... ...
		default:
			System.out.println(test);
			break;
		}
	}

	@Test
	public void test2() {
		EnumTest test = EnumTest.TUE;
		// compareTo(E o) 比较此枚举与指定对象的顺序。
		switch (test.compareTo(EnumTest.MON)) {
		case -1:
			System.out.println("TUE 在 MON 之前");
			break;
		case 1:
			System.out.println("TUE 在 MON 之后");
			break;
		default:
			System.out.println("TUE 与 MON 在同一位置");
			break;
		}

		// getDeclaringClass()返回与此枚举常量的枚举类型相对应的 Class 对象
		System.out.println("getDeclaringClass(): " + test.getDeclaringClass().getName());

		// name() 返回此枚举常量的名称，在其枚举声明中对其进行声明。
		// toString()返回枚举常量的名称，它包含在声明中。
		System.out.println("name(): " + test.name());
		System.out.println("toString(): " + test.toString());

		// ordinal()， 返回值是从 0 开始 返回枚举常量的序数（它在枚举声明中的位置，其中初始常量序数为零）
		System.out.println("ordinal(): " + test.ordinal());
	}

	@Test
	public void test3() {
		System.out.println("EnumTest.FRI 的 value = " + EnumTest.FRI.getValue());
		System.out.println("EnumTest.FRI 的 value = " + EnumTest.SUN.isRest());
	}

	@Test
	public void test4() {
		EnumSet<EnumTest> weekSet = EnumSet.allOf(EnumTest.class);
		for (EnumTest day : weekSet) {
			System.out.println(day);
		}

		// EnumMap的使用
		EnumMap<EnumTest, String> weekMap = new EnumMap<>(EnumTest.class);
		weekMap.put(EnumTest.MON, "星期一");
		weekMap.put(EnumTest.TUE, "星期二");
		// ... ...
		for (Iterator<Entry<EnumTest, String>> iter = weekMap.entrySet().iterator(); iter.hasNext();) {
			Entry<EnumTest, String> entry = iter.next();
			System.out.println(entry.getKey().name() + ":" + entry.getValue());
		}
	}
}

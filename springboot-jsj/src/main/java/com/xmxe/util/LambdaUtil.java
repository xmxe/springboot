package com.xmxe.util;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.invoke.SerializedLambda;
/**
 * 从Lambda表达式里拿Service信息的工具
 */
public class LambdaUtil {

	// 传个Lambda进来，返回它对应的“元数据”（比如哪个Service，哪个方法）
	public static SerializedLambda valueOf(Serializable lambda) {
		if (lambda == null) {
			throw new IllegalArgumentException("Lambda is null!");
		}
		try {
			// 反射拿到Lambda里的隐藏方法,Java编译器为Lambda表达式生成了一个隐藏的writeReplace()方法，该方法返回一个SerializedLambda对象，包含了Lambda的所有元数据信息
			Method writeReplaceMethod = lambda.getClass().getDeclaredMethod("writeReplace");
			writeReplaceMethod.setAccessible(true);
			return (SerializedLambda) writeReplaceMethod.invoke(lambda);
		} catch (Exception e) {
			throw new RuntimeException("Analysis Lambda Error", e);
		}
	}
}
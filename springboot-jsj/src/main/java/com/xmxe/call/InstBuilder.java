package com.xmxe.call;

import java.lang.reflect.Constructor;
import java.util.Objects;
import java.util.function.BiConsumer;

/**
 * 快速创建对象的工具，比如new ServiceExecutor后不用一个个set值
 * 使用示例：
 * ServiceExecutor executor = InstBuilder.of(ServiceExecutor.class)
 *  .set(ServiceExecutor::setName, "test")
 *  .set(ServiceExecutor::setTimeout, 1000L)
 *  .build();
 */
public class InstBuilder<T> {

	private final T target;

	/**
	 * 初始化要创建的对象
	 */
    private InstBuilder(Class<T> clazz) {
	    Objects.requireNonNull(clazz, "类不能为null");
	    this.target = createInstance(clazz);
	}

	private T createInstance(Class<T> clazz) {
		try {
			// 优先查找无参构造函数
			Constructor<T> constructor = clazz.getDeclaredConstructor();
			if (!constructor.isAccessible()) {
				constructor.setAccessible(true);
			}
			return constructor.newInstance();
		} catch (NoSuchMethodException e) {
			throw new IllegalArgumentException("类 " + clazz.getName() + " 必须有无参构造函数", e);
		} catch (Exception e) {
			throw new RuntimeException("创建 " + clazz.getName() + " 的实例失败", e);
		}
	}

	/**
	 * 静态方法，入口：InstBuilder.of(ServiceExecutor.class)
	 */
	public static <T> InstBuilder<T> of(Class<T> clazz) {
		return new InstBuilder<>(clazz);
	}

	/**
	 * 链式set值：比如.set(ServiceExecutor::setParam, param)
	 */
	public <V> InstBuilder<T> set(Setter<T,V> setter, V value) {
		Objects.requireNonNull(setter, "setter不能为null");
		setter.set(target, value);
		return this;
	}

	/**
	 * 最后调用build()拿到对象
	 */
	public T build() {
		return target;
	}

	// 定义setter的格式
	@FunctionalInterface
	public interface Setter<T,V> {
		void set(T target, V value);
	}

	/**
	 * 或者直接使用BiConsumer，无需自定义接口
	 * 使用示例：
	 * ServiceExecutor executor2 = SimpleInstBuilder.of(ServiceExecutor.class)
	 *     .with(ServiceExecutor::setName, "test")
	 *     .with(ServiceExecutor::setTimeout, 1000L)
	 *     .build();
	 */
	public <V> InstBuilder<T> with(BiConsumer<T, V> setter, V value) {
		Objects.requireNonNull(setter, "setter不能为null");
		setter.accept(target, value);
		return this;
	}
}
**[Spring自带工具类API](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/util/)**

### 断言

1. **[Assert](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/util/Assert.html)**

- 断言是一个逻辑判断，用于检查不应该发生的情况
- Assert关键字在JDK1.4中引入，可通过JVM参数-enableassertions开启
- SpringBoot中提供了Assert断言工具类，通常用于数据合法性检查

```java
// 要求参数object必须为非空（Not Null），否则抛出异常,不予放行
// 参数message 参数用于定制异常信息。
void notNull(Object object, String message)
// 要求参数必须空（Null），否则抛出异常，不予『放行』。
// 和 notNull() 方法断言规则相反
void isNull(Object object, String message)
// 要求参数必须为真（True），否则抛出异常，不予『放行』。
void isTrue(boolean expression, String message)
// 要求参数（List/Set）必须非空（Not Empty），否则抛出异常，不予放行
void notEmpty(Collection collection, String message)
// 要求参数（String）必须有长度（即，Not Empty），否则抛出异常，不予放行
void hasLength(String text, String message)
// 要求参数（String）必须有内容（即，Not Blank），否则抛出异常，不予放行
void hasText(String text, String message)
// 要求参数是指定类型的实例，否则抛出异常，不予放行
void isInstanceOf(Class type, Object obj, String message)
// 要求参数 `subType` 必须是参数 superType 的子类或实现类，否则抛出异常，不予放行
void isAssignable(Class superType, Class subType, String message)
```

### 对象、数组、集合

1. **[ObjectUtils](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/util/ObjectUtils.html)**

- 获取对象的基本信息

```java
// 获取对象的类名。参数为null时，返回字符串："null" 
String nullSafeClassName(Object obj)
// 参数为null时，返回0
int nullSafeHashCode(Object object)
// 参数为null时，返回字符串："null"
String nullSafeToString(boolean[] array)
// 获取对象 HashCode（十六进制形式字符串）。参数为 null 时，返回 0 
String getIdentityHexString(Object obj)
// 获取对象的类名和 HashCode。 参数为 null 时，返回字符串："" 
String identityToString(Object obj)
// 相当于toString()方法，但参数为 null 时，返回字符串：""
String getDisplayString(Object obj)
```

- 判断工具

```java
// 判断数组是否为空
boolean isEmpty(Object[] array)
// 判断参数对象是否是数组
boolean isArray(Object obj)
// 判断数组中是否包含指定元素
boolean containsElement(Object[] array, Object element)
// 相等，或同为 null时，返回true
boolean nullSafeEquals(Object o1, Object o2)
/*
判断参数对象是否为空，判断标准为：
 Optional: Optional.empty()
 Array: length == 0
 CharSequence: length == 0
 Collection: Collection.isEmpty()
 Map: Map.isEmpty()
 */
boolean isEmpty(Object obj)
```

- 其他工具方法

```java
// 向参数数组的末尾追加新元素，并返回一个新数组
<A, O extends A> A[] addObjectToArray(A[] array, O obj)
// 原生基础类型数组 --> 包装类数组
Object[] toObjectArray(Object source)
```

2. **[StringUtils](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/util/StringUtils.html)**

- 字符串判断工具

```java
// 判断字符串是否为null，或""。注意，包含空白符的字符串为非空
boolean isEmpty(Object str)
// 判断字符串是否是以指定内容结束。忽略大小写
boolean endsWithIgnoreCase(String str, String suffix)
// 判断字符串是否已指定内容开头。忽略大小写
boolean startsWithIgnoreCase(String str, String prefix) 
// 是否包含空白符
boolean containsWhitespace(String str)
// 判断字符串非空且长度不为0，即，Not Empty
boolean hasLength(CharSequence str)
// 判断字符串是否包含实际内容，即非仅包含空白符，也就是Not Blank
boolean hasText(CharSequence str)
// 判断字符串指定索引处是否包含一个子串。
boolean substringMatch(CharSequence str, int index, CharSequence substring)
// 计算一个字符串中指定子串的出现次数
int countOccurrencesOf(String str, String sub)
```

- 字符串操作工具

```java
// 查找并替换指定子串
String replace(String inString, String oldPattern, String newPattern)
// 去除尾部的特定字符
String trimTrailingCharacter(String str, char trailingCharacter) 
// 去除头部的特定字符
String trimLeadingCharacter(String str, char leadingCharacter)
// 去除头部的空白符
String trimLeadingWhitespace(String str)
// 去除头部的空白符
String trimTrailingWhitespace(String str)
// 去除头部和尾部的空白符
String trimWhitespace(String str)
// 删除开头、结尾和中间的空白符
String trimAllWhitespace(String str)
// 删除指定子串
String delete(String inString, String pattern)
// 删除指定字符（可以是多个）
String deleteAny(String inString, String charsToDelete)
// 对数组的每一项执行trim()方法
String[] trimArrayElements(String[] array)
// 将URL字符串进行解码
String uriDecode(String source, Charset charset)
```

- 路径相关工具方法

```java
// 解析路径字符串，优化其中的“..”
String cleanPath(String path)
// 解析路径字符串，解析出文件名部分
String getFilename(String path)
// 解析路径字符串，解析出文件后缀名
String getFilenameExtension(String path)
// 比较两个两个字符串，判断是否是同一个路径。会自动处理路径中的 “..” 
boolean pathEquals(String path1, String path2)
// 删除文件路径名中的后缀部分
String stripFilenameExtension(String path) 
// 以 “. 作为分隔符，获取其最后一部分
String unqualify(String qualifiedName)
// 以指定字符作为分隔符，获取其最后一部分
String unqualify(String qualifiedName, char separator)
```

3. **[CollectionUtils](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/util/CollectionUtils.html)**

- 集合判断工具

```java
// 判断List/Set是否为空
boolean isEmpty(Collection<?> collection)
// 判断Map是否为空
boolean isEmpty(Map<?,?> map)
// 判断List/Set中是否包含某个对象
boolean containsInstance(Collection<?> collection, Object element)
// 以迭代器的方式，判断List/Set中是否包含某个对象
boolean contains(Iterator<?> iterator, Object element)
// 判断List/Set是否包含某些对象中的任意一个
boolean containsAny(Collection<?> source, Collection<?> candidates)
// 判断List/Set中的每个元素是否唯一。即List/Set中不存在重复元素
boolean hasUniqueObject(Collection<?> collection)
```

- 集合操作工具

```java
// 将Array中的元素都添加到List/Set中
<E> void mergeArrayIntoCollection(Object array, Collection<E> collection)  
// 将Properties中的键值对都添加到Map中
<K,V> void mergePropertiesIntoMap(Properties props, Map<K,V> map)
// 返回List中最后一个元素
<T> T lastElement(List<T> list)  
// 返回Set中最后一个元素
<T> T lastElement(Set<T> set) 
// 返回参数 candidates 中第一个存在于参数source中的元素
<E> E findFirstMatch(Collection<?> source, Collection<E> candidates)
// 返回List/Set中指定类型的元素。
<T> T findValueOfType(Collection<?> collection, Class<T> type)
// 返回List/Set中指定类型的元素。如果第一种类型未找到，则查找第二种类型，以此类推
Object findValueOfType(Collection<?> collection, Class<?>[] types)
// 返回List/Set中元素的类型
Class<?> findCommonElementType(Collection<?> collection)
```

### 文件、资源、IO流

1. **[FileCopyUtils](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/util/FileCopyUtils.html)**

- 输入

```java
// 从文件中读入到字节数组中
byte[] copyToByteArray(File in)
// 从输入流中读入到字节数组中
byte[] copyToByteArray(InputStream in)
// 从输入流中读入到字符串中
String copyToString(Reader in)
```

- 输出

```java
// 从字节数组到文件
void copy(byte[] in, File out)
// 从文件到文件
int copy(File in, File out)
// 从字节数组到输出流
void copy(byte[] in, OutputStream out) 
// 从输入流到输出流
int copy(InputStream in, OutputStream out) 
// 从输入流到输出流
int copy(Reader in, Writer out)
// 从字符串到输出流
void copy(String in, Writer out)
```

2. **[ResourceUtils](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/util/ResourceUtils.html)**

- 从资源路径获取文件

```java
// 判断字符串是否是一个合法的URL字符串。
static boolean isUrl(String resourceLocation)
// 获取 URL
static URL getURL(String resourceLocation) 
// 获取文件（在JAR包内无法正常使用，需要是一个独立的文件）
static File getFile(String resourceLocation)
```

- Resource

```java
// 文件系统资源D:\...
FileSystemResource
// URL 资源，如 file://... http://...
UrlResource
// 类路径下的资源，classpth:...
ClassPathResource
// Web 容器上下文中的资源（jar包、war包）
ServletContextResource
// 判断资源是否存在
boolean exists()
// 从资源中获得File对象
File getFile()
// 从资源中获得URI对象
URI getURI()
// 从资源中获得URL对象
URL getURL()
// 获得资源的 InputStream
InputStream getInputStream()
// 获得资源的描述信息
String getDescription()
```

3. **[StreamUtils](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/util/StreamUtils.html)**

- 输入

```java
void copy(byte[] in, OutputStream out)
int copy(InputStream in, OutputStream out)
void copy(String in, Charset charset, OutputStream out)
long copyRange(InputStream in, OutputStream out, long start, long end)
```

- 输出

```java
byte[] copyToByteArray(InputStream in)
String copyToString(InputStream in, Charset charset)
// 舍弃输入流中的内容
int drain(InputStream in) 
```


### 反射、AOP、注解

1. **[ReflectionUtils](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/util/ReflectionUtils.html)**

- 获取方法

```java
// 在类中查找指定方法
Method findMethod(Class<?> clazz, String name) 
// 同上,额外提供方法参数类型作查找条件
Method findMethod(Class<?> clazz, String name, Class<?>... paramTypes) 
// 获得类中所有方法,包括继承而来的
Method[] getAllDeclaredMethods(Class<?> leafClass) 
// 在类中查找指定构造方法
Constructor<T> accessibleConstructor(Class<T> clazz, Class<?>... parameterTypes) 
// 是否是equals()方法
boolean isEqualsMethod(Method method) 
// 是否是hashCode()方法 
boolean isHashCodeMethod(Method method) 
// 是否是toString()方法
boolean isToStringMethod(Method method) 
// 是否是从Object类继承而来的方法
boolean isObjectMethod(Method method) 
// 检查一个方法是否声明抛出指定异常
boolean declaresException(Method method, Class<?> exceptionType) 
```

- 执行方法

```java
// 执行方法
Object invokeMethod(Method method, Object target)  
// 同上,提供方法参数
Object invokeMethod(Method method, Object target, Object... args) 
// 取消Java权限检查。以便后续执行该私有方法
void makeAccessible(Method method) 
// 取消Java权限检查。以便后续执行私有构造方法
void makeAccessible(Constructor<?> ctor) 
```

- 获取字段

```java
// 在类中查找指定属性
Field findField(Class<?> clazz, String name) 
// 同上,多提供了属性的类型
Field findField(Class<?> clazz, String name, Class<?> type) 
// 是否为一个"public static final"属性
boolean isPublicStaticFinal(Field field) 
```

- 设置字段

```java
// 获取target对象的field属性值
Object getField(Field field, Object target) 
// 设置target对象的field属性值，值为value
void setField(Field field, Object target, Object value) 
// 同类对象属性对等赋值
void shallowCopyFieldState(Object src, Object dest)
// 取消Java的权限控制检查,以便后续读写该私有属性
void makeAccessible(Field field) 
// 对类的每个属性执行callback
void doWithFields(Class<?> clazz, ReflectionUtils.FieldCallback fc) 
// 同上，多了个属性过滤功能
void doWithFields(Class<?> clazz, ReflectionUtils.FieldCallback fc, ReflectionUtils.FieldFilter ff) 
// 同上，但不包括继承而来的属性
void doWithLocalFields(Class<?> clazz, ReflectionUtils.FieldCallback fc) 
```

2. **[AopUtils](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/aop/support/AopUtils.html)**

- 判断代理类型

```java
// 判断是不是Spring代理对象
boolean isAopProxy()
// 判断是不是jdk动态代理对象
isJdkDynamicProxy()
// 判断是不是CGLIB代理对象
boolean isCglibProxy()
```

- 获取被代理对象的 class

```java
// 获取被代理的目标class
Class<?> getTargetClass()
```

3. **[AopContext](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/aop/framework/AopContext.html)**

- 获取当前对象的代理对象

```java
Object currentProxy()
```
4. **[AnnotationUtils](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/annotation/AnnotationUtils.html)**

- 获取注解

```java
// findAnnotation：递归向上查找superclass，直到找到为止
// getAnnotation：仅仅查找本类的

// 根据方法，注解类型获取注解
A findAnnotation(Method method,Class<A> annotationType)
// 根据类名，注解类型获取注解
A findAnnotation(Class<?> clazz,Class<A> annotationType)
// 根据被注解的元素获取注解 所有实现AnnotatedElement这个接⼝的“元素”都是可以“被注解的元素" 会在元素里寻找注解
A findAnnotation(AnnotatedElement anotatedElement,Class<A> annotationType)
// 根据方法，注解类型获取注解
A getAnnotation(Method method,Class<A> annotationType)
// 根据类名，注解类型获取注解
A getAnnotation(Class<?> clazz,Class<A> annotationType)
// 根据被注解的元素获取注解 所有实现AnnotatedElement这个接⼝的“元素”都是可以“被注解的元素" 会在元素里寻找注解
A getAnnotation(AnnotatedElement anotatedElement,Class<A> annotationType)
// 获取注解的元素(有重载的方法)
Map<String,Object> getAnnotationAttributes(Annotation annotation)
// 获取注解的默认值(有重载方法)
Object getDefaultValue(Annotation annotation)
// 获取注解的值
Object getValue(Annotation annotation)
// 根据属性获取值
Object getValue(Annotation annotation,String attributeName)
// ...
```
5. **[AnnotatedElementUtils](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/annotation/AnnotatedElementUtils.html)**
```java
// 查找注解并合并到set
Set<A> findAllMergedAnnotations(AnnotatedElement element, Class<A> annotationType)
// 查找注解并合并到set
Set<A> getAllMergedAnnotations(AnnotatedElement element, Class<A> annotationType)
// 查找注解
A findMergedAnnotation(AnnotatedElement element, Class<A> annotationType)
// 获取第一个注解
A getMergedAnnotation(AnnotatedElement element, Class<A> annotationType)
// 根据注解类名判断是否存在注解
boolean hasAnnotation(AnnotatedElement element, Class<? extends Annotation> annotationType)
// 根据注解类名判断是否是这个注解
boolean isAnnotated(AnnotatedElement element, Class<? extends Annotation> annotationType)
// 根据注解名判断是否是这个注解
boolean isAnnotated(AnnotatedElement element, String annotationName)
// 根据注解构建AnnotatedElement
AnnotatedElement forAnnotations(Annotation... annotations)
// 获取注解属性
AnnotationAttributes findMergedAnnotationAttributes(AnnotatedElement element, Class<? extends Annotation> annotationType)
// 获取注解属性
AnnotationAttributes getMergedAnnotationAttributes(AnnotatedElement element, Class<? extends Annotation> annotationType)
```
6. **[ClassUtils](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/util/ClassUtils.html)**
```java
//返回适合使用的路径ClassLoader.getResource （也适合Class.getResource通过在返回值前加上斜杠（'/'）来使用）。
String addResourcePathToPackagePath(Class<?> clazz, String resourceName)
// 构建一个由给定数组中的类/接口名称组成的字符串。
String classNamesToString(Class<?>... classes)
// 构建一个由给定集合中的类/接口名称组成的字符串。
String classNamesToString(Collection<Class<?>> classes)
// 给定一个输入类对象，返回一个由类的包名作为路径名组成的字符串，即所有点 ('.') 都被斜杠 ('/') 替换。
String classPackageAsResourcePath(Class<?> clazz)
// 将基于“.”的完全限定类名转换为基于“/”的资源路径。
String convertClassNameToResourcePath(String className)
// 将基于“/”的资源路径转换为基于“.”的完全限定类名。
String convertResourcePathToClassName(String resourcePath)
// 为给定接口创建一个复合接口类，在一个类中实现给定接口。
Class<?> createCompositeInterface(Class<?>[] interfaces, ClassLoader classLoader)
// 确定给定类的共同祖先（如果有）。
Class<?> determineCommonAncestor(Class<?> clazz1, Class<?> clazz2)
// 替换Class.forName()它还返回基元的类实例
Class<?> forName(String name, ClassLoader classLoader)
// 返回给定实例作为数组实现的所有接口，包括由超类实现的接口
Class<?>[] getAllInterfaces(Object instance)
// 返回给定实例作为 Set 实现的所有接口，包括由超类实现的接口。
Set<Class<?>> getAllInterfacesAsSet(Object instance)
// 返回给定类作为数组实现的所有接口，包括由超类实现的接口。
Class<?>[] getAllInterfacesForClass(Class<?> clazz)
// 返回给定类作为数组实现的所有接口，包括由超类实现的接口。
Class<?>[] getAllInterfacesForClass(Class<?> clazz, ClassLoader classLoader)
// 返回给定类作为 Set 实现的所有接口，包括由超类实现的接口。
Set<Class<?>> getAllInterfacesForClassAsSet(Class<?> clazz)
// 返回给定类作为 Set 实现的所有接口，包括由超类实现的接口。
Set<Class<?>> getAllInterfacesForClassAsSet(Class<?> clazz, ClassLoader classLoader)
// 确定类文件的名称，相对于包含的包
String getClassFileName(Class<?> clazz)
// 确定给定类是否具有具有给定签名的公共构造函数，如果可用则返回它（否则返回null）。
<T> Constructor<T> getConstructorIfAvailable(Class<T> clazz, Class<?>... paramTypes)
// 返回要使用的默认 ClassLoader：通常是线程上下文 ClassLoader，如果可用；加载 ClassUtils 类的 ClassLoader 将用作后备。
ClassLoader	getDefaultClassLoader()
// 返回给定对象类型的描述性名称：通常只是类名，但组件类型类名 + "[]" 用于数组，以及附加的 JDK 代理实现接口列表。
String getDescriptiveType(Object value)
// 已弃用。 有利于getInterfaceMethodIfPossible(Method, Class)
Method getInterfaceMethodIfPossible(Method method)
// 如果可能，为给定的方法句柄确定相应的接口方法。
Method getInterfaceMethodIfPossible(Method method, Class<?> targetClass)
// 确定给定类是否具有具有给定签名的公共方法，如果可用则返回它（否则抛出IllegalStateException）。
Method	getMethod(Class<?> clazz, String methodName, Class<?>... paramTypes)
// 返回给定类和/或其超类的具有给定名称（具有任何参数类型）的方法的数量。
int	getMethodCountForName(Class<?> clazz, String methodName)
// 确定给定类是否具有具有给定签名的公共方法，如果可用则返回（否则返回null）。
Method getMethodIfAvailable(Class<?> clazz, String methodName, Class<?>... paramTypes)
// 给定一个可能来自接口的方法，以及当前反射调用中使用的目标类，如果有，则找到对应的目标方法。
Method getMostSpecificMethod(Method method, Class<?> targetClass)
// 确定给定类的包名
String getPackageName(Class<?> clazz)
// 确定给定全限定类名的包名
String getPackageName(String fqClassName)
// 返回给定方法的限定名，由完全限定的接口/类名+“.”组成 + 方法名称
String getQualifiedMethodName(Method method)
// 返回给定方法的限定名，由完全限定的接口/类名+“.”组成 + 方法名称。
String getQualifiedMethodName(Method method, Class<?> clazz)
// 返回给定类的限定名：通常只是类名，但组件类型类名 + "[]" 用于数组。
String getQualifiedName(Class<?> clazz)
// 获取没有限定包名的类名。
String getShortName(Class<?> clazz)
// 获取没有限定包名的类名。
String getShortName(String className)
// 以非大写的 JavaBeans 属性格式返回 Java 类的短字符串名称。
String getShortNameAsProperty(Class<?> clazz)
// 返回类的公共静态方法。
Method getStaticMethod(Class<?> clazz, String methodName, Class<?>... args)
// 返回给定类的用户定义类：通常只是给定类，但如果是 CGLIB 生成的子类，则返回原始类。
Class<?> getUserClass(Class<?> clazz)
// 返回给定实例的用户定义类：通常只是给定实例的类，但如果是 CGLIB 生成的子类，则返回原始类。
Class<?> getUserClass(Object instance)
// 给定的类或其超类之一是否至少具有一个或多个具有所提供名称的方法（具有任何参数类型）？包括非公共方法。
boolean	hasAtLeastOneMethodWithName(Class<?> clazz, String methodName)
// 确定给定类是否具有具有给定签名的公共构造函数。
boolean	hasConstructor(Class<?> clazz, Class<?>... paramTypes)
// 确定给定类是否具有具有给定签名的公共方法。
boolean	hasMethod(Class<?> clazz, Method method)
// 确定给定类是否具有具有给定签名的公共方法。
boolean	hasMethod(Class<?> clazz, String methodName, Class<?>... paramTypes)
// 假设通过反射设置，检查右侧类型是否可以分配给左侧类型。
boolean	isAssignable(Class<?> lhsType, Class<?> rhsType)
// 假设通过反射设置，确定给定类型是否可以从给定值分配。
boolean	isAssignableValue(Class<?> type, Object value)
// 检查给定的类在给定的上下文中是否是缓存安全的
boolean	isCacheSafe(Class<?> clazz, ClassLoader classLoader)
// 已弃用。 从 5.2 开始，支持自定义（可能更窄）检查
boolean	isCglibProxy(Object object)
// 已弃用。 从 5.2 开始，支持自定义（可能更窄）检查
boolean	isCglibProxyClass(Class<?> clazz)
// 已弃用。 从 5.2 开始，支持自定义（可能更窄）检查
boolean	isCglibProxyClassName(String className)
// 确定提供的类是否为内部类
boolean	isInnerClass(Class<?> clazz)
// 确定给定的接口是否是通用 Java 语言接口： Serializable, Externalizable, Closeable, AutoCloseable, Cloneable, Comparable- 在查找“主要”用户级接口时，所有这些都可以忽略。
boolean	isJavaLanguageInterface(Class<?> ifc)
// 确定提供Class的是否是 JVM 生成的 lambda 表达式或方法引用的实现类。
boolean	isLambdaClass(Class<?> clazz)
// 确定Class由提供的名称标识的是否存在并且可以加载。
boolean	isPresent(String className, ClassLoader classLoader)
// 检查给定的类是否代表一个基元数组
boolean	isPrimitiveArray(Class<?> clazz)
// 检查给定的类是否代表一个原语
boolean	isPrimitiveOrWrapper(Class<?> clazz)
// 检查给定的类是否代表一个原始包装器
boolean	isPrimitiveWrapper(Class<?> clazz)
// 检查给定的类是否代表原始包装器数组
boolean isPrimitiveWrapperArray(Class<?> clazz)
// 确定给定方法是由用户声明还是至少指向用户声明的方法。
boolean	isUserLevelMethod(Method method)
// 检查给定的类在给定的 ClassLoader 中是否可见。
boolean	isVisible(Class<?> clazz, ClassLoader classLoader)
// 检查给定的类是否与用户指定的类型名称匹配。
boolean	matchesTypeName(Class<?> clazz, String typeName)
// 如有必要，用环境的 bean ClassLoader 覆盖线程上下文 ClassLoader
ClassLoader	overrideThreadContextClassLoader(ClassLoader classLoaderToUse)
// 将给定的类名解析为 Class 实例。
Class<?> resolveClassName(String className, ClassLoader classLoader)
// 如果合适，根据 JVM 对原始类的命名规则，将给定的类名解析为原始类。
Class<?> resolvePrimitiveClassName(String name)
// 如果给定类是原始类，则解析给定类，而是返回相应的原始包装类型。
Class<?> resolvePrimitiveIfNecessary(Class<?> clazz)
// 将给定的复制Collection到一个Class数组中。
Class<?>[] toClassArray(Collection<Class<?>> collection)
```
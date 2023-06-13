
> Shiro的几个概念
> - subject: 应用代码直接交互的对象是Subject, 也就是说Shiro的对外API核心就是Subject, Subject代表了当前的用户，这个用户不一定是一个具体的人，与当前应用交互的任何东西都是Subject,如网络爬虫，机器人等，与Subject的所有交互都会委托给SecurityManager; Subject其实是一一个门面，SecurityManageer 才是实际的执行者
> - SecurityManager: 安全管理器，即所有与安全有关的操作都会与SercurityManager交互, 并且它管理着所有的Subject,可以看出它是Shiro的核心，它负责与Shiro的其他组件进行交互，它相当于SpringMVC的DispatcherServlet的角色
> - Realm: Shiro从Realm获取安全数据 (如用户,角色，权限)，就是说SecurityManager要验证用户身份，那么它需要从Realm获取相应的用户进行比较，来确定用户的身份是否合法;也需要从Realm得到用户相应的角色、权限，进行验证用户的操作是否能够进行，可以把Realm看DataSource;


- [SpringBoot整合Shiro](https://blog.csdn.net/gdnlnsjd/article/details/121764432)
## Sa-Token
- [Sa-Token文档](http://sa-token.dev33.cn/)
- [sa-token-demo](https://github.com/dromara/Sa-Token/tree/dev/sa-token-demo)

### API
```java
// 标记当前会话登录的账号id
StpUtil.login(10001);
// 获取当前会话登录的账号id 如果未登录，则抛出异常：NotLoginException
StpUtil.getLoginId();
// 类似查询API还有：
// 获取当前会话账号id,并转化为String类型
StpUtil.getLoginIdAsString();
// 获取当前会话账号id,并转化为int类型
StpUtil.getLoginIdAsInt();
// 获取当前会话账号id,并转化为long类型
StpUtil.getLoginIdAsLong();
// ---------- 指定未登录情形下返回的默认值 ----------
// 获取当前会话账号id,如果未登录，则返回null
StpUtil.getLoginIdDefaultNull();
// 获取当前会话账号id,如果未登录，则返回默认值（defaultValue可以为任意类型）
StpUtil.getLoginId(T defaultValue);

// 获取当前会话是否已经登录,返回true或false
StpUtil.isLogin();
// 当前会话注销登录
StpUtil.logout();
// 将账号为10001的会话踢下线
StpUtil.kickout(10001);
// 让账号为10001的会话注销登录（踢人下线）
//StpUtil.logoutByLoginId(10001);
// 查询当前账号是否含有指定角色标识,返回true或false
StpUtil.hasRole("super-admin");
// 查询当前账号是否含有指定权限,返回true或false
StpUtil.hasPermission("user:add");
// 获取当前账号id的Session
StpUtil.getSession();
// 获取账号id为10001的Session
StpUtil.getSessionByLoginId(10001);
// 获取账号id为10001的token令牌值
StpUtil.getTokenValueByLoginId(10001);
// 指定设备标识登录，常用于“同端互斥登录”
StpUtil.login(10001,"PC");
// 指定账号指定设备类型踢下线(不同端不受影响)
StpUtil.kickout(10001,"PC");
// 指定设备标识进行强制注销(不同端不受影响)
//StpUtil.logoutByLoginId(10001,"PC");
// 在当前会话开启二级认证，有效期为120秒
StpUtil.openSafe(120);
// 校验当前会话是否处于二级认证有效期内，校验失败会抛出异常
StpUtil.checkSafe();
// 将当前会话身份临时切换为其它账号
StpUtil.switchTo(10044);

Token查询
// 获取当前会话的token值
StpUtil.getTokenValue()
// 获取当前StpLogic的token名称
StpUtil.getTokenName();
// 获取指定token对应的账号id，如果未登录，则返回null
StpUtil.getLoginIdByToken(String tokenValue);
// 获取当前会话剩余有效期（单位：s，返回-1代表永久有效）
StpUtil.getTokenTimeout();
// 获取当前会话的token信息参数
StpUtil.getTokenInfo();

权限认证
// 获取：当前账号所拥有的权限集合
StpUtil.getPermissionList();
// 判断：当前账号是否含有指定权限,返回true或false
StpUtil.hasPermission("user-update");
// 校验：当前账号是否含有指定权限,如果验证未通过，则抛出异常:NotPermissionException
StpUtil.checkPermission("user-update");
// 校验：当前账号是否含有指定权限[指定多个，必须全部验证通过]
StpUtil.checkPermissionAnd("user-update","user-delete");
// 校验：当前账号是否含有指定权限[指定多个，只要其一验证通过即可]
StpUtil.checkPermissionOr("user-update","user-delete");

角色认证
// 获取：当前账号所拥有的角色集合
StpUtil.getRoleList();
// 判断：当前账号是否拥有指定角色,返回true或false
StpUtil.hasRole("super-admin");
// 校验：当前账号是否含有指定角色标识,如果验证未通过，则抛出异常:NotRoleException
StpUtil.checkRole("super-admin");
// 校验：当前账号是否含有指定角色标识[指定多个，必须全部验证通过]
StpUtil.checkRoleAnd("super-admin","shop-admin");
// 校验：当前账号是否含有指定角色标识[指定多个，只要其一验证通过即可]
StpUtil.checkRoleOr("super-admin","shop-admin");

强制注销
// 强制指定账号注销下线
StpUtil.logout(10001);
// 强制指定账号指定端注销下线
StpUtil.logout(10001,"PC");
// 强制指定Token注销下线
StpUtil.logoutByTokenValue("token");

踢人下线
// 将指定账号踢下线
StpUtil.kickout(10001);
// 将指定账号指定端踢下线
StpUtil.kickout(10001,"PC");
// 将指定Token踢下线
StpUtil.kickoutByTokenValue("token");

账号封禁
// 封禁指定账号
// 参数一：账号id
// 参数二：封禁时长，单位：秒(86400秒=1天，此值为-1时，代表永久封禁)
StpUtil.disable(10001,86400);
// 获取指定账号是否已被封禁(true=已被封禁,false=未被封禁)
StpUtil.isDisable(10001);
// 获取指定账号剩余封禁时间，单位：秒
StpUtil.getDisableTime(10001);
// 解除封禁
StpUtil.untieDisable(10001);
// 对于正在登录的账号，对其账号封禁时并不会使其立刻注销
// 如果需要将其封禁后立即掉线，可采取先踢再封禁的策略，例如：
// 先踢下线
StpUtil.kickout(10001);
// 再封禁账号
StpUtil.disable(10001,86400);

// 注解鉴权
// @SaCheckLogin:登录认证——只有登录之后才能进入该方法。
// @SaCheckRole("admin"):角色认证——必须具有指定角色标识才能进入该方法。
// @SaCheckPermission("user:add"):权限认证——必须具有指定权限才能进入该方法。
// @SaCheckSafe:二级认证校验——必须二级认证之后才能进入该方法。
// @SaCheckBasic:HttpBasic认证——只有通过Basic认证后才能进入该方法。

Session
// 在登录时缓存user对象
StpUtil.getSession().set("user",user);
// 然后我们就可以在任意处使用这个user对象
SysUser user = (SysUser) StpUtil.getSession().get("user");

// 在Sa-Token中，Session分为三种，分别是：
// User-Session:指的是框架为每个账号id分配的Session
// Token-Session:指的是框架为每个token分配的Session
// Custom-Session:指的是以一个特定的值作为SessionId，来分配的Session

User-Session
// 获取当前账号id的Session(必须是登录后才能调用)
StpUtil.getSession();
// 获取当前账号id的Session,并决定在Session尚未创建时，是否新建并返回
StpUtil.getSession(true);
// 获取账号id为10001的Session
StpUtil.getSessionByLoginId(10001);
// 获取账号id为10001的Session,并决定在Session尚未创建时，是否新建并返回
StpUtil.getSessionByLoginId(10001,true);
// 获取SessionId为xxxx-xxxx的Session,在Session尚未创建时,返回null
StpUtil.getSessionBySessionId("xxxx-xxxx");

Token-Session
// 获取当前token的专属Session
StpUtil.getTokenSession();
// 获取指定token的专属Session
StpUtil.getTokenSessionByToken(token);

自定义Session
// 查询指定key的Session是否存在
SaSessionCustomUtil.isExists("goods-10001");
// 获取指定key的Session，如果没有，则新建并返回
SaSessionCustomUtil.getSessionById("goods-10001");
// 获取指定key的Session，如果没有，第二个参数决定是否新建并返回
SaSessionCustomUtil.getSessionById("goods-10001",false);
// 删除指定key的Session
SaSessionCustomUtil.deleteSessionById("goods-10001");

Session取值
// 写值
session.set("name","zhang");
// 写值(只有在此key原本无值的时候才会写入)
session.setDefaultValue("name","zhang");
// 取值
session.get("name");
// 取值(指定默认值)
session.get("name","<defaultValue>");
// ----------数据类型转换----------
session.getInt("age");         // 取值(转int类型)
session.getLong("age");        // 取值(转long类型)
session.getString("name");     // 取值(转String类型)
session.getDouble("result");   // 取值(转double类型)
session.getFloat("result");    // 取值(转float类型)
session.getModel("key",Student.class);// 取值(指定转换类型)
session.getModel("key",Student.class,<defaultValue>);  // 取值(指定转换类型,并指定值为Null时返回的默认值)
// 是否含有某个key(返回true或false)
session.has("key");
// 删值
session.delete('name');
// 清空所有值
session.clear();
// 获取此Session的所有key(返回Set<String>)
session.keys();

其他操作
// 返回此Session的id
session.getId();
// 返回此Session的创建时间(时间戳)
session.getCreateTime();
// 返回此Session会话上的底层数据对象（如果更新map里的值，请调用session.update()方法避免产生脏数据）
session.getDataMap();
// 将这个Session从持久库更新一下
session.update();
// 注销此Session会话(从持久库删除此Session)
session.logout();

有关操作其它账号的api
// 获取指定账号10001的tokenValue值
StpUtil.getTokenValueByLoginId(10001);
// 将账号10001的会话注销登录
StpUtil.logout(10001);
// 获取账号10001的Session对象,如果session尚未创建,则新建并返回
StpUtil.getSessionByLoginId(10001);
// 获取账号10001的Session对象,如果session尚未创建,则返回null
StpUtil.getSessionByLoginId(10001,false);
// 获取账号10001是否含有指定角色标识
StpUtil.hasRole(10001,"super-admin");
// 获取账号10001是否含有指定权限码
StpUtil.hasPermission(10001,"user:add");

临时身份切换
// 将当前会话[身份临时切换]为其它账号（本次请求内有效）
StpUtil.switchTo(10044);
// 此时再调用此方法会返回10044(我们临时切换到的账号id)
StpUtil.getLoginId();
// 结束[身份临时切换]
StpUtil.endSwitch();

二级认证
// 在当前会话开启二级认证，时间为120秒
StpUtil.openSafe(120);
// 获取：当前会话是否处于二级认证时间内
StpUtil.isSafe();
// 检查当前会话是否已通过二级认证，如未通过则抛出异常
StpUtil.checkSafe();
// 获取当前会话的二级认证剩余有效时间(单位:秒,返回-2代表尚未通过二级认证)
StpUtil.getSafeTime();
// 在当前会话结束二级认证
StpUtil.closeSafe();

// 二级认证：必须二级认证之后才能进入该方法
@SaCheckSafe

```
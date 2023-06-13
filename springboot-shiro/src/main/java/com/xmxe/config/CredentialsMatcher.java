package com.xmxe.config;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 密码比较器
 * 因为有的数据库保存的密码为加密后的 shiro默认是使用明文比较
 */
public class CredentialsMatcher extends SimpleCredentialsMatcher{
    Logger logger = LoggerFactory.getLogger(CredentialsMatcher.class);

    /**
     * 匹配信息是否正确
     * @param token subject.login(usernamePasswordToken)的参数，即usernamePasswordToken的数据
     * @param info 从MyRealm doGetAuthenticationInfo()得到的数据 看返回类型就知道
     */
	@Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        // 获取页面输入的数据
	    UsernamePasswordToken utoken=(UsernamePasswordToken) token;
        // 输入的密码
        String inPassword = new String(utoken.getPassword());
        // 输入的用户
        String inUsername = utoken.getUsername();

        logger.info("开始密码匹配，输入的密码为--->{},用户名为--->{},token.getCredentials--->{}",
                inPassword,inUsername,super.getCredentials(token));

        // 获得MyRealm送过来的数据即真正正确的密码，可能是加密后的:(可以采用加盐(salt)的方式去检验)
        String dbPassword= String.valueOf(super.getCredentials(info));
//        PrincipalCollection pc = info.getPrincipals();
//        Object primary = pc.getPrimaryPrincipal();
//
//        SimpleAuthenticationInfo simpleAuthenticationInfo = (SimpleAuthenticationInfo)info;
//        ByteSource byteSource = simpleAuthenticationInfo.getCredentialsSalt();
//        logger.info("获取new SimpleAuthenticationInfo中用户数据--->{}",simpleAuthenticationInfo);
        SimpleHash simpleHash = new SimpleHash("SHA-1", inPassword, "qwert");
        //进行密码的比对
        return this.equals(simpleHash.toHex(), dbPassword);
    }

}
package com.cwj.springbootTest.config.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import com.cwj.springbootTest.domain.entity.SysPermission;
import com.cwj.springbootTest.domain.entity.SysRole;
import com.cwj.springbootTest.domain.entity.User;
import com.cwj.springbootTest.domain.entity.UserInfo;
import com.cwj.springbootTest.service.UserInfoService;
import com.cwj.springbootTest.service.UserService;
public class MyShiroRealm extends AuthorizingRealm{
	
	//@Resource(name="userInfoService")
	@Autowired
	@Lazy
	UserService userService;
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
	    System.out.println("权限配置-->MyShiroRealm.doGetAuthorizationInfo()");
	    SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
	    User user  = (User)principals.getPrimaryPrincipal();
	    authorizationInfo.addRole("");
	    authorizationInfo.addStringPermission("");
	    return authorizationInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
	    UsernamePasswordToken upToken = (UsernamePasswordToken)token;
	    //获取用户的输入的账号.
	    String username = (String)upToken.getPrincipal();
	    if(username==null){
	    	return null;
	    }
	    //通过username从数据库中查找 User对象，如果找到，没找到.
	    //实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
	    User user = userService.findByUserName(username);
	    if(user == null){
	        return null;
	    }
	    SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
	    		user, //用户
	    		user.getPassWord(), //密码
	            ByteSource.Util.bytes(user.getCredentialsSalt()),//salt=username+salt
	            getName()  //realm name
	    );
	    return authenticationInfo;
	}
}

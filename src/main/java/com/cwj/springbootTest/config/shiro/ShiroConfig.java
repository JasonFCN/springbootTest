package com.cwj.springbootTest.config.shiro;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShiroConfig {
	/**
	 * 加密算法
	 */
	public static final String HASHALGORITHMNAME = "md5";
	/**
	 * 加密次数
	 */
	public static final int HASHITERATIONS = 2;
	@Bean
	public ShiroFilterFactoryBean shirFilter(@Qualifier("securityManager") SecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		// 拦截器.
		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
		// 配置不会被拦截的链接 顺序判断
		filterChainDefinitionMap.put("/index", "anon");
		filterChainDefinitionMap.put("/user/login", "anon");
		filterChainDefinitionMap.put("/user/logout", "logout");
		// 配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
		// <!-- 过滤链定义，从上向下顺序执行，一般将/**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
		// <!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
		// 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
		shiroFilterFactoryBean.setLoginUrl("/user/loginPage");
		// 未授权界面;
		shiroFilterFactoryBean.setUnauthorizedUrl("/403");
		filterChainDefinitionMap.put("/user/*", "authc");
		// 静态资源匿名访问
		filterChainDefinitionMap.put("/static/*", "anon");
		filterChainDefinitionMap.put("*.css", "anon");
		filterChainDefinitionMap.put("*.js", "anon");
		filterChainDefinitionMap.put("*.jpg", "anon");
		filterChainDefinitionMap.put("*.png", "anon");
		//filterChainDefinitionMap.put("/*", "authc");

		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

		return shiroFilterFactoryBean;
	}

	@Bean("securityManager")
	public SecurityManager securityManager(@Qualifier("myShiroRealm") MyShiroRealm myShiroRealm) {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		//securityManager.setCacheManager(null);
		securityManager.setRealm(myShiroRealm);
		return securityManager;
	}

	@Bean("myShiroRealm")
	public MyShiroRealm myShiroRealm(@Qualifier("credentialsMatcher") CredentialsMatcher matcher) {
		System.out.println("-->myShiroRealm");
		MyShiroRealm myShiroRealm = new MyShiroRealm();
		myShiroRealm.setCredentialsMatcher(matcher);
		return myShiroRealm;
	}
	
	@Bean(name = "credentialsMatcher")
	public CredentialsMatcher credentialsMatcher() {
		return new CredentialsMatcher(HASHALGORITHMNAME, HASHITERATIONS);
	}
	
	/**
	 * 开启shiro aop注解支持. 使用代理方式;所以需要开启代码支持;
	 * 
	 * @param securityManager
	 * @return
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}
}
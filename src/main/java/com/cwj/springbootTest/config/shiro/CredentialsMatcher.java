package com.cwj.springbootTest.config.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;

public class CredentialsMatcher extends HashedCredentialsMatcher {
	public CredentialsMatcher(String hashAlgorithm,int hashIterations) {
        super(hashAlgorithm);
        this.setHashIterations(hashIterations);
    }
	/*// 声明一个缓存接口，这个接口是Shiro缓存管理的一部分，它的具体实现可以通过外部容器注入
    private Cache<String, AtomicInteger> passwordRetryCache;

    public CredentialsMatcher(CacheManager cacheManager) {
        passwordRetryCache = cacheManager.getCache("passwordRetryCache");
    }
*/
	@Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
		
		/*String username = (String) token.getPrincipal();
        AtomicInteger retryCount = passwordRetryCache.get(username);
        if (retryCount == null) {
            retryCount = new AtomicInteger(0);
            passwordRetryCache.put(username, retryCount);
        }
        // 自定义一个验证过程：当用户连续输入密码错误5次以上禁止用户登录一段时间
        if (retryCount.incrementAndGet() > 5) {
            throw new ExcessiveAttemptsException();
        }
        if (match) {
            passwordRetryCache.remove(username);
        }*/
        boolean match = super.doCredentialsMatch(token, info);
        return match;
    }
}

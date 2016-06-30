/**
 * 
 */
package org.youi.framework.security;

import java.util.Collection;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.AuthenticationStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhyi_12
 *
 */
public class DefaultModularRealmAuthenticator extends ModularRealmAuthenticator{
	private static final Logger log = LoggerFactory.getLogger(DefaultModularRealmAuthenticator.class);
	
	@Override
	protected AuthenticationInfo doMultiRealmAuthentication(
			Collection<Realm> realms, AuthenticationToken token) {
		
		 AuthenticationStrategy strategy = getAuthenticationStrategy();

	        AuthenticationInfo aggregate = strategy.beforeAllAttempts(realms, token);

	        if (log.isTraceEnabled()) {
	            log.trace("Iterating through {} realms for PAM authentication", realms.size());
	        }

	        for (Realm realm : realms) {

	            aggregate = strategy.beforeAttempt(realm, token, aggregate);

	            if (realm.supports(token)) {

	                log.trace("Attempting to authenticate token [{}] using realm [{}]", token, realm);

	                AuthenticationInfo info = null;
	                Throwable t = null;
	                try {
	                    info = realm.getAuthenticationInfo(token);
	                }catch(AuthenticationException e){
	                	throw e;
	                } catch (Throwable throwable) {
	                    t = throwable;
	                    if (log.isDebugEnabled()) {
	                        String msg = "Realm [" + realm + "] threw an exception during a multi-realm authentication attempt:";
	                        log.debug(msg, t);
	                    }
	                }

	                aggregate = strategy.afterAttempt(realm, token, info, aggregate, t);

	            } else {
	                log.debug("Realm [{}] does not support token {}.  Skipping realm.", realm, token);
	            }
	        }

	        aggregate = strategy.afterAllAttempts(token, aggregate);

	        return aggregate;
	}

}

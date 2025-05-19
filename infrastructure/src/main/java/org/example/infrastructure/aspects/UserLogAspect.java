package org.example.infrastructure.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
/**
 * Aspect to log user access to certain methods.
 *
 * <p>This aspect is used to log when a user access a method annotated with
 * {@link org.example.infrastructure.aspects.UserLog UserLog}.</p>
 */
@Aspect
@Component
public class UserLogAspect {
    private static final Logger logger = LoggerFactory.getLogger(UserLogAspect.class);
/**
 * Aspect to log user access to certain methods.
 *
 * <p>This aspect is used to log when a user accesses a method annotated with
 * {@link org.example.infrastructure.aspects.UserLog UserLog}.</p>
 *
 * <p>It retrieves the current authentication from the security context and logs
 * the username and method name being accessed.</p>
 *
 * <p>If no authentication is available, a warning log is generated.</p>
 *
 *  @since 1.0.0
 *  @author Willow Maui García
 */
    @Before("@annotation(org.example.infrastructure.aspects.UserLog)")
    public void logUser(JoinPoint joinPoint) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            String username = authentication.getName();
            String methodName = joinPoint.getSignature().getName();
            logger.debug("Usuario '{}' ha accedido al método: {}", username, methodName);
        } else {
            logger.debug("Acceso anónimo al método: {}", joinPoint.getSignature().getName());
        }
    }
}

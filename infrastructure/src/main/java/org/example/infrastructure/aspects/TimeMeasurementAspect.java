package org.example.infrastructure.aspects;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class TimeMeasurementAspect {
    private static final Logger logger = LoggerFactory.getLogger(TimeMeasurementAspect.class);
    
    private final Environment environment;

    @Around("@annotation(org.example.infrastructure.aspects.TimeMeasurement)")
    public Object logElapsedTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;

        if (result instanceof ResponseEntity) {
            ResponseEntity<?> responseEntity = (ResponseEntity<?>) result;
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                String[] activeProfiles = environment.getActiveProfiles();
                String profile = (activeProfiles.length > 0) ? activeProfiles[0] : "default";
                logger.debug("Método: {} - Perfil: {} - Tiempo de respuesta: {} ms", joinPoint.getSignature().getName(), profile, elapsedTime);
            }
        }
        return result;
    }
}

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

/**
 * This class is an aspect that measures the time of execution of a method.
 *
 * <p>This aspect is used to log when a user access a method annotated with
 * {@link org.example.infrastructure.aspects.TimeMeasurement TimeMeasurement}.</p>
 *
 * @since 1.0.0
 * @author Willow Maui Garcia
 */
@Aspect
@Component
@RequiredArgsConstructor
public class TimeMeasurementAspect {
    private static final Logger logger = LoggerFactory.getLogger(TimeMeasurementAspect.class);
    
    private final Environment environment;

    /**
     * This method is an aspect that measures the time of execution of a method.
     * <p>
     * This method will be executed before and after the execution of the method that is annotated with {@link org.example.infrastructure.aspects.TimeMeasurement}.
     * The time of execution will be logged in the console.
     * <p>
     * If the method returns a {@link ResponseEntity} with status code {@link HttpStatus#OK} the time of execution will be logged
     * in the console with the following format: "Método: {methodName} - Perfil: {profile} - Tiempo de respuesta: {elapsedTime} ms"
     * where {methodName} is the name of the method, {profile} is the profile used and {elapsedTime} is the time of execution in milliseconds.
     *
     * @param joinPoint The join point of the method that is being executed.
     * @return The result of the method that is being executed.
     * @throws Throwable If the method that is being executed throws an exception.
     *
     * @since 1.0.0
     * @author Willow Maui García
     */
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

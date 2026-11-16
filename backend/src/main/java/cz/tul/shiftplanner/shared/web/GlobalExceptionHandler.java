package cz.tul.shiftplanner.shared.web;

import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Single place that turns exceptions into RFC 7807 problem responses with a {@code code} property.
 *
 * <p>Spring Security's entry point and access-denied handler delegate here as well (see
 * {@code SecurityConfig}), so authentication and authorization failures use the same format.
 * Internal details (stack traces, exception messages) are never sent to the client.
 */
@RestControllerAdvice
class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private final AuthenticationTrustResolver trustResolver = new AuthenticationTrustResolverImpl();

    /** One invalid field of a request body. */
    record FieldViolation(String field, String message) {
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ProblemDetail problem = ex.updateAndGetBody(getMessageSource(), LocaleContextHolder.getLocale());
        problem.setProperty(ProblemCodes.PROPERTY, ProblemCodes.VALIDATION_FAILED);
        List<FieldViolation> violations = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> new FieldViolation(error.getField(), error.getDefaultMessage()))
                .toList();
        problem.setProperty("errors", violations);
        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    /** Adds a fallback {@code code} to every problem produced by the framework handlers. */
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        ResponseEntity<Object> response = super.handleExceptionInternal(ex, body, headers, statusCode, request);
        if (response != null && response.getBody() instanceof ProblemDetail problem && !hasCode(problem)) {
            problem.setProperty(ProblemCodes.PROPERTY, ProblemCodes.forStatus(response.getStatusCode()));
        }
        return response;
    }

    @ExceptionHandler(AuthenticationException.class)
    ResponseEntity<ProblemDetail> handleAuthentication(AuthenticationException ex) {
        return unauthorized();
    }

    /** Anonymous users get 401 (log in), authenticated users without permission get 403. */
    @ExceptionHandler(AccessDeniedException.class)
    ResponseEntity<ProblemDetail> handleAccessDenied(AccessDeniedException ex) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || trustResolver.isAnonymous(authentication)) {
            return unauthorized();
        }
        return problem(HttpStatus.FORBIDDEN, ProblemCodes.ACCESS_DENIED, "problem.accessDenied");
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<ProblemDetail> handleUnexpected(Exception ex) {
        log.error("Unhandled exception", ex);
        return problem(HttpStatus.INTERNAL_SERVER_ERROR, ProblemCodes.INTERNAL_ERROR, "problem.internal");
    }

    private ResponseEntity<ProblemDetail> unauthorized() {
        return problem(HttpStatus.UNAUTHORIZED, ProblemCodes.UNAUTHORIZED, "problem.unauthorized");
    }

    private ResponseEntity<ProblemDetail> problem(HttpStatus status, String code, String messageKey) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(status, message(messageKey + ".detail"));
        problem.setTitle(message(messageKey + ".title"));
        problem.setProperty(ProblemCodes.PROPERTY, code);
        return ResponseEntity.status(status).body(problem);
    }

    private String message(String key) {
        MessageSource messageSource = getMessageSource();
        if (messageSource == null) {
            return key;
        }
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(key, null, key, locale);
    }

    private static boolean hasCode(ProblemDetail problem) {
        return problem.getProperties() != null && problem.getProperties().containsKey(ProblemCodes.PROPERTY);
    }
}

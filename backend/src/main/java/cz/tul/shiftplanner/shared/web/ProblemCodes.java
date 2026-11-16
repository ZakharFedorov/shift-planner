package cz.tul.shiftplanner.shared.web;

import org.springframework.http.HttpStatusCode;

/**
 * Machine-readable error codes added to every RFC 7807 response as the {@code code} property.
 * The frontend translates codes to Czech UI texts; {@code detail} is a human-readable fallback.
 */
public final class ProblemCodes {

    /** Name of the problem-detail property carrying the machine-readable code. */
    public static final String PROPERTY = "code";

    public static final String VALIDATION_FAILED = "VALIDATION_FAILED";
    public static final String BAD_REQUEST = "BAD_REQUEST";
    public static final String UNAUTHORIZED = "UNAUTHORIZED";
    public static final String ACCESS_DENIED = "ACCESS_DENIED";
    public static final String NOT_FOUND = "NOT_FOUND";
    public static final String METHOD_NOT_ALLOWED = "METHOD_NOT_ALLOWED";
    public static final String CONFLICT = "CONFLICT";
    public static final String UNSUPPORTED_MEDIA_TYPE = "UNSUPPORTED_MEDIA_TYPE";
    public static final String INTERNAL_ERROR = "INTERNAL_ERROR";

    private ProblemCodes() {
    }

    /** Fallback code for framework exceptions that do not carry a specific code. */
    public static String forStatus(HttpStatusCode status) {
        return switch (status.value()) {
            case 400 -> BAD_REQUEST;
            case 401 -> UNAUTHORIZED;
            case 403 -> ACCESS_DENIED;
            case 404 -> NOT_FOUND;
            case 405 -> METHOD_NOT_ALLOWED;
            case 409 -> CONFLICT;
            case 415 -> UNSUPPORTED_MEDIA_TYPE;
            default -> status.is5xxServerError() ? INTERNAL_ERROR : "HTTP_" + status.value();
        };
    }
}

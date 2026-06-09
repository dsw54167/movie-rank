package dsw.projects.demo;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
@Slf4j
public class AuthenticationService {

    private static final String AUTH_TOKEN_HEADER_NAME = "X-API-KEY";
    private static final String AUTH_TOKEN = "xcp:tXj{ArF:p!_0";

    public AuthenticationService() {
    }

    public static Authentication getAuthentication(HttpServletRequest request) {

        String apiKey = request.getHeader(AUTH_TOKEN_HEADER_NAME);
        log.error(request.getHeaderNames().toString());
        log.error("API Key: {}", apiKey);
        if (apiKey == null || !apiKey.equals(AUTH_TOKEN)) {
            throw new BadCredentialsException("Invalid API Key");
        }

        return new ApiKeyAuthentication(apiKey, AuthorityUtils.NO_AUTHORITIES);
    }
}
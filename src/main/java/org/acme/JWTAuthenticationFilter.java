package org.acme;

import io.smallrye.jwt.auth.principal.*;
import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;
import java.security.Principal;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class JWTAuthenticationFilter implements ContainerRequestFilter {

    private final JWTAuthContextInfo authContextInfo;

    public JWTAuthenticationFilter(JWTAuthContextInfo authContextInfo) {
        this.authContextInfo = authContextInfo;
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring("Bearer ".length());
            try {
                JWTCallerPrincipal callerPrincipal = JWTCallerPrincipalFactory.instance().parse(token, authContextInfo);
                SecurityContext originalContext = requestContext.getSecurityContext();
                SecurityContext newContext = new SecurityContext() {
                    @Override
                    public Principal getUserPrincipal() {
                        return callerPrincipal;
                    }

                    @Override
                    public boolean isUserInRole(String role) {
                        return callerPrincipal.getGroups().contains(role);
                    }

                    @Override
                    public boolean isSecure() {
                        return originalContext.isSecure();
                    }

                    @Override
                    public String getAuthenticationScheme() {
                        return "Bearer";
                    }
                };
                requestContext.setSecurityContext(newContext);
            } catch (ParseException e) {
                throw new IOException("Failed to parse JWT token", e);
            }
        }
    }
}

package no.intellitech.keycloak.provider;

import org.keycloak.OAuth2Constants;
import org.keycloak.broker.oidc.KeycloakOIDCIdentityProvider;
import org.keycloak.broker.oidc.OIDCIdentityProviderConfig;
import org.keycloak.broker.provider.AuthenticationRequest;
import org.keycloak.broker.social.SocialIdentityProvider;
import org.keycloak.models.KeycloakSession;

import javax.ws.rs.core.UriBuilder;

public class SignicatIdentityProvider extends KeycloakOIDCIdentityProvider
        implements SocialIdentityProvider<OIDCIdentityProviderConfig> {

    private static final String ARC_VALUE = "urn:signicat:oidc:method:nbid urn:signicat:oidc:method:nbid-mobil";

    public SignicatIdentityProvider(KeycloakSession session, OIDCIdentityProviderConfig config) {
        super(session, config);
    }

    @Override
    protected UriBuilder createAuthorizationUrl(AuthenticationRequest request) {
        UriBuilder uriBuilder = super.createAuthorizationUrl(request);
        uriBuilder.queryParam(OAuth2Constants.ACR_VALUES, ARC_VALUE);
        uriBuilder.queryParam("signicat_profile", "watercircles");
        logger.info("createAuthorizationUrl url: " + uriBuilder.toTemplate());
        return uriBuilder;
    }
}

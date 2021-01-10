package no.intellitech.keycloak.provider;

import org.keycloak.OAuth2Constants;
import org.keycloak.OAuthErrorException;
import org.keycloak.broker.oidc.AbstractOAuth2IdentityProvider;
import org.keycloak.broker.oidc.KeycloakOIDCIdentityProvider;
import org.keycloak.broker.oidc.OIDCIdentityProviderConfig;
import org.keycloak.broker.provider.AuthenticationRequest;
import org.keycloak.broker.provider.BrokeredIdentityContext;
import org.keycloak.broker.social.SocialIdentityProvider;
import org.keycloak.events.Errors;
import org.keycloak.events.EventBuilder;
import org.keycloak.events.EventType;
import org.keycloak.models.*;
import org.keycloak.services.ErrorPage;
import org.keycloak.services.messages.Messages;

import javax.ws.rs.GET;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

public class SignicatIdentityProvider extends KeycloakOIDCIdentityProvider
        implements SocialIdentityProvider<OIDCIdentityProviderConfig> {

    private static final String ARC_VALUE = "urn:signicat:oidc:method:nbid";

    public SignicatIdentityProvider(KeycloakSession session, OIDCIdentityProviderConfig config) {
        super(session, config);
        logger.info("SignicatIdentityProvider constructor");
    }

    @Override
    protected UriBuilder createAuthorizationUrl(AuthenticationRequest request) {
        logger.info("createAuthorizationUrl hei hei");
        UriBuilder uriBuilder = super.createAuthorizationUrl(request);
        uriBuilder.queryParam(OAuth2Constants.ACR_VALUES, ARC_VALUE);
        logger.info("createAuthorizationUrl url: " + uriBuilder.toTemplate());
        return uriBuilder;
    }

    @Override
    public Object callback(RealmModel realm, AuthenticationCallback callback, EventBuilder event) {
        return new SignicatEndpoint(realm, callback, event);
    }

    protected class SignicatEndpoint extends KeycloakEndpoint {
        public SignicatEndpoint(RealmModel realm,  AuthenticationCallback callback, EventBuilder event) {
            super(callback, realm, event);
        }

        @Override
        @GET
        public Response authResponse(@QueryParam(AbstractOAuth2IdentityProvider.OAUTH2_PARAMETER_STATE) String state,
                                     @QueryParam(AbstractOAuth2IdentityProvider.OAUTH2_PARAMETER_CODE) String authorizationCode,
                                     @QueryParam(OAuth2Constants.ERROR) String error) {

            logger.info("SignicatEndpoint.authResponse state: " + state);
            logger.info("SignicatEndpoint.authResponse authorizationCode: " + authorizationCode);
            logger.info("SignicatEndpoint.authResponse error: " + error);
            if (state == null) {
                return errorIdentityProviderLogin(Messages.IDENTITY_PROVIDER_MISSING_STATE_ERROR);
            }

            if (error != null) {
                logger.error(error + " for broker login " + getConfig().getProviderId());
                if (error.equals(ACCESS_DENIED)) {
                    return callback.cancelled(state);
                } else if (error.equals(OAuthErrorException.LOGIN_REQUIRED) || error.equals(OAuthErrorException.INTERACTION_REQUIRED)) {
                    return callback.error(state, error);
                } else {
                    return callback.error(state, Messages.IDENTITY_PROVIDER_UNEXPECTED_ERROR);
                }
            }

            try {

                if (authorizationCode != null) {
                    logger.info("SignicatEndpoint.authResponse authorizationCode != null: " + authorizationCode);
                    String response = generateTokenRequest(authorizationCode).asString();
                    logger.info("SignicatEndpoint.authResponse generateTokenRequest response: " + response);
                    BrokeredIdentityContext federatedIdentity = getFederatedIdentity(response);
                    logger.info("SignicatEndpoint.authResponse federatedIdentity: " + federatedIdentity);

                    logger.info("SignicatEndpoint.authResponse getConfig().isStoreToken(): " + getConfig().isStoreToken());
                    if (getConfig().isStoreToken()) {
                        // make sure that token wasn't already set by getFederatedIdentity();
                        // want to be able to allow provider to set the token itself.
                        logger.info("SignicatEndpoint.authResponse federatedIdentity.getToken() == null");
                        if (federatedIdentity.getToken() == null) {
                            logger.info("SignicatEndpoint.authResponse federatedIdentity.getToken() == null");
                            federatedIdentity.setToken(response);
                            logger.info("SignicatEndpoint.authResponse federatedIdentity.setToken response: " + response);
                        }
                    }

                    federatedIdentity.setIdpConfig(getConfig());
                    federatedIdentity.setIdp(SignicatIdentityProvider.this);
                    federatedIdentity.setCode(state);

                    logger.info("SignicatEndpoint.authResponse callback.authenticated federatedIdentity: " + federatedIdentity);
                    return callback.authenticated(federatedIdentity);
                }
            } catch (WebApplicationException e) {
                logger.error(e.getMessage(), e);
                return e.getResponse();
            } catch (Exception e) {
                logger.error("Failed to make identity provider oauth callback", e);
            }
            return errorIdentityProviderLogin(Messages.IDENTITY_PROVIDER_UNEXPECTED_ERROR);
        }

        private Response errorIdentityProviderLogin(String message) {
            event.event(EventType.LOGIN);
            event.error(Errors.IDENTITY_PROVIDER_LOGIN_FAILURE);
            return ErrorPage.error(session, null, Response.Status.BAD_GATEWAY, message);
        }
    }


    @Override
    public Response exchangeFromToken(UriInfo uriInfo, EventBuilder event, ClientModel authorizedClient, UserSessionModel tokenUserSession, UserModel tokenSubject, MultivaluedMap<String, String> params) {
        logger.info("exchangeFromToken pre hei hei");
        Response response = super.exchangeFromToken(uriInfo, event, authorizedClient, tokenUserSession, tokenSubject, params);
        logger.info("exchangeFromToken post hei hei");
        logger.info("exchangeFromToken post response: " + response);
        return response;
    }
}

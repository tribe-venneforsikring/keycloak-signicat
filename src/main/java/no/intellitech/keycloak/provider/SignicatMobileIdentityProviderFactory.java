package no.intellitech.keycloak.provider;

import org.keycloak.broker.provider.AbstractIdentityProviderFactory;
import org.keycloak.broker.social.SocialIdentityProviderFactory;
import org.keycloak.models.IdentityProviderModel;
import org.keycloak.models.KeycloakSession;

public class SignicatMobileIdentityProviderFactory extends AbstractIdentityProviderFactory<SignicatMobileIdentityProvider>
        implements SocialIdentityProviderFactory<SignicatMobileIdentityProvider> {

    public static final String PROVIDER_ID = "signicat-mobile-provider";
    public static final String PROVIDER_NAME = "Signicat BankID Mobile";

    @Override
    public String getName() {
        return PROVIDER_NAME;
    }

    @Override
    public SignicatMobileIdentityProvider create(KeycloakSession keycloakSession, IdentityProviderModel identityProviderModel) {
        return new SignicatMobileIdentityProvider(keycloakSession, new SignicatMobileIdentityProviderConfig(identityProviderModel));
    }

    @Override
    public SignicatMobileIdentityProviderConfig createConfig() {
        return new SignicatMobileIdentityProviderConfig();
    }

    @Override
    public String getId() {
        return PROVIDER_ID;
    }
}

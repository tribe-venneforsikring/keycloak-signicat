package no.intellitech.keycloak.provider;

import org.keycloak.broker.provider.AbstractIdentityProviderFactory;
import org.keycloak.broker.social.SocialIdentityProviderFactory;
import org.keycloak.models.IdentityProviderModel;
import org.keycloak.models.KeycloakSession;

public class SignicatIdentityProviderFactory extends AbstractIdentityProviderFactory<SignicatIdentityProvider>
        implements SocialIdentityProviderFactory<SignicatIdentityProvider> {

    public static final String PROVIDER_ID = "signicat-provider";
    public static final String PROVIDER_NAME = "Signicat BankID";

    @Override
    public String getName() {
        return PROVIDER_NAME;
    }

    @Override
    public SignicatIdentityProvider create(KeycloakSession keycloakSession, IdentityProviderModel identityProviderModel) {
        return new SignicatIdentityProvider(keycloakSession, new SignicatIdentityProviderConfig(identityProviderModel));
    }

    @Override
    public SignicatIdentityProviderConfig createConfig() {
        return new SignicatIdentityProviderConfig();
    }

    @Override
    public String getId() {
        return PROVIDER_ID;
    }
}

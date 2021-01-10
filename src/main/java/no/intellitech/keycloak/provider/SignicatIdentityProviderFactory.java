package no.intellitech.keycloak.provider;

import org.jboss.logging.Logger;
import org.keycloak.broker.provider.AbstractIdentityProviderFactory;
import org.keycloak.broker.social.SocialIdentityProviderFactory;
import org.keycloak.models.IdentityProviderModel;
import org.keycloak.models.KeycloakSession;

public class SignicatIdentityProviderFactory extends AbstractIdentityProviderFactory<SignicatIdentityProvider>
        implements SocialIdentityProviderFactory<SignicatIdentityProvider> {

    protected static final Logger logger = Logger.getLogger(SignicatIdentityProviderFactory.class);

    public static final String[] COMPATIBLE_PROVIDER = {SignicatIdentityProviderFactory.PROVIDER_ID};

    public static final String PROVIDER_ID = "signicat-provider";
    public static final String PROVIDER_NAME = "Signicat BankID";

    @Override
    public String getName() {
        logger.info("SignicatIdentityProvider.getName hei hei");
        return PROVIDER_NAME;
    }

    @Override
    public SignicatIdentityProvider create(KeycloakSession keycloakSession, IdentityProviderModel identityProviderModel) {
        logger.info("SignicatIdentityProvider.create hei hei");
        return new SignicatIdentityProvider(keycloakSession, new SignicatIdentityProviderConfig(identityProviderModel));
    }

    @Override
    public SignicatIdentityProviderConfig createConfig() {
        logger.info("SignicatIdentityProvider.createConfig hei hei");
        return new SignicatIdentityProviderConfig();
    }

    @Override
    public String getId() {
        logger.info("SignicatIdentityProvider.getId hei hei");
        return PROVIDER_ID;
    }
}

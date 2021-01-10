package no.intellitech.keycloak.provider;

import org.keycloak.broker.oidc.OIDCIdentityProviderConfig;
import org.keycloak.models.IdentityProviderModel;

class SignicatIdentityProviderConfig extends OIDCIdentityProviderConfig {

    SignicatIdentityProviderConfig(IdentityProviderModel identityProviderModel) {
        super(identityProviderModel);
    }

    SignicatIdentityProviderConfig() {
        super();
    }

}


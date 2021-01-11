package no.intellitech.keycloak.provider;

import org.keycloak.broker.oidc.OIDCIdentityProviderConfig;
import org.keycloak.models.IdentityProviderModel;

class SignicatMobileIdentityProviderConfig extends OIDCIdentityProviderConfig {

    SignicatMobileIdentityProviderConfig(IdentityProviderModel identityProviderModel) {
        super(identityProviderModel);
    }

    SignicatMobileIdentityProviderConfig() {
        super();
    }

}


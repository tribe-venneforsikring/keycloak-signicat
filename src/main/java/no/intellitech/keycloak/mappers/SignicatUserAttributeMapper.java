package no.intellitech.keycloak.mappers;

import no.intellitech.keycloak.provider.SignicatIdentityProviderFactory;
import org.keycloak.broker.oidc.mappers.UserAttributeMapper;

public class SignicatUserAttributeMapper extends UserAttributeMapper {

    private static final String MAPPER_NAME = "signicat-user-attribute-mapper";

    @Override
    public String[] getCompatibleProviders() {
        return SignicatIdentityProviderFactory.COMPATIBLE_PROVIDER;
    }

    @Override
    public String getId() {
        return MAPPER_NAME;
    }

    @Override
    public String getDisplayType() {
        return "Signicat Attribute Importer";
    }
}

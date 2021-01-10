# Keycloak Signicat

Is a custom made identity provider for Signicat, which extends KeycloakOIDCIdentityProvider.
Made this due to Signicat's enterprise edition requires `arc_values=urn:signicat:oidc:method:nbid`
to be set as request parameter to the authorize url, and the regular KeycloakOIDCIdentityProvider
didn't support this

This project is built loosely on a [French project](https://github.com/InseeFr/Keycloak-FranceConnect)

## Deployment
To build the jar, simple run

    $ mvn package

Remember to bump the version in pom.xml

Copy the jar to this folder in [keycloak-heroku](https://github.com/tribe-venneforsikring/keycloak-heroku/tree/master/deployments)

If you bumped the version in pom, remember to also update the jar file name in the
[Dockerfile](https://github.com/tribe-venneforsikring/keycloak-heroku/blob/master/Dockerfile).

To deploy the jar to Keycloak, follow the instructions in [README](https://github.com/tribe-venneforsikring/keycloak-heroku)

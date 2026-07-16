import Keycloak from 'keycloak-js';
import axios from 'axios';

const keycloakConfig = {
  url: 'http://identity-provider:8181', 
  realm: 'spring-microservices-security-realm',
  clientId: 'library-frontend',
};

export const keycloak = new Keycloak(keycloakConfig);
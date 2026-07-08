import Keycloak from 'keycloak-js';
import axios from 'axios';

const keycloakConfig = {
  url: 'http://localhost:8080', 
  realm: 'your-realm-name',
  clientId: 'your-react-frontend-client-id',
};

export const keycloak = new Keycloak(keycloakConfig);

const api = axios.create({
  baseURL: 'http://localhost:9000',
});

api.interceptors.request.use(
  async (config) => {
    if (keycloak.authenticated) {
      try {
        await keycloak.updateToken(30);
        config.headers.Authorization = `Bearer ${keycloak.token}`;
      } catch (error) {
        console.error('Failed to refresh Keycloak token', error);
        keycloak.login(); 
      }
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

export default api;
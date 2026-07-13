import Keycloak from 'keycloak-js';
import axios from 'axios';

const keycloakConfig = {
  url: 'http://identity-provider:8181', 
  realm: 'spring-microservices-security-realm',
  clientId: 'spring-microservice',
};

export const keycloak = new Keycloak(keycloakConfig);

const api = axios.create({
  baseURL: 'http://localhost:9000',
});

api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('access_token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

export default api;
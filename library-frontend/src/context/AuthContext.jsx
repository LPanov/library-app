import React, { createContext, useContext, useState, useEffect } from 'react';
import { keycloak } from '../config/keycloak';

const AuthContext = createContext(null);

export function AuthProvider({ children }) {
  const [authenticated, setAuthenticated] = useState(keycloak.authenticated ?? false);

  useEffect(() => {
    keycloak.onAuthSuccess = () => setAuthenticated(true);
    keycloak.onAuthLogout = () => setAuthenticated(false);
    keycloak.onAuthRefreshError = () => setAuthenticated(false);
  }, []);

  const value = {
    authenticated,
    login: () => keycloak.login(),
    logout: () => keycloak.logout({ redirectUri: window.location.origin }),
    token: () => keycloak.token,
    hasRole: (role) => keycloak.hasRealmRole(role),
    username: keycloak.tokenParsed?.preferred_username,
  };

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}

export function useAuth() {
  return useContext(AuthContext);
}
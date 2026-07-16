import React from 'react'
import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.jsx'
import { BrowserRouter } from 'react-router-dom'
import { keycloak } from './config/keycloak'
import { AuthProvider } from './context/AuthContext'


const root = createRoot(document.getElementById('root'));

keycloak.init({
  onLoad: 'check-sso',
  checkLoginIframe: false,
  pkceMethod: 'S256',
  silentCheckSsoRedirectUri: window.location.origin + '/silent-check-sso.html',
})
  .then(() => {
    root.render(
      <StrictMode>
        <BrowserRouter>
          <AuthProvider>
            <App />
          </AuthProvider>
        </BrowserRouter>
      </StrictMode>
    );
  })
  .catch((err) => {
    console.error("Keycloak initialization failed", err);
    root.render(
      <StrictMode>
        <div style={{ padding: '2rem', textAlign: 'center' }}>
          <h2>Unable to reach the authentication server.</h2>
          <p>Please try again later.</p>
        </div>
      </StrictMode>
    );
  });


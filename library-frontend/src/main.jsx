import React from 'react'
import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.jsx'
import { BrowserRouter } from 'react-router-dom'
import { keycloak } from './config/keycloak'

const root = createRoot(document.getElementById('root'));

keycloak.init({
  onLoad: 'check-sso',
  checkLoginIframe: false
})
  .then(() => {
    root.render(
      <StrictMode>
        <BrowserRouter>
          <App />
        </BrowserRouter>
      </StrictMode>
    );
  })
  .catch((err) => {
    console.error("Keycloak initialization failed", err);
    root.render(
      <StrictMode>
        <BrowserRouter>
          <App />
        </BrowserRouter>
      </StrictMode>
    );
  });


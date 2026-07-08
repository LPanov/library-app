import React from 'react'
import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.jsx'
import { BrowserRouter } from 'react-router-dom'
import { keycloak } from './keycloak'

const root = ReactDOM.createRoot(document.getElementById('root'));

keycloak.init({ 
  onLoad: 'login-required', // Forces login immediately, change to 'check-sso' for optional login
  checkLoginIframe: false 
})
.then((authenticated) => {
  if (authenticated) {
    root.render(
      <StrictMode>
        <BrowserRouter>
          <App />
        </BrowserRouter>
      </StrictMode>
    );
  } else {
    window.location.reload();
  }
})
.catch((err) => {
  console.error("Keycloak initialization failed", err);
});


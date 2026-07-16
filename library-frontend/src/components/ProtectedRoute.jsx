import React, { useEffect } from 'react';
import { useAuth } from '../context/AuthContext';

export function ProtectedRoute({ children, requiredRole }) {
  const { authenticated, hasRole, login } = useAuth();

  useEffect(() => {
    if (!authenticated) {
      login();
    }
  }, [authenticated, login]);

  if (!authenticated) {
    return null;
  }

  if (requiredRole && !hasRole(requiredRole)) {
    return <div>You don't have access to this page.</div>;
  }

  return children;
}
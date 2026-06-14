
import './App.css'
import Dashboard from './pages/Dashboard/Dashboard'
import React from 'react'
import { Route, Routes } from 'react-router-dom'
import UserLayout from './pages/UserLayaout/UserLayout'
import Books from './pages/Books/Books'
import Loans from './pages/Loans/Loans' 
import Reservations from './pages/Reservations/Reservations'
import Fines from './pages/Fines/Fines'
import Wishlist from './pages/Reservations/Wishlist'
import Subscriptions from './pages/Subscriptions/Subscriptions'
import Profile from './pages/User/Profile'
import Home from './pages/Home/Home'

function App() {
  return (
    <>    
    <Routes>
      {/* 💡 FIXED: Home page is now standalone (no sidebar or navbar) */}
      <Route path="/" element={<Home />} />

      {/* All routes inside this block will automatically display the UserLayout */}
      <Route element={<UserLayout />}>
        {/* user routes */}
        <Route path="/dashboard" element={<Dashboard />} />
        <Route path="/settings" element={<UserLayout />} /> {/* Note: You might want to change this element to a Settings component later! */}
        <Route path="/profile" element={<Profile />} />
        <Route path="/books" element={<Books />} />
        <Route path="/loans" element={<Loans />} />
        <Route path="/fines" element={<Fines />} />
        <Route path="/reservations" element={<Reservations />} />
        <Route path="/wishlist" element={<Wishlist />} />
        <Route path="/subscriptions" element={<Subscriptions />} />
      </Route>
    </Routes>
    </>
  )
}

export default App


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

function App() {

  return (
    <>    
    <Routes>
      <Route element={<UserLayout />}>
        {/* user routes */}
        <Route path="" element={<Dashboard />} />
        <Route path="settings" element={<UserLayout />} />
        <Route path="profile" element={<Profile />} />
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

import React from 'react';
import { Box, Toolbar } from '@mui/material';
import { Outlet } from 'react-router-dom';
import UserSidebar from './UserSideBar';
import Navbar from './Navbar';

const drawerWidth = 240;

const UserLayout = () => {
  return (
    <Box sx={{ display: 'flex', minHeight: '100vh', bgcolor: '#f5f5f5' }}>
      {/* app bar */}
      <Navbar/>

      {/* profile menu */}

      {/* user sidebar */}
      <UserSidebar />

      {/* main content */}
      <Box
        component="main"
        sx={{
          flexGrow: 1,
          width: { md: `calc(100% - ${drawerWidth}px)` },
          minHeight: "100vh",
          p: 2,
        }}
      >
        <Toolbar />
        <Box>
            <Outlet />
        </Box>
      </Box>
    </Box>
  );
};

export default UserLayout;

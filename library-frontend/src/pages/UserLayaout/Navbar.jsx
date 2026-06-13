import React from 'react';
import { Box, Toolbar, Tooltip, IconButton, Typography, Avatar, AppBar } from '@mui/material'; 
import { Notifications, Menu as MenuIcon, Contrast as ContrastIcon } from '@mui/icons-material';
import SearchIcon from '@mui/icons-material/Search'; // changed Contrast to actual Search icon
import { useLocation } from 'react-router-dom'; // Needed for active title tracking

const Navbar = () => {
    const drawerWidth = 240;
    const location = useLocation(); 

    const user = {
        fullName: "John Doe",
        profilePicture: "https://randomuser.me/api/portraits/men/54.jpg"
    };

    const handleDrawerToggle = () => {
        // Implement mobile toggle logic here if needed
    };

    return (
        <AppBar position="fixed" sx={{
            width: { md: `calc(100% - ${drawerWidth}px)` }, // Added space after minus
            ml: { md: `${drawerWidth}px` },
            bgcolor: "white",
            color: "text.primary",
            boxShadow: "0 1px 3px rgba(0,0,0,0.08)"
        }}>
            <Toolbar>
                <IconButton
                    color="inherit"
                    edge="start"
                    onClick={handleDrawerToggle}
                    sx={{ mr: 2, display: { md: 'none' }}}
                >
                    <MenuIcon />
                </IconButton>
                
                <Typography 
                    variant="h6"
                    noWrap
                    component="div"
                    sx={{ flexGrow: 1, fontWeight: 600 }}>
                        {/* Dynamic placeholder or add your navigation mapping logic here */}
                        Library Hub
                </Typography>

                <Tooltip title="Search">
                    <IconButton>
                        <SearchIcon />
                    </IconButton>
                </Tooltip>

                <IconButton color="inherit">
                    <Notifications />
                </IconButton>

                <IconButton sx={{ ml: 2 }}>
                    <ContrastIcon />
                </IconButton>

                <Tooltip title="Account">
                    <IconButton sx={{ ml: 1 }}>
                        <Avatar src={user?.profilePicture} sx={{ width: 36, height: 36 }}>
                            {user?.fullName?.charAt(0)}
                        </Avatar>
                    </IconButton>
                </Tooltip>
            </Toolbar>
        </AppBar>
    );
};

export default Navbar;
import React from 'react';
import { Avatar, Box, Typography, List, ListItem, ListItemIcon, ListItemText, Tooltip, ListItemButton, alpha, Divider } from '@mui/material';
import MenuBook from '@mui/icons-material/MenuBook';
import { navigationItems, secondaryItems } from './NavigationItems';
import { useLocation, useNavigate } from 'react-router-dom';
import Logout from '@mui/icons-material/Logout';

const SidebarDrawer = () => {
  const location = useLocation();
  const navigate = useNavigate();

  const isActive = (path) => {
    if (path === '/') {
      return location.pathname === "/";
    }
    return location.pathname.startsWith(path);
  };

  const handleChangePath = (path) => {
    navigate(path);
  };

  const handleLogout = () => {
    console.log("Logout clicked");
  };

  return (
    <Box
      sx={{
        height: "100vh", // Force container to match the exact view height
        display: "flex",
        flexDirection: "column",
        background: "linear-gradient(180deg, #1e293b 0%, #0f172a 100%)",
        color: "white",
        position: "relative",
        overflow: "hidden",
        '&::before': {
          content: '""',
          position: 'absolute',
          top: 0,
          left: 0,
          right: 0,
          height: '200px', // Slightly lower background splash depth
          background: 'radial-gradient(circle at 50% 0%, rgba(99, 102, 241, 0.15) 0%, transparent 100%)',
          pointerEvents: 'none',
        },
      }}
    >
      {/* COMPACT BRAND HEADER SECTION */}
      <Box 
        sx={{
          p: 3,
          display: "flex",
          alignItems: "center",
          gap: 1.5,
          position: "relative",
          zIndex: 1
        }}
      >
        <Avatar sx={{
          width: 48,
          height: 48,
          background: 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)',
          fontWeight: 'bold',
          boxShadow: '0 6px 20px rgba(102, 126, 234, 0.3)'
        }}>
          <MenuBook sx={{ fontSize: '1.1rem' }} />
        </Avatar>

        <Box>
          <Typography 
            variant="h6" //
            sx={{ 
              lineHeight: 1.2,
              fontWeight: "bold",
              background: "white",
              backgroundClip: "text",
              WebkitBackgroundClip: "text",
              WebkitTextFillColor: "transparent"
            }}
          >
            User Name
          </Typography>
          <Typography 
            sx={{ 
              opacity: 0.6,
              fontWeight: 600,
              fontSize: '0.65rem', // Highly crisp tiny label typography
              letterSpacing: 0.8,
              textTransform: "uppercase"
            }}
          >
            Library Hub
          </Typography>
        </Box>
      </Box> 

      {/* MID-SECTION ITEMS CONTAINER CONTAINER */}
      <Box sx={{ flexGrow: 1, px: 1 }}>
        {/* Main Navigation Items */}
        <List disablePadding>
          {navigationItems.map((item, index) => {
            const active = isActive(item.path);
            return (
              <ListItem key={index} disablePadding sx={{ mb: 0.25 }}>
                <Tooltip title={item.title} placement="right">
                  <ListItemButton
                    onClick={() => handleChangePath(item.path)}
                    sx={{
                      borderRadius: 2,
                      py: 0.75, // Lower vertical density spacing from 1.5 to 0.75
                      px: 1.5,
                      transition: 'all 0.3s cubic-bezier(0.4, 0, 0.2, 1)',
                      position: 'relative',
                      bgcolor: active
                        ? 'linear-gradient(135deg, rgba(99, 102, 241, 0.25) 0%, rgba(168, 85, 247, 0.25) 100%)'
                        : 'transparent',
                      border: active ? '1px solid rgba(99, 102, 241, 0.3)' : '1px solid transparent',
                      backdropFilter: active ? 'blur(10px)' : 'none',
                      '&:hover': {
                        bgcolor: active ? alpha('#6366f1', 0.3) : 'rgba(255, 255, 255, 0.05)',
                        transform: 'translateX(4px)',
                        border: '1px solid rgba(255, 255, 255, 0.08)',
                      },
                      '&::before': active ? {
                        content: '""',
                        position: 'absolute',
                        left: 0,
                        top: '50%',
                        transform: 'translateY(-50%)',
                        width: 3,
                        height: '55%',
                        borderRadius: '0 4px 4px 0',
                        background: 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)',
                        boxShadow: '0 0 12px rgba(102, 126, 234, 0.6)',
                      } : {},
                    }}
                  >
                    <ListItemIcon sx={{
                      minWidth: 34, // Scaled down inline-width padding from 48 to 34
                      color: active ? '#818cf8' : 'rgba(255, 255, 255, 0.7)',
                      '& .MuiSvgIcon-root': { fontSize: '1.25rem' } // Slightly smaller icons
                    }}>
                      {item.icon}
                    </ListItemIcon>
                    <ListItemText 
                      primary={item.title} 
                      primaryTypographyProps={{ fontSize: '0.82rem', color: 'white' }} 
                    />
                  </ListItemButton>
                </Tooltip>
              </ListItem>
            );
          })}
        </List>

        <Divider sx={{ width: '90%', borderColor: 'rgba(255, 255, 255, 0.1)', my: 1, mx: 'auto' }} />
        
        {/* Secondary Navigation Items */}
        <List disablePadding>
          {secondaryItems.map((item, index) => {
            const active = isActive(item.path);
            return (
              <ListItem key={index} disablePadding sx={{ mb: 0.25 }}>
                <Tooltip title={item.title} placement="right">
                  <ListItemButton
                    onClick={() => handleChangePath(item.path)}
                    sx={{
                      borderRadius: 2,
                      py: 0.75, // Lower vertical density spacing from 1.5 to 0.75
                      px: 1.5,
                      transition: 'all 0.3s cubic-bezier(0.4, 0, 0.2, 1)',
                      position: 'relative',
                      bgcolor: active
                        ? 'linear-gradient(135deg, rgba(99, 102, 241, 0.25) 0%, rgba(168, 85, 247, 0.25) 100%)'
                        : 'transparent',
                      border: active ? '1px solid rgba(99, 102, 241, 0.3)' : '1px solid transparent',
                      backdropFilter: active ? 'blur(10px)' : 'none',
                      '&:hover': {
                        bgcolor: active ? alpha('#6366f1', 0.3) : 'rgba(255, 255, 255, 0.05)',
                        transform: 'translateX(4px)',
                        border: '1px solid rgba(255, 255, 255, 0.08)',
                      },
                      '&::before': active ? {
                        content: '""',
                        position: 'absolute',
                        left: 0,
                        top: '50%',
                        transform: 'translateY(-50%)',
                        width: 3,
                        height: '55%',
                        borderRadius: '0 4px 4px 0',
                        background: 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)',
                        boxShadow: '0 0 12px rgba(102, 126, 234, 0.6)',
                      } : {},
                    }}
                  >
                    <ListItemIcon sx={{
                      minWidth: 34, // Scaled down inline-width padding from 48 to 34
                      color: active ? '#818cf8' : 'rgba(255, 255, 255, 0.7)',
                      '& .MuiSvgIcon-root': { fontSize: '1.25rem' }
                    }}>
                      {item.icon}
                    </ListItemIcon>
                    <ListItemText 
                      primary={item.title} 
                      primaryTypographyProps={{ fontSize: '0.82rem', color: 'white' }} 
                    />
                  </ListItemButton>
                </Tooltip>
              </ListItem>
            );
          })}
        </List>
      </Box>

      {/* FLOATING ACTION BOTTOM STICKY FOOTER ZONE */}
      <Box sx={{ p: 1, mt: 'auto' }}>
        <ListItemButton
          onClick={() => handleLogout()}
          sx={{
            borderRadius: 2,
            py: 0.8, // Reduced height padding
            px: 1.5,
            background: 'linear-gradient(135deg, rgba(239, 68, 68, 0.1) 0%, rgba(220, 38, 38, 0.1) 100%)',
            border: '1px solid rgba(239, 68, 68, 0.15)',
            transition: 'all 0.3s ease',
            '&:hover': {
              background: 'linear-gradient(135deg, rgba(239, 68, 68, 0.2) 0%, rgba(220, 38, 38, 0.2) 100%)',
              border: '1px solid rgba(239, 68, 68, 0.3)',
              transform: 'translateY(-1px)',
              boxShadow: '0 4px 12px rgba(239, 68, 68, 0.15)',
            },
          }}
        >
          <ListItemIcon sx={{
            minWidth: 34,
            color: "#f87171",
          }}>
            <Logout sx={{ fontSize: '1.2rem' }} />
          </ListItemIcon>
          <ListItemText 
            primary="Logout" 
            primaryTypographyProps={{ fontSize: '0.82rem', fontWeight: 600, color: '#f87171' }} 
          />
        </ListItemButton>

        <p className="pt-4 text-xs text-gray-600">&copy; 2026 Library App. All rights reserved.</p>
      </Box>
    </Box>
  );
};

export default SidebarDrawer;
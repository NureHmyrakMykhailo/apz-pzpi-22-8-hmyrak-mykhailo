import React, { useState } from 'react';
import { Outlet, useNavigate, useLocation } from 'react-router-dom';
import {
  AppBar,
  Box,
  CssBaseline,
  Drawer,
  IconButton,
  List,
  ListItem,
  ListItemIcon,
  ListItemText,
  Toolbar,
  Typography,
  Button,
  Menu,
  MenuItem,
} from '@mui/material';
import {
  Menu as MenuIcon,
  Dashboard as DashboardIcon,
  Book as BookIcon,
  People as PeopleIcon,
  Inventory as InventoryIcon,
  BarChart as BarChartIcon,
  Logout as LogoutIcon,
} from '@mui/icons-material';
import TranslateIcon from '@mui/icons-material/Translate';
import { useAuth } from '../contexts/AuthContext';
import { useTranslation } from 'react-i18next';

const drawerWidth = 240;

const Layout: React.FC = () => {
  const [mobileOpen, setMobileOpen] = useState(false);
  const { user, logout } = useAuth();
  const navigate = useNavigate();
  const location = useLocation();
  const { t, i18n } = useTranslation();
  const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);

  // Додаємо Users тільки для Admin
  const menuItems = [
    { text: t('dashboard'), icon: <DashboardIcon />, path: '/' },
    { text: t('books'), icon: <BookIcon />, path: '/books' },
    { text: t('readers'), icon: <PeopleIcon />, path: '/readers' },
    // { text: t('persons'), icon: <PeopleIcon />, path: '/persons' },
    { text: t('statistics'), icon: <BarChartIcon />, path: '/statistics' },
    { text: t('sensors'), icon: <InventoryIcon />, path: '/sensors' },
    ...(user?.role === 'Admin'
      ? [{ text: t('users'), icon: <PeopleIcon />, path: '/users' }]
      : [])
  ];

  const handleDrawerToggle = () => {
    setMobileOpen(!mobileOpen);
  };

  const handleNavigation = (path: string) => {
    if (location.pathname !== path) {
      navigate(path);
    }
    setMobileOpen(false);
  };

  const handleLangMenu = (event: React.MouseEvent<HTMLElement>) => {
    setAnchorEl(event.currentTarget);
  };

  const handleLangChange = (lang: string) => {
    i18n.changeLanguage(lang);
    setAnchorEl(null);
  };

  const drawer = (
    <div>
      <Toolbar>
        <Typography
          variant="h5"
          noWrap
          component="div"
          sx={{
            fontFamily: 'Merriweather, Georgia, serif',
            fontWeight: 700,
            color: 'primary.main',
            letterSpacing: '0.04em',
            fontSize: { xs: '1.1rem', sm: '1.3rem', md: '1.5rem' },
            maxWidth: 200,
            whiteSpace: 'nowrap',
            overflow: 'hidden',
            textOverflow: 'ellipsis',
          }}
        >
          {t('librarySystem')}
        </Typography>
      </Toolbar>
      <List>
        {menuItems.map((item) => (
          <ListItem
            key={item.text}
            onClick={() => handleNavigation(item.path)}
            sx={{
              cursor: 'pointer',
              borderRadius: 2,
              mb: 0.5,
              bgcolor: location.pathname === item.path ? 'secondary.main' : 'inherit',
              color: location.pathname === item.path ? 'primary.contrastText' : 'inherit',
              '&:hover': {
                bgcolor: 'secondary.main',
                color: 'primary.contrastText',
              },
            }}
          >
            <ListItemIcon sx={{ color: 'primary.main' }}>{item.icon}</ListItemIcon>
            <ListItemText primary={item.text} />
          </ListItem>
        ))}
      </List>
    </div>
  );
  console.log('user:', user);
  return (
    <Box sx={{ display: 'flex' }}>
      <CssBaseline />
      <AppBar
        position="fixed"
        sx={{
          width: { sm: `calc(100% - ${drawerWidth}px)` },
          ml: { sm: `${drawerWidth}px` },
          bgcolor: 'primary.main',
          boxShadow: 3,
          borderRadius: '0 0 20px 20px',
        }}
        
      >
        <Toolbar>
          <IconButton
            color="inherit"
            aria-label="open drawer"
            edge="start"
            onClick={handleDrawerToggle}
            sx={{ mr: 2, display: { sm: 'none' } }}
          >
            <MenuIcon />
          </IconButton>
          <Typography
            variant="h6"
            noWrap={false}
            component="div"
            sx={{
              fontFamily: 'Merriweather, Georgia, serif',
              fontWeight: 600,
              color: 'background.paper',
              letterSpacing: '0.04em',
              fontSize: { xs: '1.1rem', sm: '1.3rem', md: '1.5rem' },
              flexGrow: 1,
            }}
          >
            {t('librarySystem')}
          </Typography>
          <Box sx={{ display: 'flex', alignItems: 'center', ml: 'auto' }}>
            <Button
              color="inherit"
              startIcon={<TranslateIcon />}
              onClick={handleLangMenu}
              sx={{ mr: 1, minWidth: 0, px: 1 }}
            >
              {i18n.language === 'uk' ? 'UA' : 'EN'}
            </Button>
            <Menu
              anchorEl={anchorEl}
              open={Boolean(anchorEl)}
              onClose={() => setAnchorEl(null)}
            >
              <MenuItem
                selected={i18n.language === 'uk'}
                onClick={() => handleLangChange('uk')}
              >
                UA - Українська
              </MenuItem>
              <MenuItem
                selected={i18n.language === 'en'}
                onClick={() => handleLangChange('en')}
              >
                EN - English
              </MenuItem>
            </Menu>
            <Button
              color="inherit"
              onClick={logout}
              startIcon={<LogoutIcon />}
              sx={{
                bgcolor: 'secondary.main',
                color: 'primary.main',
                borderRadius: 2,
                px: 2,
                fontWeight: 600,
                boxShadow: 1,
                '&:hover': {
                  bgcolor: 'primary.main',
                  color: 'background.paper',
                },
              }}
            >
              {t('logout')}
            </Button>
          </Box>
        </Toolbar>
      </AppBar>
      <Box
        component="nav"
        sx={{ width: { sm: drawerWidth }, flexShrink: { sm: 0 } }}
      >
        <Drawer
          variant="temporary"
          open={mobileOpen}
          onClose={handleDrawerToggle}
          ModalProps={{
            keepMounted: true,
          }}
          sx={{
            display: { xs: 'block', sm: 'none' },
            '& .MuiDrawer-paper': {
              boxSizing: 'border-box',
              width: drawerWidth,
            },
          }}
        >
          {drawer}
        </Drawer>
        <Drawer
          variant="permanent"
          sx={{
            display: { xs: 'none', sm: 'block' },
            '& .MuiDrawer-paper': {
              boxSizing: 'border-box',
              width: drawerWidth,
            },
          }}
          open
        >
          {drawer}
        </Drawer>
      </Box>
      <Box
        component="main"
        sx={{
          flexGrow: 1,
          p: 3,
          width: { sm: `calc(100% - ${drawerWidth}px)` },
          mt: '64px',
        }}
      >
        <Outlet />
      </Box>
    </Box>
  );
};

export default Layout;
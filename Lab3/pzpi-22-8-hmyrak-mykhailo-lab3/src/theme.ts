import { createTheme } from '@mui/material/styles';

const theme = createTheme({
  palette: {
    mode: 'light',
    primary: {
      main: '#8D6748', // насыщенный коричневый
      contrastText: '#fff',
    },
    secondary: {
      main: '#D2B48C', // светло-коричневый (беж)
      contrastText: '#4E342E',
    },
    background: {
      default: '#F5E9DA', // очень светлый беж
      paper: '#FFF8F0',   // светлый фон карточек
    },
    text: {
      primary: '#4E342E', // темно-коричневый
      secondary: '#8D6748',
    },
    error: {
      main: '#B71C1C',
    },
    success: {
      main: '#6D9773',
    },
    warning: {
      main: '#D2B48C',
    },
    info: {
      main: '#A1887F',
    },
  },
  typography: {
    fontFamily: '"Merriweather", "Georgia", serif',
    h4: {
      fontWeight: 700,
      letterSpacing: '0.04em',
    },
    h5: {
      fontWeight: 600,
    },
    button: {
      textTransform: 'none',
      fontWeight: 600,
      letterSpacing: '0.03em',
    },
  },
  components: {
    MuiButton: {
      styleOverrides: {
        root: {
          borderRadius: 12,
          boxShadow: '0 2px 8px rgba(141, 103, 72, 0.08)',
        },
        containedPrimary: {
          background: 'linear-gradient(90deg, #8D6748 0%, #D2B48C 100%)',
          color: '#fff',
        },
        containedSecondary: {
          background: 'linear-gradient(90deg, #D2B48C 0%, #FFF8F0 100%)',
          color: '#4E342E',
        },
      },
    },
    MuiCard: {
      styleOverrides: {
        root: {
          borderRadius: 16,
          boxShadow: '0 4px 24px rgba(141, 103, 72, 0.10)',
          background: '#FFF8F0',
        },
      },
    },
    MuiPaper: {
      styleOverrides: {
        root: {
          borderRadius: 16,
        },
      },
    },
    MuiTableCell: {
      styleOverrides: {
        head: {
          background: '#D2B48C',
          color: '#4E342E',
          fontWeight: 700,
        },
        body: {
          color: '#4E342E',
        },
      },
    },
    MuiDialog: {
      styleOverrides: {
        paper: {
          borderRadius: 20,
          background: '#FFF8F0',
        },
      },
    },
  },
});

export default theme;
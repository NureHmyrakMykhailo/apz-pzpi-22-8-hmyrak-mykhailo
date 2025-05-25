import React from 'react';
import { useQuery } from '@tanstack/react-query';
import {
  Paper,
  Typography,
  Box,
  Card,
  CardContent,
  CircularProgress,
  Button,
} from '@mui/material';
import {
  Book as BookIcon,
  People as PeopleIcon,
  Inventory as InventoryIcon,
  BarChart as BarChartIcon,
} from '@mui/icons-material';
import axios from 'axios';
import { useTranslation } from 'react-i18next';
import { useNavigate } from 'react-router-dom';


interface Stats {
  bookTitlesCount: number;
  bookItemsCount: number;
  itemsOnLoanCount: number;
  availableItemsCount: number;
  specialStorageCount: number;
  readersCount: number;
  activeReadersCount: number;
  popularBookTitlesCount: number;
  averageReadingTime: number;
  maxReadingTime: number;
}

const StatCard: React.FC<{
  title: string;
  value: number;
  icon: React.ReactNode;
  color: string;
}> = ({ title, value, icon, color }) => (
  <Card sx={{ height: '100%', minWidth: 200, flex: '1 1 200px' }}>
    <CardContent>
      <Box sx={{ display: 'flex', alignItems: 'center', mb: 2 }}>
        <Box
          sx={{
            backgroundColor: `${color}20`,
            borderRadius: '50%',
            p: 1,
            mr: 2,
            display: 'flex',
            alignItems: 'center',
          }}
        >
          {icon}
        </Box>
        <Typography variant="h6" component="div">
          {title}
        </Typography>
      </Box>
      <Typography variant="h4" component="div" sx={{ fontWeight: 'bold' }}>
        {value.toLocaleString()}
      </Typography>
    </CardContent>
  </Card>
);

const Dashboard: React.FC = () => {
  const { t } = useTranslation();
  const navigate = useNavigate();

  const { data: stats, isLoading, error } = useQuery<Stats>({
    queryKey: ['stats'],
    queryFn: async () => {
      const response = await axios.get('/stat');
      return response.data;
    },
  });

  if (isLoading) {
    return (
      <Box sx={{ display: 'flex', justifyContent: 'center', mt: 4 }}>
        <CircularProgress />
      </Box>
    );
  }

  if (error) {
    return (
      <Box sx={{ mt: 4 }}>
        <Typography color="error" align="center">
          {t('errorLoadingStatistics')}
        </Typography>
      </Box>
    );
  }

  if (!stats) return null;

  return (
    <Box>
      <Typography variant="h4" gutterBottom>
        {t('dashboard')}
      </Typography>
      <Box sx={{ display: 'flex', flexWrap: 'wrap', gap: 2, mb: 3 }}>
        <StatCard
          title={t('totalBooks')}
          value={stats.bookTitlesCount}
          icon={<BookIcon sx={{ color: '#1976d2' }} />}
          color="#1976d2"
        />
        <StatCard
          title={t('totalItems')}
          value={stats.bookItemsCount}
          icon={<InventoryIcon sx={{ color: '#2e7d32' }} />}
          color="#2e7d32"
        />
        <StatCard
          title={t('activeReaders')}
          value={stats.activeReadersCount}
          icon={<PeopleIcon sx={{ color: '#ed6c02' }} />}
          color="#ed6c02"
        />
        <StatCard
          title={t('availableItems')}
          value={stats.availableItemsCount}
          icon={<BarChartIcon sx={{ color: '#9c27b0' }} />}
          color="#9c27b0"
        />
      </Box>

      <Paper sx={{ p: 3, mb: 3 }}>
        <Typography variant="h6" gutterBottom>
          {t('additionalStatistics')}
        </Typography>
        <Box sx={{ display: 'flex', flexWrap: 'wrap', gap: 4 }}>
          <Box>
            <Typography variant="subtitle2" color="text.secondary">
              {t('itemsOnLoan')}
            </Typography>
            <Typography variant="h6">{stats.itemsOnLoanCount}</Typography>
          </Box>
          <Box>
            <Typography variant="subtitle2" color="text.secondary">
              {t('specialStorage')}
            </Typography>
            <Typography variant="h6">{stats.specialStorageCount}</Typography>
          </Box>
          <Box>
            <Typography variant="subtitle2" color="text.secondary">
              {t('totalReaders')}
            </Typography>
            <Typography variant="h6">{stats.readersCount}</Typography>
          </Box>
          <Box>
            <Typography variant="subtitle2" color="text.secondary">
              {t('popularBooks')}
            </Typography>
            <Typography variant="h6">{stats.popularBookTitlesCount}</Typography>
          </Box>
        </Box>
      </Paper>

      <Paper sx={{ p: 3 }}>
        <Typography variant="h6" gutterBottom>
          {t('readingStatistics')}
        </Typography>
        <Box sx={{ display: 'flex', flexWrap: 'wrap', gap: 4 }}>
          <Box>
            <Typography variant="subtitle2" color="text.secondary">
              {t('averageReadingTime')}
            </Typography>
            <Typography variant="h6">
              {Math.round(stats.averageReadingTime)} {t('minutes')}
            </Typography>
          </Box>
          <Box>
            <Typography variant="subtitle2" color="text.secondary">
              {t('maxReadingTime')}
            </Typography>
            <Typography variant="h6">
              {Math.round(stats.maxReadingTime)} {t('minutes')}
            </Typography>
          </Box>
        </Box>
      </Paper>

      {/* Дві кнопки під статистикою читання */}
      <Box sx={{ display: 'flex', gap: 2, mt: 3 }}>
  <Button
    variant="contained"
    color="primary"
    onClick={() => navigate('/books')}
  >
    {t('addBook')}
  </Button>
  <Button
    variant="contained"
    color="primary" // <-- ТАКИЙ ЖЕ ЯК У ПЕРШОЇ КНОПКИ
    onClick={() => navigate('/readers')}
    >
    {t('addReader')}
    </Button>
    </Box>
    </Box>
  );
};

export default Dashboard;
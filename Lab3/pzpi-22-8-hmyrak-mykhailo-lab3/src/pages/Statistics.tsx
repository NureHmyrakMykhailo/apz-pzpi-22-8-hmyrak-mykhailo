import React, { useEffect, useState } from 'react';
import { Box, Card, CardContent, CircularProgress, Container, Typography } from '@mui/material';
import axios from 'axios';
import { useTranslation } from 'react-i18next';
import { LibraryStats } from '../types/statistics';

const Statistics: React.FC = () => {
  const { t } = useTranslation();
  const [stats, setStats] = useState<LibraryStats | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    axios.get('/stat')
      .then(res => {
        const data = res.data;
        setStats({
          bookTitlesCount: data.bookTitlesCount ?? data.BookTitlesCount,
          bookItemsCount: data.bookItemsCount ?? data.BookItemsCount,
          itemsOnLoanCount: data.itemsOnLoanCount ?? data.ItemsOnLoanCount,
          availableItemsCount: data.availableItemsCount ?? data.AvailableItemsCount,
          specialStorageCount: data.specialStorageCount ?? data.SpecialStorageCount,
          readersCount: data.readersCount ?? data.ReadersCount,
          activeReadersCount: data.activeReadersCount ?? data.ActiveReadersCount,
          popularBookTitlesCount: data.popularBookTitlesCount ?? data.PopularBookTitlesCount,
          averageReadingTime: data.averageReadingTime ?? data.AverageReadingTime,
          maxReadingTime: data.maxReadingTime ?? data.MaxReadingTime,
        });
      })
      .finally(() => setLoading(false));
  }, []);

  if (loading) {
    return (
      <Container>
        <Box sx={{ display: 'flex', justifyContent: 'center', mt: 8 }}>
          <CircularProgress />
        </Box>
      </Container>
    );
  }

  if (!stats) {
    return (
      <Container>
        <Typography variant="h5" color="error" align="center" sx={{ mt: 8 }}>
          {t('failedToLoadStatistics')}
        </Typography>
      </Container>
    );
  }

  return (
    <Container>
      <Box sx={{ my: 4 }}>
        <Typography variant="h4" component="h1" gutterBottom>
          {t('statisticsTitle')}
        </Typography>
        <Box sx={{ display: 'flex', flexWrap: 'wrap', gap: 2, mb: 4 }}>
          <Card sx={{ flex: '1 1 220px', minWidth: 220 }}>
            <CardContent>
              <Typography variant="subtitle1" color="textSecondary" gutterBottom>
                {t('totalBooks')}
              </Typography>
              <Typography variant="h5">{stats.bookTitlesCount}</Typography>
            </CardContent>
          </Card>
          <Card sx={{ flex: '1 1 220px', minWidth: 220 }}>
            <CardContent>
              <Typography variant="subtitle1" color="textSecondary" gutterBottom>
                {t('totalItems')}
              </Typography>
              <Typography variant="h5">{stats.bookItemsCount}</Typography>
            </CardContent>
          </Card>
          <Card sx={{ flex: '1 1 220px', minWidth: 220 }}>
            <CardContent>
              <Typography variant="subtitle1" color="textSecondary" gutterBottom>
                {t('itemsOnLoan')}
              </Typography>
              <Typography variant="h5">{stats.itemsOnLoanCount}</Typography>
            </CardContent>
          </Card>
          <Card sx={{ flex: '1 1 220px', minWidth: 220 }}>
            <CardContent>
              <Typography variant="subtitle1" color="textSecondary" gutterBottom>
                {t('availableItems')}
              </Typography>
              <Typography variant="h5">{stats.availableItemsCount}</Typography>
            </CardContent>
          </Card>
        </Box>
        <Card sx={{ mb: 2 }}>
          <CardContent>
            <Typography variant="h6" gutterBottom>
              {t('additionalStatistics')}
            </Typography>
            <Typography>
              {t('itemsOnLoan')}: {stats.itemsOnLoanCount}
              &nbsp;&nbsp;{t('specialStorage')}: {stats.specialStorageCount}
              &nbsp;&nbsp;{t('totalReaders')}: {stats.readersCount}
              &nbsp;&nbsp;{t('popularBooks')}: {stats.popularBookTitlesCount}
            </Typography>
          </CardContent>
        </Card>
        <Card sx={{ mb: 2 }}>
          <CardContent>
            <Typography variant="h6" gutterBottom>
              {t('readingStatistics')}
            </Typography>
            <Typography>
              <b>{t('averageReadingTime')}:</b> {Math.round(stats.averageReadingTime)} {t('minutes')}
            </Typography>
            <Typography>
              <b>{t('maxReadingTime')}:</b> {Math.round(stats.maxReadingTime)} {t('minutes')}
            </Typography>
          </CardContent>
        </Card>
      </Box>
    </Container>
  );
};

export default Statistics;
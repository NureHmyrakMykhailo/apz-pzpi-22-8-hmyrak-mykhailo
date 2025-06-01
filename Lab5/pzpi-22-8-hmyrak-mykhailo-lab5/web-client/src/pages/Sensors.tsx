import React, { useEffect, useState } from 'react';
import { Box, Card, CardContent, Typography, CircularProgress, Container, Table, TableBody, TableCell, TableHead, TableRow, Chip } from '@mui/material';
import axios from 'axios';
import { useTranslation } from 'react-i18next';

interface ClimatMonitor {
  id: number;
  time: string;
  temperature: number;
  wet: number;
  isTemperatureOutOfBounds: boolean;
  isWetOutOfBounds: boolean;
}

interface SensorsData {
  result: ClimatMonitor[];
  timeTemperatureAbove: number;
  timeTemperatureBelow: number;
  timeWetAbove: number;
  timeWetBelow: number;
}

const Sensors: React.FC = () => {
  const { t } = useTranslation();
  const [data, setData] = useState<SensorsData | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    axios.get('/climatmonitors')
      .then(res => setData(res.data))
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

  if (!data) {
    return (
      <Container>
        <Typography variant="h5" color="error" align="center" sx={{ mt: 8 }}>
          {t('failedToLoadSensors')}
        </Typography>
      </Container>
    );
  }

  return (
    <Container>
      <Box sx={{ my: 4 }}>
        <Typography variant="h4" component="h1" gutterBottom>
          {t('sensorsData')}
        </Typography>
        <Card sx={{ mb: 3 }}>
          <CardContent>
            <Typography>
              <b>{t('timeTemperatureAbove')}:</b> {Math.round(data.timeTemperatureAbove / 60)} {t('minutes')}
            </Typography>
            <Typography>
              <b>{t('timeTemperatureBelow')}:</b> {Math.round(data.timeTemperatureBelow / 60)} {t('minutes')}
            </Typography>
            <Typography>
              <b>{t('timeHumidityAbove')}:</b> {Math.round(data.timeWetAbove / 60)} {t('minutes')}
            </Typography>
            <Typography>
              <b>{t('timeHumidityBelow')}:</b> {Math.round(data.timeWetBelow / 60)} {t('minutes')}
            </Typography>
          </CardContent>
        </Card>
        <Card>
          <CardContent>
            <Typography variant="h6" gutterBottom>
              {t('sensorReadings')}
            </Typography>
            <Box sx={{ overflowX: 'auto' }}>
              <Table>
                <TableHead>
                  <TableRow>
                    <TableCell>{t('time')}</TableCell>
                    <TableCell>{t('temperature')}</TableCell>
                    <TableCell>{t('humidity')}</TableCell>
                    <TableCell>{t('alerts')}</TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                  {data.result.map((row) => (
                    <TableRow key={row.id}>
                      <TableCell>{new Date(row.time).toLocaleString()}</TableCell>
                      <TableCell>
                        {row.temperature}
                        {row.isTemperatureOutOfBounds && (
                          <Chip label="!" color="error" size="small" sx={{ ml: 1 }} />
                        )}
                      </TableCell>
                      <TableCell>
                        {row.wet}
                        {row.isWetOutOfBounds && (
                          <Chip label="!" color="error" size="small" sx={{ ml: 1 }} />
                        )}
                      </TableCell>
                      <TableCell>
                        {row.isTemperatureOutOfBounds && <Chip label={t('tempAlert')} color="error" size="small" sx={{ mr: 1 }} />}
                        {row.isWetOutOfBounds && <Chip label={t('humidityAlert')} color="error" size="small" />}
                        {(!row.isTemperatureOutOfBounds && !row.isWetOutOfBounds) && <Chip label={t('ok')} color="success" size="small" />}
                      </TableCell>
                    </TableRow>
                  ))}
                </TableBody>
              </Table>
            </Box>
          </CardContent>
        </Card>
      </Box>
    </Container>
  );
};

export default Sensors;
import React, { useState, useEffect } from 'react';
import { Button, Container, Typography, Box } from '@mui/material';
import ReaderList from '../components/readers/ReaderList';
import ReaderForm from '../components/readers/ReaderForm';
import { Reader } from '../types/reader';
import axios from 'axios';
import { useTranslation } from 'react-i18next';

const Readers: React.FC = () => {
  const { t } = useTranslation();
  const [readers, setReaders] = useState<Reader[]>([]);
  const [isFormOpen, setIsFormOpen] = useState(false);
  const [selectedReader, setSelectedReader] = useState<Reader | null>(null);

  const fetchReaders = async () => {
    try {
      const response = await axios.get('/readers');
      setReaders(response.data);
    } catch (error) {
      console.error('Error fetching readers:', error);
    }
  };

  useEffect(() => {
    fetchReaders();
  }, []);

  const handleAddReader = () => {
    setSelectedReader(null);
    setIsFormOpen(true);
  };

  const handleEditReader = (reader: Reader) => {
    setSelectedReader(reader);
    setIsFormOpen(true);
  };

  const handleDeleteReader = async (id: number) => {
    if (window.confirm(t('deleteReaderConfirm'))) {
      try {
        await axios.delete(`/readers/${id}`);
        fetchReaders();
      } catch (error) {
        console.error('Error deleting reader:', error);
      }
    }
  };

  const handleFormSubmit = async (readerData: Partial<Reader>) => {
    try {
      if (selectedReader) {
        await axios.put(`/readers/${selectedReader.readerId}`, readerData);
      } else {
        await axios.post('/readers', readerData);
      }
      setIsFormOpen(false);
      fetchReaders();
    } catch (error) {
      console.error('Error saving reader:', error);
    }
  };

  return (
    <Container>
      <Box sx={{ my: 4 }}>
        <Typography variant="h4" component="h1" gutterBottom>
          {t('readersManagement')}
        </Typography>
        <Button
          variant="contained"
          color="primary"
          onClick={handleAddReader}
          sx={{ mb: 2 }}
        >
          {t('addReader')}
        </Button>
        <ReaderList
          readers={readers}
          onEdit={handleEditReader}
          onDelete={handleDeleteReader}
        />
      </Box>
      <ReaderForm
        open={isFormOpen}
        onClose={() => setIsFormOpen(false)}
        onSubmit={handleFormSubmit}
        reader={selectedReader}
      />
    </Container>
  );
};

export default Readers;
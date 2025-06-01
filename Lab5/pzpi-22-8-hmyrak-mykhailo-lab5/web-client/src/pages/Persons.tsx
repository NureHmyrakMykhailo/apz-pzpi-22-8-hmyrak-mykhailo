import React, { useState, useEffect } from 'react';
import { Button, Container, Typography, Box, TextField } from '@mui/material';
import axios from 'axios';
import { useTranslation } from 'react-i18next';
import PersonList from '../components/persons/PersonList';
import PersonForm from '../components/persons/PersonForm';
import { Person } from '../types/person';

const Persons: React.FC = () => {
  const { t } = useTranslation();
  const [persons, setPersons] = useState<Person[]>([]);
  const [isFormOpen, setIsFormOpen] = useState(false);
  const [selectedPerson, setSelectedPerson] = useState<Person | null>(null);
  const [searchName, setSearchName] = useState('');

  const fetchPersons = async (name?: string) => {
    try {
      let url = '/persons';
      if (name && name.trim() !== '') {
        url = `/persons/search?name=${encodeURIComponent(name)}`;
      }
      const response = await axios.get(url);
      setPersons(response.data);
    } catch (error) {
      console.error('Error fetching persons:', error);
    }
  };

  useEffect(() => {
    fetchPersons();
  }, []);

  const handleAddPerson = () => {
    setSelectedPerson(null);
    setIsFormOpen(true);
  };

  const handleEditPerson = (person: Person) => {
    setSelectedPerson(person);
    setIsFormOpen(true);
  };

  const handleDeletePerson = async (id: number) => {
    if (window.confirm(t('deletePersonConfirm'))) {
      try {
        await axios.delete(`/persons/${id}`);
        fetchPersons(searchName);
      } catch (error) {
        console.error('Error deleting person:', error);
      }
    }
  };

  const handleFormSubmit = async (personData: Partial<Person>) => {
    try {
      if (selectedPerson && selectedPerson.personId) {
        await axios.put(`/persons/${selectedPerson.personId}`, personData);
      } else {
        await axios.post('/persons', personData);
      }
      setIsFormOpen(false);
      fetchPersons(searchName);
    } catch (error) {
      console.error('Error saving person:', error);
    }
  };

  const handleSearch = (e: React.FormEvent) => {
    e.preventDefault();
    fetchPersons(searchName);
  };

  const handleReset = () => {
    setSearchName('');
    fetchPersons();
  };

  return (
    <Container>
      <Box sx={{ my: 4 }}>
        <Typography variant="h4" component="h1" gutterBottom>
          {t('personsManagement')}
        </Typography>
        <Button
          variant="contained"
          color="primary"
          onClick={handleAddPerson}
          sx={{ mb: 2 }}
        >
          {t('addPerson')}
        </Button>
        <Box component="form" onSubmit={handleSearch} sx={{ display: 'flex', gap: 1, mb: 2 }}>
          <TextField
            size="small"
            label={t('searchByName')}
            value={searchName}
            onChange={e => setSearchName(e.target.value)}
          />
          <Button type="submit" variant="contained" color="primary">
            {t('search')}
          </Button>
          <Button variant="outlined" onClick={handleReset}>
            {t('reset')}
          </Button>
        </Box>
        <PersonList
          persons={persons}
          onEdit={handleEditPerson}
          onDelete={handleDeletePerson}
        />
      </Box>
      <PersonForm
        open={isFormOpen}
        onClose={() => setIsFormOpen(false)}
        onSubmit={handleFormSubmit}
        person={selectedPerson}
      />
    </Container>
  );
};

export default Persons;
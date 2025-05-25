import React from 'react';
import {
  Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper, IconButton
} from '@mui/material';
import { Edit as EditIcon, Delete as DeleteIcon } from '@mui/icons-material';
import { Person } from '../../types/person';
import { useTranslation } from 'react-i18next';

interface PersonListProps {
  persons: Person[];
  onEdit: (person: Person) => void;
  onDelete: (id: number) => void;
}

const PersonList: React.FC<PersonListProps> = ({ persons, onEdit, onDelete }) => {
  const { t } = useTranslation();
  return (
    <TableContainer component={Paper}>
      <Table>
        <TableHead>
          <TableRow>
            <TableCell>{t('name')}</TableCell>
            <TableCell>{t('dateOfBirth')}</TableCell>
            <TableCell>{t('dateOfDeath')}</TableCell>
            <TableCell>{t('country')}</TableCell>
            <TableCell>{t('isReal')}</TableCell>
            <TableCell>{t('actions')}</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {persons.map((person) => (
            <TableRow key={person.personId}>
              <TableCell>{person.name}</TableCell>
              <TableCell>{person.dateOfBirth || '-'}</TableCell>
              <TableCell>{person.dateOfDeath || '-'}</TableCell>
              <TableCell>{person.country || '-'}</TableCell>
              <TableCell>{person.isReal ? t('yes') : t('no')}</TableCell>
              <TableCell>
                <IconButton onClick={() => onEdit(person)} color="primary" title={t('edit')}>
                  <EditIcon />
                </IconButton>
                <IconButton onClick={() => onDelete(person.personId!)} color="error" title={t('delete')}>
                  <DeleteIcon />
                </IconButton>
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
};

export default PersonList;
import React from 'react';
import {
  Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper, IconButton
} from '@mui/material';
import { Edit as EditIcon, Delete as DeleteIcon } from '@mui/icons-material';
import { Reader } from '../../types/reader';
import { useTranslation } from 'react-i18next';

interface ReaderListProps {
  readers: Reader[];
  onEdit: (reader: Reader) => void;
  onDelete: (id: number) => void;
}

const ReaderList: React.FC<ReaderListProps> = ({ readers, onEdit, onDelete }) => {
  const { t } = useTranslation();
  return (
    <TableContainer component={Paper}>
      <Table>
        <TableHead>
          <TableRow>
            <TableCell>{t('name')}</TableCell>
            <TableCell>{t('class')}</TableCell>
            <TableCell>{t('studentCard')}</TableCell>
            <TableCell>{t('birthday')}</TableCell>
            <TableCell>{t('phone')}</TableCell>
            <TableCell>{t('email')}</TableCell>
            <TableCell>{t('address')}</TableCell>
            <TableCell>{t('actions')}</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {readers.map((reader) => (
            <TableRow key={reader.readerId}>
              <TableCell>{reader.name}</TableCell>
              <TableCell>{reader.class}</TableCell>
              <TableCell>{reader.studentCard}</TableCell>
              <TableCell>{reader.birthday}</TableCell>
              <TableCell>{reader.phone}</TableCell>
              <TableCell>{reader.email}</TableCell>
              <TableCell>{reader.address}</TableCell>
              <TableCell>
                <IconButton onClick={() => onEdit(reader)} color="primary" title={t('edit')}>
                  <EditIcon />
                </IconButton>
                <IconButton onClick={() => onDelete(reader.readerId)} color="error" title={t('delete')}>
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

export default ReaderList;
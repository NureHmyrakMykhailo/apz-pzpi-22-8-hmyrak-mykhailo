import React from 'react';
import {
  Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper, IconButton, Chip
} from '@mui/material';
import { Edit as EditIcon, Delete as DeleteIcon } from '@mui/icons-material';
import { Book } from '../../types/book';
import { useTranslation } from 'react-i18next';

interface BookListProps {
  books: Book[];
  onEdit: (book: Book) => void;
  onDelete: (id: number) => void;
}

const BookList: React.FC<BookListProps> = ({ books, onEdit, onDelete }) => {
  const { t } = useTranslation();
  return (
    <TableContainer component={Paper}>
      <Table>
        <TableHead>
          <TableRow>
            <TableCell>{t('title')}</TableCell>
            <TableCell>{t('isbn')}</TableCell>
            <TableCell>{t('pages')}</TableCell>
            <TableCell>{t('publisher')}</TableCell>
            <TableCell>{t('categoryId')}</TableCell>
            <TableCell>{t('class')}</TableCell>
            <TableCell>{t('language')}</TableCell>
            <TableCell>{t('year')}</TableCell>
            <TableCell>{t('available')}</TableCell>
            <TableCell>{t('actions')}</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {books.map((book) => (
            <TableRow key={book.bookId}>
              <TableCell>{book.title}</TableCell>
              <TableCell>{book.isbn}</TableCell>
              <TableCell>{book.pages}</TableCell>
              <TableCell>{book.publish}</TableCell>
              <TableCell>{book.categoryId}</TableCell>
              <TableCell>{book.class}</TableCell>
              <TableCell>{book.lang}</TableCell>
              <TableCell>{book.year}</TableCell>
              <TableCell>
                <Chip
                  label={`${book.availableItemsCount}/${book.itemsCount}`}
                  color={book.availableItemsCount > 0 ? 'success' : 'error'}
                />
              </TableCell>
              <TableCell>
                <IconButton onClick={() => onEdit(book)} color="primary" title={t('edit')}>
                  <EditIcon />
                </IconButton>
                <IconButton onClick={() => onDelete(book.bookId)} color="error" title={t('delete')}>
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

export default BookList;
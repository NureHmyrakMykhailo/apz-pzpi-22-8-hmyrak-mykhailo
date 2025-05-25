import React, { useState, useEffect } from 'react';
import {
  Dialog, DialogTitle, DialogContent, DialogActions, Button, TextField
} from '@mui/material';
import { Book } from '../../types/book';
import { useTranslation } from 'react-i18next';
import ItemsDialog from '../../pages/ItemsDialog';

interface BookFormProps {
  open: boolean;
  onClose: () => void;
  onSubmit: (bookData: Partial<Book>) => void;
  book: Book | null;
}

const BookForm: React.FC<BookFormProps> = ({ open, onClose, onSubmit, book }) => {
  const { t } = useTranslation();
  const [formData, setFormData] = useState<Partial<Book>>({
    title: '',
    isbn: '',
    pages: undefined,
    publish: '',
    categoryId: undefined,
    class: '',
    lang: '',
    year: undefined,
  });

  const [itemsDialogOpen, setItemsDialogOpen] = useState(false);

  useEffect(() => {
    if (book) setFormData(book);
    else setFormData({
      title: '',
      isbn: '',
      pages: undefined,
      publish: '',
      categoryId: undefined,
      class: '',
      lang: '',
      year: undefined,
    });
  }, [book]);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: ['pages', 'categoryId', 'year'].includes(name)
        ? value === '' ? undefined : parseInt(value)
        : value,
    }));
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (!formData.title || formData.title.trim() === '') {
      alert(t('titleRequired'));
      return;
    }
    const dataToSend: Partial<Book> = {};
    Object.keys(formData).forEach((key) => {
      const value = (formData as any)[key];
      if (value !== undefined && value !== '') {
        (dataToSend as any)[key] = value;
      }
    });
    onSubmit(dataToSend);
  };

  return (
    <>
      <Dialog open={open} onClose={onClose} maxWidth="sm" fullWidth>
        <DialogTitle>{book ? t('editBook') : t('addBook')}</DialogTitle>
        <form onSubmit={handleSubmit}>
          <DialogContent>
            <TextField fullWidth margin="normal" label={t('title') + ' *'} name="title" value={formData.title} onChange={handleChange} required />
            <TextField fullWidth margin="normal" label={t('isbn')} name="isbn" value={formData.isbn} onChange={handleChange} />
            <TextField fullWidth margin="normal" label={t('pages')} name="pages" type="number" value={formData.pages ?? ''} onChange={handleChange} />
            <TextField fullWidth margin="normal" label={t('publisher')} name="publish" value={formData.publish} onChange={handleChange} />
            <TextField fullWidth margin="normal" label={t('categoryId')} name="categoryId" type="number" value={formData.categoryId ?? ''} onChange={handleChange} />
            <TextField fullWidth margin="normal" label={t('class')} name="class" value={formData.class} onChange={handleChange} />
            <TextField fullWidth margin="normal" label={t('language')} name="lang" value={formData.lang} onChange={handleChange} />
            <TextField fullWidth margin="normal" label={t('year')} name="year" type="number" value={formData.year ?? ''} onChange={handleChange} />
          </DialogContent>
          <DialogActions>
            {book && (
              <Button
              variant="outlined"
              onClick={() => setItemsDialogOpen(true)}
              >
              {t('viewBookItems')}
            </Button>
            )}
            <Button onClick={onClose}>{t('cancel')}</Button>
            <Button type="submit" variant="contained" color="primary">
              {book ? t('saveChanges') : t('addBook')}
            </Button>
          </DialogActions>
        </form>
      </Dialog>
      {book && (
        <ItemsDialog
          open={itemsDialogOpen}
          onClose={() => setItemsDialogOpen(false)}
          bookId={book.bookId}
        />
      )}
    </>
  );
};

export default BookForm;
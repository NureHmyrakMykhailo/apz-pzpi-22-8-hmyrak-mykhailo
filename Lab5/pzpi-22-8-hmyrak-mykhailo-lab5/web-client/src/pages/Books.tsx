import React, { useState, useEffect } from 'react';
import { Button, Container, Typography, Box, TextField } from '@mui/material';
import BookList from '../components/books/BookList';
import BookForm from '../components/books/BookForm';
import { Book } from '../types/book';
import axios from 'axios';
import { useTranslation } from 'react-i18next';

const Books: React.FC = () => {
  const { t } = useTranslation();
  const [books, setBooks] = useState<Book[]>([]);
  const [isFormOpen, setIsFormOpen] = useState(false);
  const [selectedBook, setSelectedBook] = useState<Book | null>(null);
  const [searchTitle, setSearchTitle] = useState('');

  const fetchBooks = async (title?: string) => {
    try {
      let url = '/books';
      if (title && title.trim() !== '') {
        url = `/books/search?title=${encodeURIComponent(title)}`;
      }
      const response = await axios.get(url);
      setBooks(response.data);
    } catch (error) {
      console.error('Error fetching books:', error);
    }
  };

  useEffect(() => {
    fetchBooks();
  }, []);

  const handleAddBook = () => {
    setSelectedBook(null);
    setIsFormOpen(true);
  };

  const handleEditBook = (book: Book) => {
    setSelectedBook(book);
    setIsFormOpen(true);
  };

  const handleDeleteBook = async (id: number) => {
    if (window.confirm(t('deleteBookConfirm'))) {
      try {
        await axios.delete(`/books/${id}`);
        fetchBooks(searchTitle);
      } catch (error) {
        console.error('Error deleting book:', error);
      }
    }
  };

  const handleFormSubmit = async (bookData: Partial<Book>) => {
    try {
      if (selectedBook) {
        await axios.put(`/books/${selectedBook.bookId}`, bookData);
      } else {
        await axios.post('/books', bookData);
      }
      setIsFormOpen(false);
      fetchBooks(searchTitle);
    } catch (error) {
      console.error('Error saving book:', error);
    }
  };

  // --- Пошук по назві ---
  const handleSearch = (e: React.FormEvent) => {
    e.preventDefault();
    fetchBooks(searchTitle);
  };

  const handleReset = () => {
    setSearchTitle('');
    fetchBooks();
  };

  return (
    <Container>
      <Box sx={{ my: 4 }}>
        <Typography variant="h4" component="h1" gutterBottom>
          {t('booksManagement')}
        </Typography>
        <Button
          variant="contained"
          color="primary"
          onClick={handleAddBook}
          sx={{ mb: 2 }}
        >
          {t('addBook')}
        </Button>
        {/* Пошук по назві */}
        <Box component="form" onSubmit={handleSearch} sx={{ display: 'flex', gap: 1, mb: 2 }}>
          <TextField
            size="small"
            label={t('searchByTitle')}
            value={searchTitle}
            onChange={e => setSearchTitle(e.target.value)}
          />
          <Button type="submit" variant="contained" color="primary">
            {t('search')}
          </Button>
          <Button variant="outlined" onClick={handleReset}>
            {t('reset')}
          </Button>
        </Box>
        <BookList
          books={books}
          onEdit={handleEditBook}
          onDelete={handleDeleteBook}
        />
      </Box>
      <BookForm
        open={isFormOpen}
        onClose={() => setIsFormOpen(false)}
        onSubmit={handleFormSubmit}
        book={selectedBook}
      />
    </Container>
  );
};

export default Books;
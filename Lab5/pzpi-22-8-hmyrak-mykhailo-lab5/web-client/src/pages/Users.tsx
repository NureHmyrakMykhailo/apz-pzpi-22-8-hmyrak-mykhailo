import React, { useEffect, useState } from 'react';
import {
  Container, Typography, Box, Table, TableHead, TableRow, TableCell, TableBody,
  IconButton, Dialog, DialogTitle, DialogContent, DialogActions, Button, TextField, MenuItem
} from '@mui/material';
import { Edit as EditIcon, Delete as DeleteIcon } from '@mui/icons-material';
import axios from 'axios';
import { useTranslation } from 'react-i18next';
import { User } from '../types/user';

const roleOptions = [
  { value: 'Admin', label: 'Admin' },
  { value: 'User', label: 'User' }
];

const Users: React.FC = () => {
  const { t } = useTranslation();
  const [users, setUsers] = useState<User[]>([]);
  const [editUser, setEditUser] = useState<User | null>(null);
  const [formData, setFormData] = useState<Partial<User>>({});

  const fetchUsers = async () => {
    try {
      const response = await axios.get('/users');
      setUsers(response.data);
    } catch (error) {
      // handle error
    }
  };

  useEffect(() => {
    fetchUsers();
  }, []);

  const handleEdit = (user: User) => {
    setEditUser(user);
    setFormData({
      login: user.login,
      email: user.email,
      role: user.role,
      passwordHash: ''
    });
  };

  const handleDelete = async (id: number) => {
    if (window.confirm(t('deleteUserConfirm'))) {
      await axios.delete(`/users/${id}`);
      fetchUsers();
    }
  };

  const handleFormChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData((prev: Partial<User>) => ({
      ...prev,
      [name]: value
    }));
  };

  const handleFormSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (editUser) {
      await axios.put(`/users/${editUser.userId}`, formData);
      setEditUser(null);
      fetchUsers();
    }
  };

  return (
    <Container>
      <Box sx={{ my: 4 }}>
        <Typography variant="h4" gutterBottom>
          {t('usersManagement')}
        </Typography>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>{t('login')}</TableCell>
              <TableCell>{t('email')}</TableCell>
              <TableCell>{t('role')}</TableCell>
              <TableCell>{t('actions')}</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {users.map(user => (
              <TableRow key={user.userId}>
                <TableCell>{user.login}</TableCell>
                <TableCell>{user.email}</TableCell>
                <TableCell>{user.role}</TableCell>
                <TableCell>
                  <IconButton onClick={() => handleEdit(user)} color="primary">
                    <EditIcon />
                  </IconButton>
                  <IconButton onClick={() => handleDelete(user.userId)} color="error">
                    <DeleteIcon />
                  </IconButton>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </Box>
      <Dialog open={!!editUser} onClose={() => setEditUser(null)} maxWidth="sm" fullWidth>
        <DialogTitle>{t('editUser')}</DialogTitle>
        <form onSubmit={handleFormSubmit}>
          <DialogContent>
            <TextField
              margin="normal"
              fullWidth
              label={t('login')}
              name="login"
              value={formData.login || ''}
              onChange={handleFormChange}
              required
            />
            <TextField
              margin="normal"
              fullWidth
              label={t('email')}
              name="email"
              value={formData.email || ''}
              onChange={handleFormChange}
              required
            />
            <TextField
              margin="normal"
              fullWidth
              label={t('role')}
              name="role"
              select
              value={formData.role || ''}
              onChange={handleFormChange}
              required
            >
              {roleOptions.map(opt => (
                <MenuItem key={opt.value} value={opt.value}>{opt.label}</MenuItem>
              ))}
            </TextField>
            <TextField
              margin="normal"
              fullWidth
              label={t('newPassword')}
              name="passwordHash"
              type="password"
              value={formData.passwordHash || ''}
              onChange={handleFormChange}
              autoComplete="new-password"
            />
          </DialogContent>
          <DialogActions>
            <Button onClick={() => setEditUser(null)}>{t('cancel')}</Button>
            <Button type="submit" variant="contained">{t('saveChanges')}</Button>
          </DialogActions>
        </form>
      </Dialog>
    </Container>
  );
};

export default Users;
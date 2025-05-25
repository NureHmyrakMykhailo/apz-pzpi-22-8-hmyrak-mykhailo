import React from 'react';
import {
  Dialog, DialogTitle, DialogContent, DialogActions, Button, TextField, FormControlLabel, Checkbox
} from '@mui/material';
import { Person } from '../../types/person';
import { useTranslation } from 'react-i18next';

interface PersonFormProps {
  open: boolean;
  onClose: () => void;
  onSubmit: (personData: Partial<Person>) => void;
  person: Person | null;
}

const PersonForm: React.FC<PersonFormProps> = ({ open, onClose, onSubmit, person }) => {
  const { t } = useTranslation();
  const [formData, setFormData] = React.useState<Partial<Person>>({
    name: '',
    dateOfBirth: '',
    dateOfDeath: '',
    country: '',
    isReal: false,
  });

  React.useEffect(() => {
    if (person) setFormData(person);
    else setFormData({
      name: '',
      dateOfBirth: '',
      dateOfDeath: '',
      country: '',
      isReal: false,
    });
  }, [person]);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value, type, checked } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: type === 'checkbox' ? checked : value,
    }));
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (!formData.name || formData.name.trim() === '') {
      alert(t('nameRequired'));
      return;
    }
    onSubmit(formData);
  };

  return (
    <Dialog open={open} onClose={onClose} maxWidth="sm" fullWidth>
      <DialogTitle>{person ? t('editPerson') : t('addPerson')}</DialogTitle>
      <form onSubmit={handleSubmit}>
        <DialogContent>
          <TextField
            fullWidth
            margin="normal"
            label={t('name')}
            name="name"
            value={formData.name}
            onChange={handleChange}
            required
          />
          <TextField
            fullWidth
            margin="normal"
            label={t('dateOfBirth')}
            name="dateOfBirth"
            type="date"
            value={formData.dateOfBirth ? String(formData.dateOfBirth).slice(0, 10) : ''}
            onChange={handleChange}
            InputLabelProps={{ shrink: true }}
          />
          <TextField
            fullWidth
            margin="normal"
            label={t('dateOfDeath')}
            name="dateOfDeath"
            type="date"
            value={formData.dateOfDeath ? String(formData.dateOfDeath).slice(0, 10) : ''}
            onChange={handleChange}
            InputLabelProps={{ shrink: true }}
          />
          <TextField
            fullWidth
            margin="normal"
            label={t('country')}
            name="country"
            value={formData.country}
            onChange={handleChange}
          />
          <FormControlLabel
            control={
              <Checkbox
                checked={!!formData.isReal}
                onChange={handleChange}
                name="isReal"
              />
            }
            label={t('isReal')}
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={onClose}>{t('cancel')}</Button>
          <Button type="submit" variant="contained">{t('saveChanges')}</Button>
        </DialogActions>
      </form>
    </Dialog>
  );
};

export default PersonForm;
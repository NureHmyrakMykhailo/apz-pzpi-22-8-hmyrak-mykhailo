import React from 'react';
import {
  Dialog, DialogTitle, DialogContent, DialogActions, Button, TextField
} from '@mui/material';
import { Reader } from '../../types/reader';
import { useTranslation } from 'react-i18next';

function normalizeBirthday(birthday: any): string {
  if (!birthday) return '';
  if (/^\d{4}-\d{2}-\d{2}/.test(birthday)) return birthday.slice(0, 10);
  if (/^\d{2}\.\d{2}\.\d{4}$/.test(birthday)) {
    const [d, m, y] = birthday.split('.');
    return `${y}-${m}-${d}`;
  }
  return '';
}

interface ReaderFormProps {
  open: boolean;
  onClose: () => void;
  onSubmit: (readerData: Partial<Reader>) => void;
  reader: Reader | null;
}

const ReaderForm: React.FC<ReaderFormProps> = ({ open, onClose, onSubmit, reader }) => {
  const { t } = useTranslation();
  const [formData, setFormData] = React.useState<Partial<Reader>>({
    name: '',
    class: '',
    studentCard: '',
    birthday: '',
    phone: '',
    email: '',
    address: '',
  });

  React.useEffect(() => {
    if (reader) {
      setFormData({
        ...reader,
        birthday: normalizeBirthday(reader.birthday),
      });
    } else {
      setFormData({
        name: '',
        class: '',
        studentCard: '',
        birthday: '',
        phone: '',
        email: '',
        address: '',
      });
    }
  }, [reader]);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (!formData.name || formData.name.trim() === '') {
      alert(t('nameRequired'));
      return;
    }
    const dataToSend: Partial<Reader> = {};
    Object.keys(formData).forEach((key) => {
      let value = (formData as any)[key];
      if (key === 'birthday' && value && !/^\d{4}-\d{2}-\d{2}$/.test(value)) {
        value = undefined;
      }
      if (value !== undefined && value !== '') {
        (dataToSend as any)[key] = value;
      }
    });
    onSubmit(dataToSend);
  };

  return (
    <Dialog open={open} onClose={onClose} maxWidth="sm" fullWidth>
      <DialogTitle>{reader ? t('editReader') : t('addReader')}</DialogTitle>
      <form onSubmit={handleSubmit}>
        <DialogContent>
          <TextField fullWidth margin="normal" label={t('name') + ' *'} name="name" value={formData.name} onChange={handleChange} required />
          <TextField fullWidth margin="normal" label={t('class')} name="class" value={formData.class} onChange={handleChange} />
          <TextField fullWidth margin="normal" label={t('studentCard')} name="studentCard" value={formData.studentCard} onChange={handleChange} />
          <TextField fullWidth margin="normal" label={t('birthday')} name="birthday" type="date" value={formData.birthday || ''} onChange={handleChange} InputLabelProps={{ shrink: true }} />
          <TextField fullWidth margin="normal" label={t('phone')} name="phone" value={formData.phone} onChange={handleChange} />
          <TextField fullWidth margin="normal" label={t('email')} name="email" value={formData.email} onChange={handleChange} />
          <TextField fullWidth margin="normal" label={t('address')} name="address" value={formData.address} onChange={handleChange} />
        </DialogContent>
        <DialogActions>
          <Button onClick={onClose}>{t('cancel')}</Button>
          <Button type="submit" variant="contained" color="primary">
            {reader ? t('saveChanges') : t('addReader')}
          </Button>
        </DialogActions>
      </form>
    </Dialog>
  );
};

export default ReaderForm;
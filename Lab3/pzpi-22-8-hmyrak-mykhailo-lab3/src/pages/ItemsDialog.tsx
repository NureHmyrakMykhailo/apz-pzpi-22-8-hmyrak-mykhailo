import React, { useEffect, useState } from 'react';
import {
  Dialog, DialogTitle, DialogContent, DialogActions, Button, Table, TableHead, TableRow, TableCell, TableBody, Chip,
  MenuItem, Select, FormControl, InputLabel, Box, TextField
} from '@mui/material';
import axios from 'axios';
import { useTranslation } from 'react-i18next';

interface Item {
  itemId: number;
  bookId: number;
  readerId?: number | null;
  available: boolean;
  description?: string;
}

interface Reader {
  readerId: number;
  name: string;
}

interface ItemsDialogProps {
  open: boolean;
  onClose: () => void;
  bookId: number;
}

const ItemsDialog: React.FC<ItemsDialogProps> = ({ open, onClose, bookId }) => {
  const { t } = useTranslation();
  const [items, setItems] = useState<Item[]>([]);
  const [loading, setLoading] = useState(false);
  const [readers, setReaders] = useState<Reader[]>([]);
  const [giveOutItemId, setGiveOutItemId] = useState<number | null>(null);
  const [selectedReader, setSelectedReader] = useState<number | ''>('');
  const [actionLoading, setActionLoading] = useState(false);

  // Додавання екземплярів
  const [addCount, setAddCount] = useState<number>(1);
  const [addDescription, setAddDescription] = useState<string>('');
  const [addLoading, setAddLoading] = useState(false);

  useEffect(() => {
    if (open) {
      fetchItems();
    }
    // eslint-disable-next-line
  }, [open, bookId]);

  const fetchItems = async () => {
    setLoading(true);
    try {
      const res = await axios.get('/items');
      setItems(res.data.filter((item: Item) => item.bookId === bookId));
    } finally {
      setLoading(false);
    }
  };

  // Завантажити читачів при відкритті діалогу видачі
  const fetchReaders = async () => {
    const res = await axios.get('/readers');
    setReaders(res.data);
  };

  // Видача екземпляра
  const handleGiveOut = async () => {
    if (!giveOutItemId || !selectedReader) return;
    setActionLoading(true);
    try {
      await axios.post('/business/giveout', {
        ItemId: giveOutItemId,
        ReaderId: selectedReader
      });
      setGiveOutItemId(null);
      setSelectedReader('');
      await fetchItems();
    } finally {
      setActionLoading(false);
    }
  };

  // Повернення екземпляра
  const handleReturn = async (itemId: number) => {
    setActionLoading(true);
    try {
      await axios.post('/business/return', { ItemId: itemId });
      await fetchItems();
    } finally {
      setActionLoading(false);
    }
  };

  // Додавання екземплярів
  const handleAddItems = async () => {
    setAddLoading(true);
    try {
      await axios.post('/business/additems', {
        BookId: bookId,
        Description: addDescription,
        Available: true,
        Count: addCount
      });
      setAddCount(1);
      setAddDescription('');
      await fetchItems();
    } finally {
      setAddLoading(false);
    }
  };

  return (
    <Dialog open={open} onClose={onClose} maxWidth="md" fullWidth>
      <DialogTitle>{t('bookItems')}</DialogTitle>
      <DialogContent>
        {/* Форма додавання екземплярів */}
        <Box sx={{ display: 'flex', gap: 2, mb: 2, alignItems: 'center' }}>
          <TextField
            type="number"
            label={t('addItemsCount')}
            value={addCount}
            onChange={e => setAddCount(Number(e.target.value))}
            size="small"
            sx={{ width: 120 }}
            inputProps={{ min: 1 }}
          />
          <TextField
            label={t('addItemsDescription')}
            value={addDescription}
            onChange={e => setAddDescription(e.target.value)}
            size="small"
            sx={{ width: 200 }}
          />
          <Button
            variant="contained"
            onClick={handleAddItems}
            disabled={addLoading || addCount < 1}
          >
            {t('addItems')}
          </Button>
        </Box>
        {loading ? t('loading') : (
          <Table>
            <TableHead>
              <TableRow>
                <TableCell>{t('itemId')}</TableCell>
                <TableCell>{t('available')}</TableCell>
                <TableCell>{t('readerId')}</TableCell>
                <TableCell>{t('description')}</TableCell>
                <TableCell>{t('actions')}</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {items.map(item => (
                <TableRow key={item.itemId}>
                  <TableCell>{item.itemId}</TableCell>
                  <TableCell>
                    <Chip
                      label={item.available ? t('yes') : t('no')}
                      color={item.available ? 'success' : 'error'}
                    />
                  </TableCell>
                  <TableCell>{item.readerId ?? '-'}</TableCell>
                  <TableCell>{item.description ?? '-'}</TableCell>
                  <TableCell>
                    {item.available ? (
                      <Button
                        size="small"
                        variant="outlined"
                        onClick={async () => {
                          setGiveOutItemId(item.itemId);
                          await fetchReaders();
                        }}
                        disabled={actionLoading}
                      >
                        {t('giveOut')}
                      </Button>
                    ) : (
                      <Button
                        size="small"
                        variant="contained"
                        color="secondary"
                        onClick={() => handleReturn(item.itemId)}
                        disabled={actionLoading}
                      >
                        {t('returnBook')}
                      </Button>
                    )}
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        )}
        {/* Діалог вибору читача для видачі */}
        {giveOutItemId && (
          <Box sx={{ mt: 2 }}>
            <FormControl fullWidth>
              <InputLabel>{t('selectReader')}</InputLabel>
              <Select
                value={selectedReader}
                label={t('selectReader')}
                onChange={e => setSelectedReader(Number(e.target.value))}
              >
                {readers.map(reader => (
                  <MenuItem key={reader.readerId} value={reader.readerId}>
                    {reader.name}
                  </MenuItem>
                ))}
              </Select>
            </FormControl>
            <Button
              variant="contained"
              sx={{ mt: 2 }}
              onClick={handleGiveOut}
              disabled={!selectedReader || actionLoading}
            >
              {t('confirmGiveOut')}
            </Button>
            <Button
              sx={{ mt: 2, ml: 2 }}
              onClick={() => {
                setGiveOutItemId(null);
                setSelectedReader('');
              }}
            >
              {t('cancel')}
            </Button>
          </Box>
        )}
      </DialogContent>
      <DialogActions>
        <Button onClick={onClose}>{t('close')}</Button>
      </DialogActions>
    </Dialog>
  );
};

export default ItemsDialog;
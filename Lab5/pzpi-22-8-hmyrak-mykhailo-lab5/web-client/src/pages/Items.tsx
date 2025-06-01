import React, { useState } from 'react';
import {
  Box,
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  TextField,
  Typography,
  IconButton,
  Paper,
  Chip,
  MenuItem,
  Select,
  FormControl,
  InputLabel,
} from '@mui/material';
import {
  Add as AddIcon,
  Edit as EditIcon,
  Delete as DeleteIcon,
  History as HistoryIcon,
} from '@mui/icons-material';
import { DataGrid, GridColDef } from '@mui/x-data-grid';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import axios from 'axios';
import { format } from 'date-fns';

interface Book {
  id: number;
  title: string;
  author: string;
}

interface Reader {
  id: number;
  firstName: string;
  lastName: string;
}

interface Item {
  id: number;
  bookId: number;
  book: Book;
  inventoryNumber: string;
  status: 'available' | 'borrowed' | 'lost' | 'damaged';
  condition: 'new' | 'good' | 'fair' | 'poor';
  location: string;
  acquisitionDate: string;
  lastBorrowedBy?: Reader;
  lastBorrowedDate?: string;
}

interface BorrowHistory {
  id: number;
  itemId: number;
  readerId: number;
  reader: Reader;
  borrowDate: string;
  returnDate?: string;
}

const Items: React.FC = () => {
  const [open, setOpen] = useState(false);
  const [historyOpen, setHistoryOpen] = useState(false);
  const [selectedItem, setSelectedItem] = useState<Item | null>(null);
  const [selectedItemHistory, setSelectedItemHistory] = useState<BorrowHistory[]>([]);
  const [formData, setFormData] = useState<Partial<Item>>({
    bookId: 0,
    inventoryNumber: '',
    status: 'available',
    condition: 'new',
    location: '',
    acquisitionDate: new Date().toISOString().split('T')[0],
  });

  const queryClient = useQueryClient();

  const { data: items, isLoading } = useQuery<Item[]>({
    queryKey: ['items'],
    queryFn: async () => {
      const response = await axios.get('/items');
      return response.data;
    },
  });

  const { data: books } = useQuery<Book[]>({
    queryKey: ['books'],
    queryFn: async () => {
      const response = await axios.get('/books');
      return response.data;
    },
  });

  const createMutation = useMutation({
    mutationFn: (newItem: Partial<Item>) => axios.post('/items', newItem),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['items'] });
      handleClose();
    },
  });

  const updateMutation = useMutation({
    mutationFn: (item: Item) => axios.put(`/items/${item.id}`, item),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['items'] });
      handleClose();
    },
  });

  const deleteMutation = useMutation({
    mutationFn: (id: number) => axios.delete(`/items/${id}`),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['items'] });
    },
  });

  const handleOpen = (item?: Item) => {
    if (item) {
      setSelectedItem(item);
      setFormData(item);
    } else {
      setSelectedItem(null);
      setFormData({
        bookId: 0,
        inventoryNumber: '',
        status: 'available',
        condition: 'new',
        location: '',
        acquisitionDate: new Date().toISOString().split('T')[0],
      });
    }
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
    setSelectedItem(null);
    setFormData({
      bookId: 0,
      inventoryNumber: '',
      status: 'available',
      condition: 'new',
      location: '',
      acquisitionDate: new Date().toISOString().split('T')[0],
    });
  };

  const handleHistoryOpen = async (item: Item) => {
    try {
      const response = await axios.get(`/items/${item.id}/history`);
      setSelectedItemHistory(response.data);
      setHistoryOpen(true);
    } catch (error) {
      console.error('Error fetching item history:', error);
    }
  };

  const handleHistoryClose = () => {
    setHistoryOpen(false);
    setSelectedItemHistory([]);
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (selectedItem) {
      updateMutation.mutate({ ...selectedItem, ...formData });
    } else {
      createMutation.mutate(formData);
    }
  };

  const handleChange = (
    e: React.ChangeEvent<HTMLInputElement | { name?: string; value: unknown }>
  ) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name as string]: value,
    }));
  };

  const getStatusColor = (status: string) => {
    switch (status) {
      case 'available':
        return 'success';
      case 'borrowed':
        return 'warning';
      case 'lost':
        return 'error';
      case 'damaged':
        return 'error';
      default:
        return 'default';
    }
  };

  const getConditionColor = (condition: string) => {
    switch (condition) {
      case 'new':
        return 'success';
      case 'good':
        return 'info';
      case 'fair':
        return 'warning';
      case 'poor':
        return 'error';
      default:
        return 'default';
    }
  };

  const columns: GridColDef[] = [
    {
      field: 'book',
      headerName: 'Book',
      flex: 1,
      valueGetter: (params: any) => params.row.book.title,
    },
    {
      field: 'inventoryNumber',
      headerName: 'Inventory #',
      width: 150,
    },
    {
      field: 'status',
      headerName: 'Status',
      width: 120,
      renderCell: (params) => (
        <Chip
          label={params.value}
          color={getStatusColor(params.value as string)}
          size="small"
        />
      ),
    },
    {
      field: 'condition',
      headerName: 'Condition',
      width: 120,
      renderCell: (params) => (
        <Chip
          label={params.value}
          color={getConditionColor(params.value as string)}
          size="small"
        />
      ),
    },
    {
      field: 'location',
      headerName: 'Location',
      flex: 1,
    },
    {
      field: 'acquisitionDate',
      headerName: 'Acquisition Date',
      flex: 1,
      valueFormatter: (params: any) =>
        format(new Date(params.value as string), 'dd/MM/yyyy'),
    },
    {
      field: 'lastBorrowedBy',
      headerName: 'Last Borrowed By',
      flex: 1,
      valueGetter: (params: any) =>
        params.row.lastBorrowedBy
          ? `${params.row.lastBorrowedBy.firstName} ${params.row.lastBorrowedBy.lastName}`
          : '-',
    },
    {
      field: 'lastBorrowedDate',
      headerName: 'Last Borrowed Date',
      flex: 1,
      valueFormatter: (params: any) =>
        params.value ? format(new Date(params.value as string), 'dd/MM/yyyy') : '-',
    },
    {
      field: 'actions',
      headerName: 'Actions',
      width: 180,
      sortable: false,
      renderCell: (params) => (
        <Box>
          <IconButton
            color="primary"
            size="small"
            onClick={() => handleOpen(params.row)}
          >
            <EditIcon />
          </IconButton>
          <IconButton
            color="info"
            size="small"
            onClick={() => handleHistoryOpen(params.row)}
          >
            <HistoryIcon />
          </IconButton>
          <IconButton
            color="error"
            size="small"
            onClick={() => {
              if (window.confirm('Are you sure you want to delete this item?')) {
                deleteMutation.mutate(params.row.id);
              }
            }}
          >
            <DeleteIcon />
          </IconButton>
        </Box>
      ),
    },
  ];

  const historyColumns: GridColDef[] = [
    {
      field: 'reader',
      headerName: 'Reader',
      flex: 1,
      valueGetter: (params: any) =>
        `${params.row.reader.firstName} ${params.row.reader.lastName}`,
    },
    {
      field: 'borrowDate',
      headerName: 'Borrow Date',
      flex: 1,
      valueFormatter: (params: any) =>
        format(new Date(params.value as string), 'dd/MM/yyyy'),
    },
    {
      field: 'returnDate',
      headerName: 'Return Date',
      flex: 1,
      valueFormatter: (params: any) =>
        params.value ? format(new Date(params.value as string), 'dd/MM/yyyy') : '-',
    },
  ];

  return (
    <Box sx={{ p: 3 }}>
      <Box sx={{ display: 'flex', justifyContent: 'space-between', mb: 3 }}>
        <Typography variant="h4">Items Management</Typography>
        <Button
          variant="contained"
          startIcon={<AddIcon />}
          onClick={() => handleOpen()}
        >
          Add Item
        </Button>
      </Box>

      <Paper sx={{ height: 'calc(100vh - 200px)' }}>
        <DataGrid
          rows={items || []}
          columns={columns}
          loading={isLoading}
          pageSizeOptions={[10, 25, 50]}
          initialState={{
            pagination: { paginationModel: { pageSize: 10 } },
          }}
          disableRowSelectionOnClick
        />
      </Paper>

      <Dialog open={open} onClose={handleClose} maxWidth="sm" fullWidth>
        <form onSubmit={handleSubmit}>
          <DialogTitle>
            {selectedItem ? 'Edit Item' : 'Add New Item'}
          </DialogTitle>
          <DialogContent>
            <Box sx={{ display: 'flex', flexDirection: 'column', gap: 2, pt: 2 }}>
              <FormControl fullWidth required>
                <InputLabel>Book</InputLabel>
                <Select
                  name="bookId"
                  value={formData.bookId}
                  
                  label="Book"
                >
                  {books?.map((book) => (
                    <MenuItem key={book.id} value={book.id}>
                      {book.title} by {book.author}
                    </MenuItem>
                  ))}
                </Select>
              </FormControl>
              <TextField
                name="inventoryNumber"
                label="Inventory Number"
                value={formData.inventoryNumber}
                onChange={handleChange}
                required
                fullWidth
              />
              <FormControl fullWidth required>
                <InputLabel>Status</InputLabel>
                <Select
                  name="status"
                  value={formData.status}
                 
                  label="Status"
                >
                  <MenuItem value="available">Available</MenuItem>
                  <MenuItem value="borrowed">Borrowed</MenuItem>
                  <MenuItem value="lost">Lost</MenuItem>
                  <MenuItem value="damaged">Damaged</MenuItem>
                </Select>
              </FormControl>
              <FormControl fullWidth required>
                <InputLabel>Condition</InputLabel>
                <Select
                  name="condition"
                  value={formData.condition}
                  
                  label="Condition"
                >
                  <MenuItem value="new">New</MenuItem>
                  <MenuItem value="good">Good</MenuItem>
                  <MenuItem value="fair">Fair</MenuItem>
                  <MenuItem value="poor">Poor</MenuItem>
                </Select>
              </FormControl>
              <TextField
                name="location"
                label="Location"
                value={formData.location}
                onChange={handleChange}
                required
                fullWidth
              />
              <TextField
                name="acquisitionDate"
                label="Acquisition Date"
                type="date"
                value={formData.acquisitionDate}
                onChange={handleChange}
                required
                fullWidth
                
              />
            </Box>
          </DialogContent>
          <DialogActions>
            <Button onClick={handleClose}>Cancel</Button>
            <Button
              type="submit"
              variant="contained"
              disabled={createMutation.isPending || updateMutation.isPending}
            >
              {selectedItem ? 'Update' : 'Add'}
            </Button>
          </DialogActions>
        </form>
      </Dialog>

      <Dialog
        open={historyOpen}
        onClose={handleHistoryClose}
        maxWidth="md"
        fullWidth
      >
        <DialogTitle>Borrowing History</DialogTitle>
        <DialogContent>
          <Box sx={{ height: 400, width: '100%', mt: 2 }}>
            <DataGrid
              rows={selectedItemHistory}
              columns={historyColumns}
              pageSizeOptions={[5, 10, 25]}
              initialState={{
                pagination: { paginationModel: { pageSize: 5 } },
              }}
              disableRowSelectionOnClick
            />
          </Box>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleHistoryClose}>Close</Button>
        </DialogActions>
      </Dialog>
    </Box>
  );
};

export default Items; 
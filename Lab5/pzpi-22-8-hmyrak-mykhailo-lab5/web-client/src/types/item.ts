export interface Item {
  itemId: number;
  bookId: number;
  readerId?: number | null;
  available: boolean;
  description?: string;
}
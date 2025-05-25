export interface Book {
  bookId: number;
  title: string;
  isbn: string;
  pages: number;
  publish: string;
  categoryId: number;
  class?: string;
  lang?: string;
  year: number;
  itemsCount: number;
  availableItemsCount: number;
}
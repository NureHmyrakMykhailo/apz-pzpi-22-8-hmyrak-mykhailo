export interface Reader {
  readerId: number;
  name: string;
  class?: string;
  studentCard?: string;
  birthday?: string; // ISO строка, например "2000-01-01"
  phone?: string;
  email?: string;
  address?: string;
}
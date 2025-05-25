export interface User {
  userId: number;
  login: string;
  email: string;
  role: string;
  passwordHash?: string;
}
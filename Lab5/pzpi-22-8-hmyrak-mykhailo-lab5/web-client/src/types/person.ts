export interface Person {
  personId?: number;
  name: string;
  dateOfBirth?: string | null;
  dateOfDeath?: string | null;
  country?: string;
  isReal?: boolean;
}
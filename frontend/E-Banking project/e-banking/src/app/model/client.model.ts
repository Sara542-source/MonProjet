import { Account } from "./account.model";
export interface Client {
  clientId: number;
  login: string;
  password?: string; // Marked as optional since we might not always want to expose this
  fname: string;
  lname: string;
  email: string;
  phone: string;
  cin: string;
  gender: string;
  dateNaissance: Date;
  adresse: string;
  profession: string;
  dateEnregistrment: Date;
  statut: string;
  accounts?: Account[]; // Optional as we might not always load accounts
}
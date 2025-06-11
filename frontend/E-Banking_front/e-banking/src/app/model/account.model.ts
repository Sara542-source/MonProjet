import { Virement } from "./virement.model";
export interface Account {
  id: number;
  balance: number;
  accountNumber: string;
  creationDate: Date;
  accountType: Virement[]; // Optional as we might not always load transactions
}


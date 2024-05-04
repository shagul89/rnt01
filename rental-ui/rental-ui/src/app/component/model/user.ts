import { Address } from "./address";

export class User {
    userId!: string;
    firstName!: string;
    lastName!: string;
    email!: string;
    status!: string;
    password!: string;
    userName!: string;
    userType!: string;
    gender!: string;
    contactnumber!: string;
    roles!: any;
    createdBy!: string;
    createdDate!: Date;
    updatedName!: string;
    updatedDate!: Date;
    userAddress!: Address[];
}
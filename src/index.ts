import { NativeModules } from 'react-native';

export type HyperwalletConfig = {
  transferMethodCountry: string;
  transferMethodCurrency: string;
  bankAccountId: string;
  branchId: string;
  bankAccountPurpose: string;
};

type HyperwalletConstants = {
  BANK_ACCOUNT_PURPOSE_CHECKING: string;
  BANK_ACCOUNT_PURPOSE_SAVINGS: string;
};

export type BankAccount = {
  addressLine1: string;
  bankAccountId: string;
  bankAccountPurpose: string;
  bankName: string;
  branchId: string;
  city: string;
  country: string;
  createdOn: string;
  dateOfBirth: string;
  firstName: string;
  lastName: string;
  postalCode: string;
  profileType: string;
  stateProvince: string;
  status: string;
  token: string;
  transferMethodCountry: string;
  transferMethodCurrency: string;
  type: string;
  userToken: string;
  verificationStatus: string;
};

type HyperwalletType = {
  startup(token: string): void;
  createBankAccount(config: HyperwalletConfig): Promise<BankAccount>;
};

const { Hyperwallet } = NativeModules;

export const BankAccountPurpose: HyperwalletConstants = Hyperwallet.getConstants();
export default Hyperwallet as HyperwalletType;

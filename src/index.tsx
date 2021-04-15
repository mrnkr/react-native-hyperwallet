import { NativeModules } from 'react-native';

type HyperwalletType = {
  multiply(a: number, b: number): Promise<number>;
};

const { Hyperwallet } = NativeModules;

export default Hyperwallet as HyperwalletType;

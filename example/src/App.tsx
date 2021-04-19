import * as React from 'react';

import { StyleSheet, View, Text } from 'react-native';
import Hyperwallet, { BankAccountPurpose } from 'react-native-hyperwallet';

export default function App() {
  const [result] = React.useState<number | undefined>();

  React.useEffect(() => {
    (async () => {
      try {
        var myHeaders = new Headers();
        myHeaders.append('Accept', 'application/json');
        myHeaders.append('Content-Type', 'application/json');
        myHeaders.append(
          'Authorization',
          'Basic cmVzdGFwaXVzZXJAOTA0MjYwOTE2MTg6UGl0dXNhczEyMzQ='
        );

        var requestOptions = {
          method: 'POST',
          headers: myHeaders,
        };

        const { value } = await fetch(
          'https://api.sandbox.hyperwallet.com/rest/v3/users/usr-8f7bde67-6408-453f-8267-d2d2bcf87cf1/authentication-token',
          requestOptions
        ).then((res) => res.json());

        console.log(value);

        Hyperwallet.startup(value);
        const res = await Hyperwallet.createBankAccount({
          transferMethodCountry: 'US',
          transferMethodCurrency: 'USD',
          bankAccountId: '8017110254',
          branchId: '211179539',
          bankAccountPurpose: BankAccountPurpose.BANK_ACCOUNT_PURPOSE_CHECKING,
        });
        console.log('eureka', res);
      } catch (err) {
        console.log('error', err);
      }
    })();
  }, []);

  return (
    <View style={styles.container}>
      <Text>Result: {result}</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  box: {
    width: 60,
    height: 60,
    marginVertical: 20,
  },
});

package com.reactnativehyperwallet

import android.annotation.SuppressLint
import android.os.Handler
import com.facebook.react.bridge.*
import com.hyperwallet.android.Hyperwallet
import com.hyperwallet.android.exception.HyperwalletException
import com.hyperwallet.android.listener.HyperwalletListener
import com.hyperwallet.android.model.transfermethod.BankAccount


class HyperwalletModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {

  override fun getName(): String {
      return "Hyperwallet"
  }

  override fun getConstants(): MutableMap<String, Any> {
    val ret = HashMap<String, Any>()

    ret["BANK_ACCOUNT_PURPOSE_CHECKING"] = BankAccount.Purpose.CHECKING
    ret["BANK_ACCOUNT_PURPOSE_SAVING"] = BankAccount.Purpose.SAVINGS

    return ret
  }

  @ReactMethod
  fun startup(token: String) {
    Hyperwallet.getInstance {
      it.onSuccess(token)
    }
  }

  // Example method
  // See https://reactnative.dev/docs/native-modules-android
  @ReactMethod
  fun createBankAccount(config: ReadableMap, promise: Promise) {

    val bankAccount = BankAccount.Builder()
      .transferMethodCountry(config.getString("transferMethodCountry")
        ?: error("Please set transferMethodCountry"))
      .transferMethodCurrency(config.getString("transferMethodCurrency")
        ?: error("Please set transferMethodCurrency"))
      .bankAccountId(config.getString("bankAccountId") ?: error("Please set bankAccountId"))
      .branchId(config.getString("branchId") ?: error("Please set branchId"))
      .bankAccountPurpose(config.getString("bankAccountPurpose") ?: error("Please set bankAccountPurpose"))
      .build()

    Hyperwallet.getDefault().createBankAccount(bankAccount, object : HyperwalletListener<BankAccount?> {
      @SuppressLint("WrongConstant")
      override fun onSuccess(result: BankAccount?) {
        // onSuccess: response (PageList<BankAccount>) will contain a PageList of BankAccount or null if not exists
        var ret = Arguments.createMap()

        ret.putString("branchId", result?.getField("branchId"))
        ret.putString("lastName", result?.getField("lastName"))
        ret.putString("country", result?.getField("country"))
        ret.putString("transferMethodCountry", result?.getField("transferMethodCountry"))
        ret.putString("transferMethodCurrency", result?.getField("transferMethodCurrency"))
        ret.putString("verificationStatus", result?.getField("verificationStatus"))
        ret.putString("city", result?.getField("city"))
        ret.putString("postalCode", result?.getField("postalCode"))
        ret.putString("bankAccountPurpose", result?.getField("bankAccountPurpose"))
        ret.putString("stateProvince", result?.getField("stateProvince"))
        ret.putString("bankName", result?.getField("bankName"))
        ret.putString("dateOfBirth", result?.getField("dateOfBirth"))
        ret.putString("type", result?.getField("type"))
        ret.putString("createdOn", result?.getField("createdOn"))
        ret.putString("token", result?.getField("token"))
        ret.putString("userToken", result?.getField("userToken"))
        ret.putString("firstName", result?.getField("firstName"))
        ret.putString("profileType", result?.getField("profileType"))
        ret.putString("bankAccountId", result?.getField("bankAccountId"))
        ret.putString("addressLine1", result?.getField("addressLine1"))
        ret.putString("status", result?.getField("status"))

        promise.resolve(ret)
      }

      override fun onFailure(exception: HyperwalletException) {
        // onFailure: error (HyperwalletErrorType) will contain HyperwalletErrors containing information about what caused the failure
        promise.reject(Exception(exception.errors.errors.joinToString { it.message }))
      }

      override fun getHandler(): Handler? {
        return null
      }
    })

  }

}

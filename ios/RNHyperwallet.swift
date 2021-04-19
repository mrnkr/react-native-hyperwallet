import HyperwalletSDK

@objc(RNHyperwallet)
public class RNHyperwallet: RCTEventEmitter, HyperwalletAuthenticationTokenProvider {
    
    var _token = ""
    
    override public static func requiresMainQueueSetup() -> Bool {
        return true;
    }
    
    @objc(constantsToExport)
    override public func constantsToExport() -> [AnyHashable: Any] {
        return [
            "BANK_ACCOUNT_PURPOSE_CHECKING": "checking",
            "BANK_ACCOUNT_PURPOSE_SAVINGS": "savings"
        ]
    }
    
    @objc(supportedEvents)
    override public func supportedEvents() -> [String] {
        return []
    }

    @objc(startup:)
    func startup(token: String) -> Void {
        _token = token;
        Hyperwallet.setup(self)
    }
    
    public func retrieveAuthenticationToken(
            completionHandler: @escaping HyperwalletAuthenticationTokenProvider.CompletionHandler) {
        completionHandler(_token, nil)
    }
    
    @objc(createBankAccount:withResolver:withRejecter:)
    func createBankAccount(config: [AnyHashable: Any], resolve: @escaping RCTPromiseResolveBlock, reject: @escaping RCTPromiseRejectBlock) -> Void {
        let bankAccount = HyperwalletBankAccount.Builder(transferMethodCountry: config["transferMethodCountry"] as! String,
                                                         transferMethodCurrency: config["transferMethodCurrency"] as! String,
                                                         transferMethodProfileType: "INDIVIDUAL", transferMethodType: "BANK_ACCOUNT")
            .bankAccountId(config["bankAccountId"] as! String)
            .branchId(config["branchId"] as! String)
            .bankAccountPurpose(config["bankAccountPurpose"] as! String)
        .build()

        Hyperwallet.shared.createBankAccount(account: bankAccount, completion: { (result, error) in
            // In case of failure, error (HyperwalletErrorType) will contain HyperwalletErrors containing information about what caused the failure of account creation
            guard error == nil else {
                reject(error?.getHyperwalletErrors()?.originalError.debugDescription, error?.getHyperwalletErrors()?.errorList?.description, error)
                return
            }

            // In case of successful creation, response (HyperwalletBankAccount in this case) payload will contain information about the account created
            resolve([
                "addressLine1": result?.addressLine1,
                "bankAccountId": result?.bankAccountId,
                "bankAccountPurpose": result?.bankAccountPurpose,
                "bankName": result?.bankName,
                "branchId": result?.branchId,
                "city": result?.city,
                "country": result?.country,
                "createdOn": result?.createdOn,
                "dateOfBirth": result?.dateOfBirth,
                "firstName": result?.firstName,
                "lastName": result?.lastName,
                "postalCode": result?.postalCode,
                "profileType": result?.profileType,
                "stateProvince": result?.stateProvince,
                "status": result?.status,
                "token": result?.token,
                "transferMethodCountry": result?.transferMethodCountry,
                "transferMethodCurrency": result?.transferMethodCurrency,
                "type": result?.type,
            ])
        })
    }
}

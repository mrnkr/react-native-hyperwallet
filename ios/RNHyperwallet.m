#import <React/RCTBridgeModule.h>

@interface RCT_EXTERN_REMAP_MODULE(Hyperwallet, RNHyperwallet, NSObject)

RCT_EXTERN_METHOD(startup:(NSString *)token)
RCT_EXTERN_METHOD(createBankAccount:(NSDictionary *)config
                    withResolver:(RCTPromiseResolveBlock)resolve
                    withRejecter:(RCTPromiseRejectBlock)reject)

@end

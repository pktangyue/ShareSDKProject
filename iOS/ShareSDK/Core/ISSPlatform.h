//
//  ISSPlatform.h
//  Unity-iPhone
//
//  Created by tangyue on 15/1/14.
//
//

#ifndef ShareSDK_ISSPlatform_h
#define ShareSDK_ISSPlatform_h

#import "ShareSDKTypeDef.h"
#import "ShareSDKEventHandlerDef.h"

@protocol ISSPlatform <NSObject>

- (ShareType)type;

- (id<ISSPlatform>)registerApp:(NSDictionary *)info;

- (void)share:(NSMutableDictionary *)content
       result:(SSPublishContentEventHandler)result;

- (BOOL)handleOpenURL:(NSURL *)url
             delegate:(id)delegate;

- (BOOL)handleOpenURL:(NSURL *)url
    sourceApplication:(NSString *)sourceApplication
           annotation:(id)annotation
             delegate:(id)delegate;

@end

#endif

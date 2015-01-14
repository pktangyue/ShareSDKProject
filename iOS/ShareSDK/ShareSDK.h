//
//  ShareSDK.h
//  Unity-iPhone
//
//  Created by tangyue on 15/1/14.
//
//

#import <Foundation/Foundation.h>
#include "ShareSDKTypeDef.h"
#include "ShareSDKEventHandlerDef.h"

@interface ShareSDK : NSObject

+ (void)registerApp:(NSString *)appKey;

+ (NSMutableDictionary *)jsonObjectWithString:(NSString *)string;

+ (NSString *)jsonStringWithObject:(NSMutableDictionary *)object;

+ (void)connectPlatformWithType:(ShareType)type
                        appInfo:(NSDictionary *)appInfo;

+ (void)authWithType:(ShareType)type
              result:(SSAuthEventHandler)result;

+ (void)shareContent:(NSMutableDictionary *)content
                type:(ShareType)type
              result:(SSPublishContentEventHandler)result;

@end

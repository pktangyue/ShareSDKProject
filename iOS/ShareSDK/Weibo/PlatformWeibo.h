//
//  PlatformWeibo.h
//  ShareSDK
//
//  Created by tangyue on 15/1/15.
//
//

#import <Foundation/Foundation.h>
#import "ISSPlatform.h"
#import "WeiboSDK.h"

@interface PlatformWeibo : NSObject<ISSPlatform, WeiboSDKDelegate>
{
    SSPublishContentEventHandler _result;
    NSString * redirect_uri;
    NSString* wbtoken;
    NSString* wbCurrentUserID;
}

@property (nonatomic, copy) SSPublishContentEventHandler result;
@property (strong, nonatomic) NSString * redirect_uri;
@property (strong, nonatomic) NSString *wbtoken;
@property (strong, nonatomic) NSString *wbCurrentUserID;

@end

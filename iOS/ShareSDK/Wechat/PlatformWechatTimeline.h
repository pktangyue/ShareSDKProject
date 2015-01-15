//
//  PlatformWechat.h
//  ShareSDK
//
//  Created by tangyue on 15/1/15.
//
//

#import <Foundation/Foundation.h>
#import "ISSPlatform.h"
#import "WXApi.h"

@interface PlatformWechatTimeline : NSObject<ISSPlatform, WXApiDelegate>
{
    SSPublishContentEventHandler _result;
}

@property (nonatomic, copy) SSPublishContentEventHandler result;

@end

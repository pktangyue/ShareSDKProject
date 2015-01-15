//
//  PlatformWeibo.m
//  ShareSDK
//
//  Created by tangyue on 15/1/15.
//
//

#import <Foundation/Foundation.h>
#import "PlatformWeibo.h"

@implementation PlatformWeibo

- (ShareType)type
{
    return ShareTypeSinaWeibo;
}

- (id<ISSPlatform>)registerApp:(NSDictionary *)info
{
    
    return self;
}

- (void)share:(NSMutableDictionary *)content
       result:(SSPublishContentEventHandler)result
{
    _result = result;
    
    
}

- (BOOL)handleOpenURL:(NSURL *)url
             delegate:(id)delegate
{
    return YES;
}

- (BOOL)handleOpenURL:(NSURL *)url
    sourceApplication:(NSString *)sourceApplication
           annotation:(id)annotation
             delegate:(id)delegate
{
    return YES;
}

@end


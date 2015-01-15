//
//  PlatFormContainer.m
//  Unity-iPhone
//
//  Created by tangyue on 15/1/15.
//
//

#import "PlatformContainer.h"
#import "PlatformWechatTimeline.h"
#import "PlatformWeibo.h"

@implementation PlatformContainer

static id shareInstance = nil;

+ (void)initialize {
    if (self == [PlatformContainer class]) {
        shareInstance = [[self alloc] init];
    }
}

+ (id)shareInstance
{
    return shareInstance;
}

-(id)init
{
    self = [super init];
    platforms = [[NSMutableArray alloc] init];
    return self;
}

-(void)dealloc
{
    [platforms release];
    [super dealloc];
}

-(id<ISSPlatform>)getPlatform:(ShareType)type
{
    for (id<ISSPlatform> platform in platforms) {
        if ([platform type] == type)
        {
            return platform;
        }
    }
    
    id<ISSPlatform> platform;
    switch (type)
    {
        case ShareTypeSinaWeibo:
            platform = [[[PlatformWeibo alloc] init] autorelease];
            break;
        case ShareTypeWeixiTimeline:
            platform = [[[PlatformWechatTimeline alloc] init] autorelease];
            break;
        default:
            break;
    }
    
    [platforms addObject:platform];
    
    return platform;
}

- (BOOL)handleOpenURL:(NSURL *)url
{
    for (id<ISSPlatform> platform in platforms) {
        BOOL ret = [platform handleOpenURL:url
                                  delegate:platform];
        if (!ret)
        {
            return ret;
        }
    }
    return YES;
}

- (BOOL)handleOpenURL:(NSURL *)url
    sourceApplication:(NSString *)sourceApplication
           annotation:(id)annotation
{
    for (id<ISSPlatform> platform in platforms) {
        BOOL ret = [platform handleOpenURL:url
                         sourceApplication:sourceApplication
                                annotation:annotation
                                  delegate:platform];
        if (!ret)
        {
            return ret;
        }
    }
    return YES;
}

@end
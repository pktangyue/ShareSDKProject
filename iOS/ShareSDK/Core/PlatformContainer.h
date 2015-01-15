//
//  PlatformContainer.h
//  Unity-iPhone
//
//  Created by tangyue on 15/1/15.
//
//

#import "ISSPlatform.h"

@interface PlatformContainer : NSObject
{
    @private
    NSMutableArray * platforms;
}

+(id)shareInstance;

-(id<ISSPlatform>)getPlatform:(ShareType)type;

- (BOOL)handleOpenURL:(NSURL *)url;

- (BOOL)handleOpenURL:(NSURL *)url
    sourceApplication:(NSString *)sourceApplication
           annotation:(id)annotation;

@end

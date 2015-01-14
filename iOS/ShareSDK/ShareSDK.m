//
//  ShareSDK.m
//  Unity-iPhone
//
//  Created by tangyue on 15/1/14.
//
//

#import "ShareSDK.h"

@implementation ShareSDK

+(void)registerApp:(NSString *)appKey
{
    
}

+ (NSMutableDictionary *)jsonObjectWithString:(NSString *)string
{
    NSError *jsonError;
    NSData* data = [string dataUsingEncoding:NSUTF8StringEncoding];
    NSDictionary *jsonResponse = [NSJSONSerialization JSONObjectWithData:data
                                                                 options:NSJSONReadingMutableContainers
                                                                   error:&jsonError];
    NSMutableDictionary *dict = [NSMutableDictionary dictionaryWithDictionary:jsonResponse];
    
    return dict;
}

+ (NSString *)jsonStringWithObject:(NSMutableDictionary *)object
{
    NSError *jsonError;

    NSData* data = [NSJSONSerialization dataWithJSONObject:object
                                                   options:NSJSONReadingMutableContainers
                                                     error:&jsonError];
    NSString* string = [[NSString alloc] initWithData:data
                                             encoding:NSUTF8StringEncoding];
    return string;
}

+ (void)connectPlatformWithType:(ShareType)type
                        appInfo:(NSDictionary *)appInfo
{
    
}

+ (void)authWithType:(ShareType)type
              result:(SSAuthEventHandler)result
{
    
}

+ (void)shareContent:(NSMutableDictionary *)content
                type:(ShareType)type
              result:(SSPublishContentEventHandler)result
{
    
}

@end

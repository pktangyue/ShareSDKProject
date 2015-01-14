//
//  ShareSDKUnity3DBridge.m
//  Unity-iPhone
//
//  Created by 冯 鸿杰 on 14-2-14.
//
//

#import "ShareSDKUnity3DBridge.h"

#include "ShareSDK.h"

#define __SHARESDK_WECHAT__

#ifdef __SHARESDK_WECHAT__
#import "WXApi.h"
#endif

#if defined (__cplusplus)
extern "C" {
#endif
    
    /**
     *	@brief	初始化ShareSDK
     *
     *	@param 	appKey 	应用Key
     */
    extern void __iosShareSDKOpen(void *appKey);

    /**
     *	@brief	初始化社交平台
     *
     *	@param 	platType 	平台类型
     *	@param 	contigInfo 	配置信息
     */
    extern void __iosShareSDKSetPlatformConfig(int platType, void *configInfo);

    /**
     *	@brief	用户授权
     *
     *	@param 	platType 	平台类型
     *  @param  observer    观察回调对象名称
     */
    extern void __iosShareSDKAuthorize (int platType, void *observer);

    /**
     *	@brief	取消用户授权
     *
     *	@param 	platType 	平台类型
     */
    extern void __iosShareSDKCancelAuthorize (int platType);

    /**
     *	@brief	判断用户是否授权
     *
     *	@param 	platType 	平台类型
     *
     *	@return	YES 表示已经授权，NO 表示尚未授权
     */
    extern bool __iosShareSDKHasAuthorized (int platType);
    
    /**
     *	@brief	获取用户信息
     *
     *	@param 	platType 	平台类型
     *  @param  observer    观察回调对象名称
     */
    extern void __iosShareSDKGetUserInfo (int platType, void *observer);
    
    /**
     *	@brief	分享内容
     *
     *	@param 	platType 	平台类型
     *	@param 	content 	分享内容
     *  @param  observer    观察回调对象名称
     */
    extern void __iosShareSDKShare (int platType, void *content, void *observer);
    
#if defined (__cplusplus)
}
#endif

#if defined (__cplusplus)
extern "C" {
#endif
    void __iosShareSDKOpen(void *appKey)
    {
        NSString *appKeyStr = [NSString stringWithCString:appKey encoding:NSUTF8StringEncoding];
        [ShareSDK registerApp:appKeyStr];
        
#ifdef __SHARESDK_WECHAT__
        //[ShareSDK importWeChatClass:[WXApi class]];
#endif
    }
    
    void __iosShareSDKSetPlatformConfig(int platType, void *configInfo)
    {
        NSString *configInfoStr = nil;
        if (configInfo)
        {
            configInfoStr = [NSString stringWithCString:configInfo encoding:NSUTF8StringEncoding];
        }
        NSMutableDictionary *configInfoDict = [NSMutableDictionary dictionaryWithDictionary:[ShareSDK jsonObjectWithString:configInfoStr]];
        
        [ShareSDK connectPlatformWithType:platType
                                  appInfo:configInfoDict];
    }
    
    void __iosShareSDKAuthorize (int platType, void *observer)
    {
        NSString *observerStr = nil;
        if (observer)
        {
            observerStr = [NSString stringWithCString:observer encoding:NSUTF8StringEncoding];
        }
        
        [ShareSDK authWithType:platType
                        result:^(SSAuthState state, id<ICMErrorInfo> error) {
                            
                            NSMutableDictionary *resultDict = [NSMutableDictionary dictionary];
                            [resultDict setObject:[NSNumber numberWithInteger:1] forKey:@"action"];
                            [resultDict setObject:[NSNumber numberWithInteger:state] forKey:@"state"];
                            [resultDict setObject:[NSNumber numberWithInteger:platType] forKey:@"type"];
                            
                            if (state == SSResponseStateFail && error)
                            {
                                NSMutableDictionary *errorDict = [NSMutableDictionary dictionary];
                                [errorDict setObject:[NSNumber numberWithInteger:[error errorCode]] forKey:@"error_code"];
                                if ([error errorDescription])
                                {
                                    [errorDict setObject:[error errorDescription] forKey:@"error_msg"];
                                }
                                [resultDict setObject:errorDict forKey:@"error"];
                            }
                            
                            NSString *resultStr = [ShareSDK jsonStringWithObject:resultDict];
                            UnitySendMessage([observerStr UTF8String], "_callback", [resultStr UTF8String]);
                        }];
    }
    
    void __iosShareSDKCancelAuthorize (int platType)
    {
        //[ShareSDK cancelAuthWithType:platType];
    }
    
    bool __iosShareSDKHasAuthorized (int platType)
    {
        //return [ShareSDK hasAuthorizedWithType:platType];
        return true;
    }
    
    void __iosShareSDKGetUserInfo (int platType, void *observer)
    {
        NSString *observerStr = nil;
        if (observer)
        {
            observerStr = [NSString stringWithCString:observer encoding:NSUTF8StringEncoding];
        }
        
//        [ShareSDK getUserInfoWithType:platType
//                          authOptions:nil
//                               result:^(BOOL result, id<ISSPlatformUser> userInfo, id<ICMErrorInfo> error) {
//                                   
//                                   NSMutableDictionary *resultDict = [NSMutableDictionary dictionary];
//                                   [resultDict setObject:[NSNumber numberWithInteger:2] forKey:@"action"];
//                                   [resultDict setObject:[NSNumber numberWithInteger:result ? SSResponseStateSuccess : SSResponseStateFail] forKey:@"state"];
//                                   [resultDict setObject:[NSNumber numberWithInteger:platType] forKey:@"type"];
//                                   
//                                   if (!result && error)
//                                   {
//                                       NSMutableDictionary *errorDict = [NSMutableDictionary dictionary];
//                                       [errorDict setObject:[NSNumber numberWithInteger:[error errorCode]] forKey:@"error_code"];
//                                       if ([error errorDescription])
//                                       {
//                                           [errorDict setObject:[error errorDescription] forKey:@"error_msg"];
//                                       }
//                                       [resultDict setObject:errorDict forKey:@"error"];
//                                   }
//                                   else if ([userInfo sourceData])
//                                   {
//                                       [resultDict setObject:[userInfo sourceData] forKey:@"user"];
//                                   }
//                                   
//                                   NSString *resultStr = [ShareSDK jsonStringWithObject:resultDict];
//                                   UnitySendMessage([observerStr UTF8String], "_callback", [resultStr UTF8String]);
//                                   
//                               }];
    }
    
    void __iosShareSDKShare (int platType, void *content, void *observer)
    {
        NSString *observerStr = nil;
        
        NSString *contentStr = nil;
        
        observerStr = [NSString stringWithCString:observer encoding:NSUTF8StringEncoding];
        
        if (content)
        {
            contentStr = [NSString stringWithCString:content encoding:NSUTF8StringEncoding];
        }
        
        [ShareSDK shareContent:[ShareSDK jsonObjectWithString:contentStr]
                          type:platType
                        result:^(ShareType type, SSResponseState state, id<ISSPlatformShareInfo> statusInfo, id<ICMErrorInfo> error, BOOL end) {
                            
                            NSMutableDictionary *resultDict = [NSMutableDictionary dictionary];
                            [resultDict setObject:[NSNumber numberWithInteger:3] forKey:@"action"];
                            [resultDict setObject:[NSNumber numberWithInteger:state] forKey:@"state"];
                            [resultDict setObject:[NSNumber numberWithInteger:platType] forKey:@"type"];
                            [resultDict setObject:[NSNumber numberWithBool:end] forKey:@"end"];
                            
                            if (state == SSResponseStateFail && error)
                            {
                                NSMutableDictionary *errorDict = [NSMutableDictionary dictionary];
                                [errorDict setObject:[NSNumber numberWithInteger:[error errorCode]] forKey:@"error_code"];
                                if ([error errorDescription])
                                {
                                    [errorDict setObject:[error errorDescription] forKey:@"error_msg"];
                                }
                                [resultDict setObject:errorDict forKey:@"error"];
                            }
                            else if ([statusInfo sourceData])
                            {
                                {
                                    [resultDict setObject:[statusInfo sourceData]
                                                   forKey:@"share_info"];
                                }
                            }
                            
                            NSString *resultStr = [ShareSDK jsonStringWithObject:resultDict];
                            UnitySendMessage([observerStr UTF8String], "_callback", [resultStr UTF8String]);
                        }];
    }
    
#if defined (__cplusplus)
}
#endif

@implementation ShareSDKUnity3DBridge

@end

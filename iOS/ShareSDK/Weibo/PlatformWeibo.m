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

@synthesize result = _result;
@synthesize redirect_uri;
@synthesize wbtoken;
@synthesize wbCurrentUserID;

- (ShareType)type
{
    return ShareTypeSinaWeibo;
}

- (id<ISSPlatform>)registerApp:(NSDictionary *)info
{
    [WeiboSDK enableDebugMode:YES];
    NSString* app_key = [info objectForKey:@"app_key"];
    [WeiboSDK registerApp:app_key];
    
    self.redirect_uri = [info objectForKey:@"redirect_uri"];
    
    return self;
}

- (void)share:(NSMutableDictionary *)content
       result:(SSPublishContentEventHandler)result
{
    self.result = result;
    
    WBMessageObject *message = [WBMessageObject message];
    
    NSString* text = [content objectForKey:@"text"];
    if (text)
    {
        message.text = NSLocalizedString(text, nil);
    }
    
    WBWebpageObject *webpage = [WBWebpageObject object];
    webpage.objectID = [[NSUUID UUID] UUIDString];
    webpage.title = NSLocalizedString([content objectForKey:@"title"], nil);
    webpage.description = NSLocalizedString([content objectForKey:@"description"], nil);
    //webpage.thumbnailData = [NSData dataWithContentsOfFile:[[NSBundle mainBundle] pathForResource:@"image_2" ofType:@"jpg"]];
    webpage.webpageUrl = [content objectForKey:@"url"];
    message.mediaObject = webpage;
    
    WBAuthorizeRequest *authRequest = [WBAuthorizeRequest request];
    authRequest.redirectURI = self.redirect_uri;
    authRequest.scope = @"all";
    
    WBSendMessageToWeiboRequest *request =
    [WBSendMessageToWeiboRequest requestWithMessage:message
                                           authInfo:authRequest
                                       access_token:self.wbtoken];
    
    //    request.shouldOpenWeiboAppInstallPageIfNotInstalled = NO;
    [WeiboSDK sendRequest:request];

}

- (BOOL)handleOpenURL:(NSURL *)url
             delegate:(id)delegate
{
    return [WeiboSDK handleOpenURL:url
                          delegate:self];
}

- (BOOL)handleOpenURL:(NSURL *)url
    sourceApplication:(NSString *)sourceApplication
           annotation:(id)annotation
             delegate:(id)delegate
{
    return [WeiboSDK handleOpenURL:url
                          delegate:self];
}

- (void)didReceiveWeiboRequest:(WBBaseRequest *)request
{
    
}

- (void)didReceiveWeiboResponse:(WBBaseResponse *)response
{
    if ([response isKindOfClass:WBSendMessageToWeiboResponse.class])
    {
        WBSendMessageToWeiboResponse* sendMessageToWeiboResponse = (WBSendMessageToWeiboResponse*)response;

        NSString* accessToken = [sendMessageToWeiboResponse.authResponse accessToken];
        if (accessToken)
        {
            self.wbtoken = accessToken;
        }
        NSString* userID = [sendMessageToWeiboResponse.authResponse userID];
        if (userID) {
            self.wbCurrentUserID = userID;
        }
        
        
        NSMutableDictionary * ret = [[NSMutableDictionary alloc]init];
        NSString* error_code = [response.userInfo objectForKey:@"code"];
        if (error_code)
        {
            [ret setObject:error_code forKeyedSubscript:@"error_code"];
        }
        NSString* error_msg = [response.userInfo objectForKey:@"msg"];
        if (error_msg)
        {
            [ret setObject:error_msg forKeyedSubscript:@"error_msg"];
        }
        
        SSResponseState state;
        
        switch (response.statusCode)
        {
            case WeiboSDKResponseStatusCodeSuccess:
                state = SSResponseStateSuccess;
                break;
            case WeiboSDKResponseStatusCodeUserCancel:
                state = SSResponseStateCancel;
                break;
            default:
                state = SSResponseStateFail;
                break;
        }
        
        self.result([self type], state, ret);
    }
}

@end


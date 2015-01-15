//
//  PlatformWechat.m
//  ShareSDK
//
//  Created by tangyue on 15/1/15.
//
//

#import <Foundation/Foundation.h>
#import "PlatformWechatTimeline.h"
#import "WXApiObject.h"

@implementation PlatformWechatTimeline

@synthesize result = _result;

- (ShareType)type
{
    return ShareTypeWeixiTimeline;
}

- (id<ISSPlatform>)registerApp:(NSDictionary *)info
{
    [WXApi registerApp:[info objectForKey:@"app_id"]];
    
    return self;
}

- (void)share:(NSMutableDictionary *)content
       result:(SSPublishContentEventHandler)result
{
    self.result = result;
    
    WXMediaMessage *message = [WXMediaMessage message];
    message.title = [content objectForKey:@"title"];
    message.description = [content objectForKey:@"description"];
    //[message setThumbImage:[UIImage imageNamed:@"res2.png"]];
    
    WXWebpageObject *ext = [WXWebpageObject object];
    ext.webpageUrl = [content objectForKey:@"url"];
    
    message.mediaObject = ext;
    
    SendMessageToWXReq* req = [[[SendMessageToWXReq alloc] init]autorelease];
    req.bText = NO;
    req.message = message;
    req.scene = WXSceneTimeline;
    [WXApi sendReq:req];
    
}

- (BOOL)handleOpenURL:(NSURL *)url
             delegate:(id)delegate
{
    return [WXApi handleOpenURL:url
                       delegate:self];
}

- (BOOL)handleOpenURL:(NSURL *)url
    sourceApplication:(NSString *)sourceApplication
           annotation:(id)annotation
             delegate:(id)delegate
{
    return [WXApi handleOpenURL:url
                       delegate:self];
}

-(void) onReq:(BaseReq*)req
{
    
}

-(void) onResp:(BaseResp*)resp
{
    if([resp isKindOfClass:[SendMessageToWXResp class]])
    {
        NSMutableDictionary * ret = [[NSMutableDictionary alloc]init];
        [ret setObject:[NSNumber numberWithInt:resp.errCode] forKey:@"error_code"];
        if (resp.errStr)
        {
            [ret setObject:resp.errStr forKey:@"error_msg"];
        }
        
        SSResponseState state;
        switch (resp.errCode)
        {
            case WXSuccess:
                state = SSResponseStateSuccess;
                break;
            case WXErrCodeUserCancel:
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
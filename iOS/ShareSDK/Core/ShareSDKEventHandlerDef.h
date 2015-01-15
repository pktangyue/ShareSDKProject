//
//  ShareSDKEventHandlerDef.h
//  Unity-iPhone
//
//  Created by tangyue on 15/1/14.
//
//

#ifndef ShareSDK_ShareSDKEventHandlerDef_h
#define ShareSDK_ShareSDKEventHandlerDef_h

///#begin zh-cn
/**
 *	@brief	授权事件处理器
 *
 *  @param  state  授权状态
 *  @param  error   授权失败的错误信息,仅当state为SSAuthStateFail时有效
 */
///#end
///#begin en
/**
 *	@brief	Authorize event handler.
 *
 *  @param  state  Authorized state.
 *  @param  error   Error information, When the state is SSAuthStateFail only valid
 */
///#end

typedef void(^SSAuthEventHandler) (SSAuthState state, NSMutableDictionary* error);

///#begin zh-cn
/**
 *	@brief	获取用户信息事件处理器
 *
 *  @param  result  回复标识，YES：获取成功，NO：获取失败
 *  @param  userInfo     用户信息
 *  @param  error   获取失败的错误信息
 */
///#end
///#begin en
/**
 *	@brief	Get user information event handler.
 *
 *  @param  result  Result identity, YES: to succeed, NO: Failed to get
 *  @param  userInfo     User object.
 *  @param  error   Error information.
 */
///#end

//typedef void(^SSGetUserInfoEventHandler) (BOOL result, id<ISSPlatformUser> userInfo, id<ICMErrorInfo> error);

///#begin zh-cn
/**
 *	@brief	分享内容事件处理器
 *
 *  @param  type    平台类型
 *  @param  state  发布内容状态
 *  @param  statusInfo  分享信息
 *  @param  error   分享内容失败的错误信息
 *  @param  end     分享完毕标志，对于单个平台分享此值为YES，对于多个分享平台此值在最后一个平台分享完毕后为YES。
 */
///#end
///#begin en
/**
 *	@brief	Share content event handler
 *
 *  @param  type    Platform type
 *  @param  state  Publish state.
 *  @param  statusInfo  Share information object.
 *  @param  error   Error handler.
 *  @param  end     Share finished flag, for a single platform to share this value YES, for multiple platforms to share this value after completion of the final share a platform for YES.
 */
///#end

typedef void(^SSPublishContentEventHandler) (ShareType type, SSResponseState state, NSMutableDictionary* error);

#endif

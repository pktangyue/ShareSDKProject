//
//  ShareSDKTypeDef.h
//  Unity-iPhone
//
//  Created by tangyue on 15/1/14.
//
//

#ifndef ShareSDK_ShareSDKTypeDef_h
#define ShareSDK_ShareSDKTypeDef_h

///#begin zh-cn
/**
 *	@brief	分享类型
 */
///#end
///#begin en
/**
 *	@brief	Platform type.
 */
///#end
typedef enum
{
    ShareTypeSinaWeibo = 1,         /**< 新浪微博 */
    ShareTypeWeixiSession = 22,     /**< 微信好友 */
    ShareTypeWeixiTimeline = 23,    /**< 微信朋友圈 */
    ShareTypeAny = 99               /**< 任意平台 */
}
ShareType;

///#begin zh-cn
/**
 *	@brief	授权状态
 */
///#end
///#begin en
/**
 *	@brief	Authorized state.
 */
///#end
typedef enum
{
    SSAuthStateBegan = 0, /**< 开始 */
    SSAuthStateSuccess = 1, /**< 成功 */
    SSAuthStateFail = 2, /**< 失败 */
    SSAuthStateCancel = 3 /**< 取消 */
}
SSAuthState;

///#begin zh-cn
/**
 *	@brief	响应状态
 */
///#end
///#begin en
/**
 *	@brief	Response state.
 */
///#end
typedef enum
{
    SSResponseStateBegan = 0, /**< 开始 */
    SSResponseStateSuccess = 1, /**< 成功 */
    SSResponseStateFail = 2, /**< 失败 */
    SSResponseStateCancel = 3 /**< 取消 */
}
SSResponseState;

#endif

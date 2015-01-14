//
//  ISSPlatformShareInfo.h
//  Unity-iPhone
//
//  Created by tangyue on 15/1/14.
//
//

#ifndef Unity_iPhone_ISSPlatformShareInfo_h
#define Unity_iPhone_ISSPlatformShareInfo_h

///#begin zh-cn
/**
 *	@brief	分享信息协议
 */
///#end
///#begin en
/**
 *	@brief	Share info protocol
 */
///#end
@protocol ISSPlatformShareInfo <NSObject>

@required

///#begin zh-cn
/**
 *	@brief	获取分享信息的原始数据,与各平台定义结构一致
 *
 *	@return	原始数据
 */
///#end
///#begin en
/**
 *	@brief	Get sharing raw data, define the structure consistent with the platform
 *
 *	@return	Raw data dicationary.
 */
///#end
- (NSDictionary *)sourceData;

///#begin zh-cn
/**
 *	@brief	获取分享信息标识
 *
 *	@return	分享信息标识
 */
///#end
///#begin en
/**
 *	@brief	Get share info id.
 *
 *	@return	Share info id.
 */
///#end
- (NSString *)sid;

///#begin zh-cn
/**
 *	@brief	获取分享内容
 *
 *	@return	分享内容
 */
///#end
///#begin en
/**
 *	@brief	Get share content.
 *
 *	@return	Content string.
 */
///#end
- (NSString *)text;

///#begin zh-cn
/**
 *	@brief	获取分享的链接列表
 *
 *	@return	链接列表数组
 */
///#end
///#begin en
/**
 *	@brief	Get a list of url.
 *
 *	@return	urls list array.
 */
///#end
- (NSArray *)urls;

///#begin zh-cn
/**
 *	@brief	获取分享的图片列表
 *
 *	@return	图片列表数组
 */
///#end
///#begin en
/**
 *	@brief	Get a list of image
 *
 *	@return	Images list array.
 */
///#end
- (NSArray *)imgs;

///#begin zh-cn
/**
 *	@brief	获取扩展信息
 *
 *	@return	扩展信息
 */
///#end
///#begin en
/**
 *	@brief	Get extended Information.
 *
 *	@return	Extended Information
 */
///#end
- (NSDictionary *)extInfo;

@end


#endif

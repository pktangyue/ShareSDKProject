package com.gumichina.sharesdk.framework.sinaweibo;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.gumichina.sharesdk.framework.Platform;
import com.gumichina.sharesdk.framework.PlatformType;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.IWeiboHandler.Response;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.utils.Utility;

public class PlatformSinaWeibo extends Platform
{

	private IWeiboShareAPI api;

	public String appKey;

	/**
	 * <p>
	 * 注：关于授权回调页对移动客户端应用来说对用户是不可见的，所以定义为何种形式都将不影响， 但是没有定义将无法使用 SDK 认证登录。
	 * 建议使用默认回调页：https://api.weibo.com/oauth2/default.html
	 * </p>
	 */
	public String redirectUrl;

	/**
	 * Scope 是 OAuth2.0 授权机制中 authorize 接口的一个参数。通过 Scope，平台将开放更多的微博
	 * 核心功能给开发者，同时也加强用户隐私保护，提升了用户体验，用户在新 OAuth2.0 授权页中有权利 选择赋予应用的功能。
	 * 
	 * 我们通过新浪微博开放平台-->管理中心-->我的应用-->接口管理处，能看到我们目前已有哪些接口的 使用权限，高级权限需要进行申请。
	 * 
	 * 目前 Scope 支持传入多个 Scope 权限，用逗号分隔。
	 * 
	 * 有关哪些 OpenAPI 需要权限申请，请查看：http://open.weibo.com/wiki/%E5%BE%AE%E5%8D%9AAPI
	 * 关于 Scope 概念及注意事项，请查看：http://open.weibo.com/wiki/Scope
	 */
	public static final String scope = "email,direct_messages_read,direct_messages_write,"
			+ "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
			+ "follow_app_official_microblog," + "invitation_write";

	public PlatformSinaWeibo(Context paramContext)
	{
		super(paramContext);
		// TODO Auto-generated constructor stub
		platformid = PlatformType.SinaWeibo;
	}

	@Override
	public void setPlatformDevInfo(HashMap<String, Object> devInfo)
	{
		// TODO Auto-generated method stub
		appKey = devInfo.get("app_key").toString();
		redirectUrl = devInfo.get("redirect_uri").toString();

		api = WeiboShareSDK.createWeiboAPI(context, appKey);

		api.registerApp();
	}

	@Override
	public void removeAccount(boolean removeCookie)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isValid()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void authorize()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void showUser(String param)
	{
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("unchecked")
	@Override
	public void share(HashMap<String, Object> hash)
	{
		// TODO Auto-generated method stub

		WeiboMultiMessage weiboMessage = new WeiboMultiMessage();

		String text = hash.get("text").toString();
		if (!text.isEmpty())
		{
			TextObject textObject = new TextObject();
			textObject.text = text;
			weiboMessage.textObject = textObject;
		}

		byte[] bytes = convertBytes((ArrayList<Integer>) hash.get("imageData"));
		if (bytes.length > 0)
		{
			ImageObject imageObject = new ImageObject();
			imageObject.imageData = bytes;
			weiboMessage.imageObject = imageObject;
		}

		WebpageObject webpageObject = new WebpageObject();
		webpageObject.identify = Utility.generateGUID();
		webpageObject.title = hash.get("title").toString();
		webpageObject.description = hash.get("description").toString();
		webpageObject.actionUrl = hash.get("url").toString();
		weiboMessage.mediaObject = webpageObject;

		SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
		request.transaction = String.valueOf(System.currentTimeMillis());
		request.multiMessage = weiboMessage;

		AuthInfo authInfo = new AuthInfo(context, appKey, redirectUrl, scope);
		Oauth2AccessToken accessToken = AccessTokenKeeper.readAccessToken(context);
		String token = "";
		if (accessToken != null)
		{
			token = accessToken.getToken();
		}
		api.sendRequest((Activity) context, request, authInfo, token, new WeiboAuthListener()
		{

			@Override
			public void onWeiboException(WeiboException arg0)
			{
			}

			@Override
			public void onComplete(Bundle bundle)
			{
				// TODO Auto-generated method stub
				Oauth2AccessToken newToken = Oauth2AccessToken.parseAccessToken(bundle);
				AccessTokenKeeper.writeAccessToken(context, newToken);
			}

			@Override
			public void onCancel()
			{
			}
		});
	}

	@Override
	public void handleIntent(Intent intent, Object object)
	{
		// TODO Auto-generated method stub
		api.handleWeiboResponse(intent, (Response) object);
	}
}

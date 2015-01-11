package com.gumichina.sharesdk.framework.sinaweibo;

import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.gumichina.sharesdk.framework.Platform;
import com.gumichina.sharesdk.framework.PlatformType;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.IWeiboHandler.Response;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;

public class PlatformSinaWeibo extends Platform
{

	private IWeiboShareAPI api;

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
		String appKey = devInfo.get("app_key").toString();

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

	@Override
	public void share(HashMap<String, Object> hash)
	{
		// TODO Auto-generated method stub

		TextObject textObject = new TextObject();
		textObject.text = hash.get("text").toString();

		WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
		weiboMessage.textObject = textObject;

		SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
		request.transaction = String.valueOf(System.currentTimeMillis());
		request.multiMessage = weiboMessage;

		api.sendRequest((Activity) context, request);
	}

	@Override
	public void handleIntent(Intent intent, Object object)
	{
		// TODO Auto-generated method stub
		api.handleWeiboResponse(intent, (Response) object);
	}
}

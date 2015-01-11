package com.gumichina.sharesdk.framework.wechat;

import java.util.HashMap;

import android.content.Context;
import android.content.Intent;

import com.gumichina.sharesdk.framework.Platform;
import com.gumichina.sharesdk.framework.PlatformType;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.mm.sdk.openapi.WXMediaMessage;
import com.tencent.mm.sdk.openapi.WXTextObject;

public class PlatformWechatTimeline extends Platform
{

	private IWXAPI api;

	public PlatformWechatTimeline(Context paramContext)
	{
		super(paramContext);
		// TODO Auto-generated constructor stub
		platformid = PlatformType.WechatTimeline;
	}

	@Override
	public void setPlatformDevInfo(HashMap<String, Object> devInfo)
	{
		// TODO Auto-generated method stub
		String app_id = devInfo.get("app_id").toString();

		api = WXAPIFactory.createWXAPI(context, app_id, true);

		api.registerApp(app_id);
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
		String text = hash.get("text").toString();

		// TODO Auto-generated method stub
		WXTextObject textObj = new WXTextObject();
		textObj.text = text;

		WXMediaMessage msg = new WXMediaMessage();
		msg.mediaObject = textObj;
		msg.description = text;

		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = String.valueOf(System.currentTimeMillis());
		req.message = msg;
		req.scene = SendMessageToWX.Req.WXSceneTimeline;

		api.sendReq(req);

	}

	@Override
	public void handleIntent(Intent intent, Object object)
	{
		// TODO Auto-generated method stub
		api.handleIntent(intent, (IWXAPIEventHandler) object);
	}

}

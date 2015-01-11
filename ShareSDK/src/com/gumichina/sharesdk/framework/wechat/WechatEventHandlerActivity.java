package com.gumichina.sharesdk.framework.wechat;

import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.gumichina.sharesdk.framework.Platform;
import com.gumichina.sharesdk.framework.PlatformType;
import com.gumichina.sharesdk.framework.ShareSDK;
import com.tencent.mm.sdk.openapi.BaseReq;
import com.tencent.mm.sdk.openapi.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;

public class WechatEventHandlerActivity extends Activity implements IWXAPIEventHandler
{

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		Platform platform = ShareSDK.getPlatform(PlatformType.WechatTimeline);
		((PlatformWechatTimeline) platform).getIWXAPI().handleIntent(getIntent(), this);
	}

	@Override
	protected void onNewIntent(Intent intent)
	{
		super.onNewIntent(intent);

		setIntent(intent);

		Platform platform = ShareSDK.getPlatform(PlatformType.WechatTimeline);
		((PlatformWechatTimeline) platform).getIWXAPI().handleIntent(getIntent(), this);
	}

	// 微信发送请求到第三方应用时，会回调到该方法
	@Override
	public void onReq(BaseReq req)
	{
		// TODO Auto-generated method stub
		Log.e("IWXAPIEventHandler", req.toString());
		finish();
	}

	// 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
	@Override
	public void onResp(BaseResp resp)
	{
		// TODO Auto-generated method stub
		Platform platform = ShareSDK.getPlatform(PlatformType.WechatTimeline);

		switch (resp.errCode)
		{
		case BaseResp.ErrCode.ERR_OK:

			HashMap<String, Object> res = new HashMap<String, Object>();
			res.put("errStr", resp.errStr);

			platform.getPlatformActionListener().onComplete(platform, Platform.ACTION_SHARE, res);

			break;
		case BaseResp.ErrCode.ERR_USER_CANCEL:

			platform.getPlatformActionListener().onCancel(platform, Platform.ACTION_SHARE);

			break;
		case BaseResp.ErrCode.ERR_AUTH_DENIED:

			platform.getPlatformActionListener().onError(platform, Platform.ACTION_SHARE, new Throwable(resp.errStr));

			break;
		default:
			platform.getPlatformActionListener().onError(platform, Platform.ACTION_SHARE, new Throwable(resp.errStr));

			break;
		}

		finish();
	}
}

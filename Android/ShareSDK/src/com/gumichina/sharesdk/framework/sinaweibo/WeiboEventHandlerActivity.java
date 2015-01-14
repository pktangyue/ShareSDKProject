package com.gumichina.sharesdk.framework.sinaweibo;

import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.gumichina.sharesdk.framework.Platform;
import com.gumichina.sharesdk.framework.PlatformType;
import com.gumichina.sharesdk.framework.ShareSDK;
import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.constant.WBConstants;

public class WeiboEventHandlerActivity extends Activity implements IWeiboHandler.Response
{

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		ShareSDK.getPlatform(PlatformType.SinaWeibo).handleIntent(getIntent(), this);
	}

	protected void onNewIntent(Intent intent)
	{
		super.onNewIntent(intent);

		ShareSDK.getPlatform(PlatformType.SinaWeibo).handleIntent(intent, this);// 当前应用唤起微博分享后,返回当前应用
	}

	@Override
	public void onResponse(BaseResponse baseResp)
	{
		// TODO Auto-generated method stub

		Platform platform = ShareSDK.getPlatform(PlatformType.SinaWeibo);

		switch (baseResp.errCode)
		{
		case WBConstants.ErrorCode.ERR_OK:

			HashMap<String, Object> res = new HashMap<String, Object>();
			res.put("errStr", baseResp.errMsg);

			platform.getPlatformActionListener().onComplete(platform, Platform.ACTION_SHARE, res);

			break;
		case WBConstants.ErrorCode.ERR_CANCEL:

			platform.getPlatformActionListener().onCancel(platform, Platform.ACTION_SHARE);

			break;
		case WBConstants.ErrorCode.ERR_FAIL:

			platform.getPlatformActionListener().onError(platform, Platform.ACTION_SHARE,
					new Throwable(baseResp.errMsg));

			break;
		}

		finish();
	}
}

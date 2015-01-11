package cn.sharesdk.unity3d;

import android.app.Application;

import com.gumichina.sharesdk.unity3d.ShareSDKUtils;

public class ShareSDKApplication extends Application
{

	@Override
	public void onCreate()
	{
		super.onCreate();
		ShareSDKUtils.prepare(this.getApplicationContext());
	}

}

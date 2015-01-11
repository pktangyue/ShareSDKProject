package com.gumichina.sharesdk.framework;

import android.content.Context;

public class ShareSDK
{

	private static PlatformContainer platformContainer = null;

	public static void initSDK(Context paramContext)
	{
		platformContainer = new PlatformContainer(paramContext);
	}

	public static void stopSDK(Context paramContext)
	{

	}

	private static void checkShareSDK()
	{
		if (platformContainer == null)
		{
			String str = "Please call ShareSDK.initSDK(Context) before any action.";
			throw new NullPointerException(str);
		}
	}

	public static Platform getPlatform(int platformid)
	{
		checkShareSDK();
		return platformContainer.getPlatform(platformid);
	}
}

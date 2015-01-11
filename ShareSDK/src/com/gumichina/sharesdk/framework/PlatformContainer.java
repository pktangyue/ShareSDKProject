package com.gumichina.sharesdk.framework;

import java.util.ArrayList;

import com.gumichina.sharesdk.framework.sinaweibo.PlatformSinaWeibo;
import com.gumichina.sharesdk.framework.wechat.PlatformWechatTimeline;

import android.content.Context;

public class PlatformContainer
{

	private ArrayList<Platform> platforms = new ArrayList<Platform>();
	private Context context = null;

	public PlatformContainer(Context context)
	{
		this.context = context;
	}

	public Platform getPlatform(int platformid)
	{
		for (Platform platform : platforms)
		{
			if (platform.platformid == platformid)
				return platform;
		}

		Platform platform = null;
		// 如果内存中没有
		switch (platformid)
		{
		case PlatformType.SinaWeibo:
			platform = new PlatformSinaWeibo(context);
			break;
		case PlatformType.WechatTimeline:
			platform = new PlatformWechatTimeline(context);
			break;
		default:
			return null;
		}

		platforms.add(platform);

		return platform;
	}
}

package com.gumichina.sharesdk.framework;

import java.util.HashMap;

import android.content.Context;
import android.content.Intent;

public abstract class Platform
{
	protected int platformid;
	protected Context context;
	protected PlatformActionListener actionListener;

	public static final int ACTION_AUTHORIZING = 1;
	public static final int ACTION_GETTING_FRIEND_LIST = 2;
	public static final int ACTION_SENDING_DIRECT_MESSAGE = 5;
	public static final int ACTION_FOLLOWING_USER = 6;
	public static final int ACTION_TIMELINE = 7;
	public static final int ACTION_USER_INFOR = 8;
	public static final int ACTION_SHARE = 9;

	public static final int SHARE_TEXT = 1;
	public static final int SHARE_IMAGE = 2;
	public static final int SHARE_WEBPAGE = 4;
	public static final int SHARE_MUSIC = 5;
	public static final int SHARE_VIDEO = 6;
	public static final int SHARE_APPS = 7;
	public static final int SHARE_FILE = 8;
	public static final int SHARE_EMOJI = 9;

	public Platform(Context paramContext)
	{
		context = paramContext;
	}

	public int getPlatformId()
	{
		return platformid;
	}

	public PlatformActionListener getPlatformActionListener()
	{
		return actionListener;
	}

	public abstract void setPlatformDevInfo(HashMap<String, Object> devInfo);

	public abstract void removeAccount(boolean removeCookie);

	public abstract boolean isValid();

	public void setPlatformActionListener(PlatformActionListener listener)
	{
		this.actionListener = listener;
	}

	public abstract void authorize();

	public abstract void showUser(String param);

	public abstract void share(HashMap<String, Object> hash);

	public abstract void handleIntent(Intent intent, Object object);
}

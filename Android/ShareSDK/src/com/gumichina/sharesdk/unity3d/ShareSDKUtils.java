package com.gumichina.sharesdk.unity3d;

import java.util.ArrayList;
import java.util.HashMap;

import m.framework.utils.Hashon;
import android.content.Context;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;

import com.gumichina.sharesdk.framework.Platform;
import com.gumichina.sharesdk.framework.PlatformActionListener;
import com.gumichina.sharesdk.framework.ShareSDK;
import com.unity3d.player.UnityPlayer;

public class ShareSDKUtils
{
	private static boolean DEBUG = true;

	private static final int MSG_INITSDK = 1;
	private static final int MSG_STOPSDK = 2;
	private static final int MSG_AUTHORIZE = 3;
	private static final int MSG_SHOW_USER = 4;
	private static final int MSG_SHARE = 5;
	private static final int MSG_ONEKEY_SAHRE = 6;
	private static final int MSG_SETCONFIG = 7;

	private static Context context;
	private static Callback uiCallback;
	private static PlatformActionListener paListener;
	private static String gameObject;
	private static String callback;
	private static Handler handler;

	public static void prepare(Context appContext)
	{
		if (DEBUG)
		{
			System.out.println("ShareSDKUtils.prepare");
		}

		if (context == null)
		{
			context = appContext != null ? appContext : UnityPlayer.currentActivity;
		}
		if (uiCallback == null)
		{
			uiCallback = new Callback()
			{
				public boolean handleMessage(Message msg)
				{
					return ShareSDKUtils.handleMessage(msg);
				}
			};
		}
		if (handler == null)
		{
			handler = new Handler(Looper.getMainLooper(), uiCallback);
		}
		if (paListener == null)
		{
			paListener = new PlatformActionListener()
			{
				public void onError(Platform platform, int action, Throwable t)
				{
					String resp = javaActionResToCS(platform, action, t);
					UnityPlayer.UnitySendMessage(gameObject, callback, resp);
				}

				public void onComplete(Platform platform, int action, HashMap<String, Object> res)
				{
					String resp = javaActionResToCS(platform, action, res);
					UnityPlayer.UnitySendMessage(gameObject, callback, resp);
				}

				public void onCancel(Platform platform, int action)
				{
					String resp = javaActionResToCS(platform, action);
					UnityPlayer.UnitySendMessage(gameObject, callback, resp);
				}
			};
		}
	}

	public static void setGameObject(String gameObject, String callback)
	{
		if (DEBUG)
		{
			System.out.println("ShareSDKUtils.setGameObject");
		}
		ShareSDKUtils.gameObject = gameObject;
		ShareSDKUtils.callback = callback;
	}

	public static void initSDK(String appKey)
	{
		if (DEBUG)
		{
			System.out.println("ShareSDKUtils.initSDK");
		}

		Message msg = new Message();
		msg.what = MSG_INITSDK;
		msg.obj = appKey;
		handler.sendMessage(msg);
	}

	public static void stopSDK()
	{
		if (DEBUG)
		{
			System.out.println("ShareSDKUtils.stopSDK");
		}
		handler.sendEmptyMessage(MSG_STOPSDK);
	}

	public static void setPlatformConfig(int platform, String configs)
	{
		if (DEBUG)
		{
			System.out.println("ShareSDKUtils.setPlatformConfig");
		}

		Message msg = new Message();
		msg.what = MSG_SETCONFIG;
		msg.arg1 = platform;
		msg.obj = configs;
		handler.sendMessage(msg);
	}

	public static void authorize(int platform)
	{
		if (DEBUG)
		{
			System.out.println("ShareSDKUtils.authorize");
		}
		Message msg = new Message();
		msg.what = MSG_AUTHORIZE;
		msg.arg1 = platform;
		handler.sendMessage(msg);
	}

	public static void removeAccount(int platform)
	{
		if (DEBUG)
		{
			System.out.println("ShareSDKUtils.removeAccount");
		}
		Platform plat = ShareSDK.getPlatform(platform);
		plat.removeAccount(true);
	}

	public static boolean isValid(int platform)
	{
		if (DEBUG)
		{
			System.out.println("ShareSDKUtils.isValid");
		}
		Platform plat = ShareSDK.getPlatform(platform);
		return plat.isValid();
	}

	public static void showUser(int platform)
	{
		if (DEBUG)
		{
			System.out.println("ShareSDKUtils.showUser");
		}
		Message msg = new Message();
		msg.what = MSG_SHOW_USER;
		msg.arg1 = platform;
		handler.sendMessage(msg);
	}

	public static void share(int platform, String content)
	{
		if (DEBUG)
		{
			System.out.println("ShareSDKUtils.share");
		}
		Message msg = new Message();
		msg.what = MSG_SHARE;
		msg.arg1 = platform;
		msg.obj = content;
		handler.sendMessage(msg);
	}

	public static void onekeyShare(int platform, String content)
	{
		if (DEBUG)
		{
			System.out.println("ShareSDKUtils.OnekeyShare");
		}
		Message msg = new Message();
		msg.what = MSG_ONEKEY_SAHRE;
		msg.arg1 = platform;
		msg.obj = content;
		handler.sendMessage(msg);
	}

	public static boolean handleMessage(Message msg)
	{
		switch (msg.what)
		{
		case MSG_INITSDK:
		{
			ShareSDK.initSDK(context);
		}
			break;
		case MSG_SETCONFIG:
		{

			Hashon hashon = new Hashon();
			int platform = msg.arg1;
			String configs = msg.obj.toString();

			HashMap<String, Object> devInfo = hashon.fromJson(configs);
			Platform p = ShareSDK.getPlatform(platform);
			p.setPlatformDevInfo(devInfo);
		}
			break;
		case MSG_STOPSDK:
		{
			ShareSDK.stopSDK(context);
		}
			break;
		case MSG_AUTHORIZE:
		{
			int platform = msg.arg1;
			Platform plat = ShareSDK.getPlatform(platform);
			plat.setPlatformActionListener(paListener);
			plat.authorize();
		}
			break;
		case MSG_SHOW_USER:
		{
			int platform = msg.arg1;
			Platform plat = ShareSDK.getPlatform(platform);
			plat.setPlatformActionListener(paListener);
			plat.showUser(null);
		}
			break;
		case MSG_SHARE:
		{
			int platform = msg.arg1;
			String content = (String) msg.obj;
			Platform plat = ShareSDK.getPlatform(platform);
			plat.setPlatformActionListener(paListener);
			try
			{
				Hashon hashon = new Hashon();
				plat.share(hashon.fromJson(content));
			} catch (Throwable t)
			{
				t.printStackTrace();
			}
		}
			break;

		}
		return false;
	}

	// ==================== java tools =====================

	private static String javaActionResToCS(Platform platform, int action, Throwable t)
	{
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("platform", platform.getPlatformId());
		map.put("action", action);
		map.put("status", 2); // Success = 1, Fail = 2, Cancel = 3
		map.put("res", throwableToMap(t));
		Hashon hashon = new Hashon();
		return hashon.fromHashMap(map);
	}

	private static String javaActionResToCS(Platform platform, int action, HashMap<String, Object> res)
	{
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("platform", platform.getPlatformId());
		map.put("action", action);
		map.put("status", 1); // Success = 1, Fail = 2, Cancel = 3
		map.put("res", res);
		Hashon hashon = new Hashon();
		return hashon.fromHashMap(map);
	}

	private static String javaActionResToCS(Platform platform, int action)
	{
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("platform", platform.getPlatformId());
		map.put("action", action);
		map.put("status", 3); // Success = 1, Fail = 2, Cancel = 3
		Hashon hashon = new Hashon();
		return hashon.fromHashMap(map);
	}

	private static HashMap<String, Object> throwableToMap(Throwable t)
	{
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("msg", t.getMessage());
		ArrayList<HashMap<String, Object>> traces = new ArrayList<HashMap<String, Object>>();
		for (StackTraceElement trace : t.getStackTrace())
		{
			HashMap<String, Object> element = new HashMap<String, Object>();
			element.put("cls", trace.getClassName());
			element.put("method", trace.getMethodName());
			element.put("file", trace.getFileName());
			element.put("line", trace.getLineNumber());
			traces.add(element);
		}
		map.put("stack", traces);
		Throwable cause = t.getCause();
		if (cause != null)
		{
			map.put("cause", throwableToMap(cause));
		}
		return map;
	}

}

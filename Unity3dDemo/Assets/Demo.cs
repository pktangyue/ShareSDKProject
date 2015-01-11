using UnityEngine;
using System;
using System.Collections;
using System.Collections.Generic;
using cn.sharesdk.unity3d;

public class Demo : MonoBehaviour {
	
	public GUISkin demoSkin;

	// Use this for initialization
	void Start ()
	{	
		print("start");
		Debug.Log("start");
		ShareSDK.setCallbackObjectName("Main Camera");
		ShareSDK.open ("5244809aeb1c");
		
		//Sina Weibo
		Hashtable sinaWeiboConf = new Hashtable();
		sinaWeiboConf.Add("app_key", "3560580095");
		sinaWeiboConf.Add("app_secret", "cfefec2e6c292d6b08e05916cef25a10");
		sinaWeiboConf.Add("redirect_uri", "http://dev01.wcat.gumichina.com/");
		ShareSDK.setPlatformConfig (PlatformType.SinaWeibo, sinaWeiboConf);

		//WeChat
		Hashtable wcConf = new Hashtable();
		wcConf.Add ("app_id", "wx0a33fd8a4968be1d");
		ShareSDK.setPlatformConfig (PlatformType.WeChatTimeline, wcConf);
    	
	}
	
	// Update is called once per frame
	void Update () 
	{
		if (Input.GetKeyDown(KeyCode.Escape)) {
			ShareSDK.close();
			Application.Quit();
		}
	}
	
	void OnGUI ()
	{
		GUI.skin = demoSkin;
		
		float scale = 2.0f;
		
		if (Application.platform == RuntimePlatform.IPhonePlayer)
		{
			scale = Screen.width / 320;
		}
		
		float btnWidth = 200 * scale;
		float btnHeight = 45 * scale;
		float btnTop = 20 * scale;
		GUI.skin.button.fontSize = Convert.ToInt32(16 * scale);
		
		btnTop += btnHeight + 20 * scale;
		if (GUI.Button(new Rect((Screen.width - btnWidth) / 2, btnTop, btnWidth, btnHeight), "Share Weibo"))
		{
			Hashtable content = new Hashtable();
			content["text"] = "this is a test string.";
			content["image"] = "http://img.baidu.com/img/image/zhenrenmeinv0207.jpg";
			content["title"] = "test title";
			content["description"] = "test description";
			content["url"] = "http://sharesdk.cn";
			content["type"] = Convert.ToString((int)ContentType.News);
			content["siteUrl"] = "http://sharesdk.cn";
			content["site"] = "ShareSDK";
			content["musicUrl"] = "http://mp3.mwap8.com/destdir/Music/2009/20090601/ZuiXuanMinZuFeng20090601119.mp3";
			
			ShareResultEvent evt = new ShareResultEvent(ShareResultHandler);
			ShareSDK.shareContent (PlatformType.SinaWeibo, content, evt);
		}

		btnTop += btnHeight + 20 * scale;
		if (GUI.Button(new Rect((Screen.width - btnWidth) / 2, btnTop, btnWidth, btnHeight), "Share Wechat"))
		{
			Hashtable content = new Hashtable();
			content["text"] = "this is a test string.";
			content["image"] = "http://img.baidu.com/img/image/zhenrenmeinv0207.jpg";
			content["title"] = "test title";
			content["description"] = "test description";
			content["url"] = "http://sharesdk.cn";
			content["type"] = Convert.ToString((int)ContentType.News);
			content["siteUrl"] = "http://sharesdk.cn";
			content["site"] = "ShareSDK";
			content["musicUrl"] = "http://mp3.mwap8.com/destdir/Music/2009/20090601/ZuiXuanMinZuFeng20090601119.mp3";
			
			ShareResultEvent evt = new ShareResultEvent(ShareResultHandler);
			ShareSDK.shareContent (PlatformType.WeChatTimeline, content, evt);
		}

	}
	
	void AuthResultHandler(ResponseState state, PlatformType type, Hashtable error)
	{
		if (state == ResponseState.Success)
		{
			print ("success !");
		}
		else if (state == ResponseState.Fail)
		{
			print ("fail! error code = " + error["error_code"] + "; error msg = " + error["error_msg"]);
		}
		else if (state == ResponseState.Cancel) 
		{
			print ("cancel !");
		}
	}
	
	void GetUserInfoResultHandler (ResponseState state, PlatformType type, Hashtable user, Hashtable error)
	{
		if (state == ResponseState.Success)
		{
			print ("get user result :");
			print (MiniJSON.jsonEncode(user));
		}
		else if (state == ResponseState.Fail)
		{
			print ("fail! error code = " + error["error_code"] + "; error msg = " + error["error_msg"]);
		}
		else if (state == ResponseState.Cancel) 
		{
			print ("cancel !");
		}
	}
	
	void ShareResultHandler (ResponseState state, PlatformType type, Hashtable shareInfo, Hashtable error, bool end)
	{
		Debug.Log("ShareResultHandler");
		if (state == ResponseState.Success)
		{
			print ("share result :");
			print (MiniJSON.jsonEncode(shareInfo));
		}
		else if (state == ResponseState.Fail)
		{
			print ("fail! error code = " + error["error_code"] + "; error msg = " + error["error_msg"]);
		}
		else if (state == ResponseState.Cancel) 
		{
			print ("cancel !");
		}
	}

	void GetFriendsResultHandler (ResponseState state, PlatformType type, ArrayList friends, Hashtable error)
	{
		if (state == ResponseState.Success)
		{
			print ("share result :");
			print (MiniJSON.jsonEncode(friends));
		}
		else if (state == ResponseState.Fail)
		{
			print ("fail! error code = " + error["error_code"] + "; error msg = " + error["error_msg"]);
		}
		else if (state == ResponseState.Cancel) 
		{
			print ("cancel !");
		}
	}

	void GetTokenResultHandler (ResponseState state, PlatformType type, Hashtable credential, Hashtable error)
	{
		if (state == ResponseState.Success)
		{
			print ("share result :");
			print (MiniJSON.jsonEncode(credential));
		}
		else if (state == ResponseState.Fail)
		{
			print ("fail! error code = " + error["error_code"] + "; error msg = " + error["error_msg"]);
		}
		else if (state == ResponseState.Cancel) 
		{
			print ("cancel !");
		}
	}
}

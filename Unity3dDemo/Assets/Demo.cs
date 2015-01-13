using UnityEngine;
using System;
using System.Collections;
using System.Collections.Generic;
using cn.sharesdk.unity3d;
using System.Linq;

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
			StartCoroutine(CaptureCamera2());
		}

		btnTop += btnHeight + 20 * scale;
		if (GUI.Button(new Rect((Screen.width - btnWidth) / 2, btnTop, btnWidth, btnHeight), "Share Wechat"))
		{
			StartCoroutine(CaptureCamera());
		}

	}

	IEnumerator CaptureCamera()   
	{  
		Rect rect = new Rect();
		rect.width = Screen.width;
		rect.height = Screen.height;
		yield return new WaitForEndOfFrame();
		// 创建一个RenderTexture对象  
		RenderTexture rt = new RenderTexture((int)rect.width, (int)rect.height, 100);  
		// 临时设置相关相机的targetTexture为rt, 并手动渲染相关相机  
		camera.targetTexture = rt;  
		camera.Render();  
		//ps: --- 如果这样加上第二个相机，可以实现只截图某几个指定的相机一起看到的图像。  
		//ps: camera2.targetTexture = rt;  
		//ps: camera2.Render();  
		//ps: -------------------------------------------------------------------  
		
		// 激活这个rt, 并从中中读取像素。  
		RenderTexture.active = rt;  
		Texture2D screenShot = new Texture2D((int)rect.width, (int)rect.height, TextureFormat.RGB24,false);  
		screenShot.ReadPixels(rect, 0, 0);// 注：这个时候，它是从RenderTexture.active中读取像素  
		screenShot.Apply();  
		
		// 重置相关参数，以使用camera继续在屏幕上显示  
		camera.targetTexture = null;  
		//ps: camera2.targetTexture = null;  
		RenderTexture.active = null; // JC: added to avoid errors  
		GameObject.Destroy(rt);  
		// 最后将这些纹理数据，成一个png图片文件  
		byte[] bytes = screenShot.EncodeToPNG();  
		//string filename = Application.dataPath + "/Screenshot.png";  
		//System.IO.File.WriteAllBytes(filename, bytes);  
		//Debug.Log(string.Format("截屏了一张照片: {0}", filename));  

		yield return new WaitForEndOfFrame();

		Hashtable content = new Hashtable();
		content["title"] = "test title";
		content["description"] = "test description";
		content["imageData"] = bytes;
		content["url"] = "http://www.gumichina.com/";
		string str=string.Join(",",bytes.Select(t=>t.ToString()).ToArray());
		Debug.Log(str);
		//content["imageFilePath"] = filename;
		
		ShareResultEvent evt = new ShareResultEvent(ShareResultHandler);
		ShareSDK.shareContent (PlatformType.WeChatTimeline, content, evt);
		
	}  

	IEnumerator CaptureCamera2()   
	{  
		Rect rect = new Rect();
		rect.width = Screen.width;
		rect.height = Screen.height;
		yield return new WaitForEndOfFrame();
		// 创建一个RenderTexture对象  
		RenderTexture rt = new RenderTexture((int)rect.width, (int)rect.height, 100);  
		// 临时设置相关相机的targetTexture为rt, 并手动渲染相关相机  
		camera.targetTexture = rt;  
		camera.Render();  
		//ps: --- 如果这样加上第二个相机，可以实现只截图某几个指定的相机一起看到的图像。  
		//ps: camera2.targetTexture = rt;  
		//ps: camera2.Render();  
		//ps: -------------------------------------------------------------------  
		
		// 激活这个rt, 并从中中读取像素。  
		RenderTexture.active = rt;  
		Texture2D screenShot = new Texture2D((int)rect.width, (int)rect.height, TextureFormat.RGB24,false);  
		screenShot.ReadPixels(rect, 0, 0);// 注：这个时候，它是从RenderTexture.active中读取像素  
		screenShot.Apply();  
		
		// 重置相关参数，以使用camera继续在屏幕上显示  
		camera.targetTexture = null;  
		//ps: camera2.targetTexture = null;  
		RenderTexture.active = null; // JC: added to avoid errors  
		GameObject.Destroy(rt);  
		// 最后将这些纹理数据，成一个png图片文件  
		byte[] bytes = screenShot.EncodeToPNG();  
		//string filename = Application.dataPath + "/Screenshot.png";  
		//System.IO.File.WriteAllBytes(filename, bytes);  
		//Debug.Log(string.Format("截屏了一张照片: {0}", filename));  
		
		yield return new WaitForEndOfFrame();
		
		Hashtable content = new Hashtable();
		content["content"] = "this is a test string.";
		content["title"] = "test title";
		content["description"] = "test description";
		content["url"] = "http://www.gumichina.com";
		content["imageData"] = bytes;

		ShareResultEvent evt = new ShareResultEvent(ShareResultHandler);
		ShareSDK.shareContent (PlatformType.SinaWeibo, content, evt);
		
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

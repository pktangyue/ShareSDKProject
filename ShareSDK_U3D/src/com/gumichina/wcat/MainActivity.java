package com.gumichina.wcat;

import android.os.Bundle;

import com.gumichina.sharesdk.unity3d.ShareSDKUtils;
import com.unity3d.player.UnityPlayerActivity;

public class MainActivity extends UnityPlayerActivity
{

	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		// 当使用ShareSDKApplication后，这里就不需要重复调用了
		ShareSDKUtils.prepare(this);
	}
}

package com.gumichina.sharesdk.framework.sinaweibo;

import java.util.HashMap;

import android.content.Context;

import com.gumichina.sharesdk.framework.Platform;
import com.gumichina.sharesdk.framework.PlatformType;

public class PlatformSinaWeibo extends Platform
{

	public PlatformSinaWeibo(Context paramContext)
	{
		super(paramContext);
		// TODO Auto-generated constructor stub
		platformid = PlatformType.SinaWeibo;
	}

	@Override
	public void setPlatformDevInfo(HashMap<String, Object> devInfo)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void removeAccount(boolean removeCookie)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isValid()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void authorize()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void showUser(String param)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void share(HashMap<String, Object> hash)
	{
		// TODO Auto-generated method stub

	}

}

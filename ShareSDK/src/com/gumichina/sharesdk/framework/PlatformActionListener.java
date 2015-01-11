package com.gumichina.sharesdk.framework;

import java.util.HashMap;

public interface PlatformActionListener {
	public abstract void onComplete(Platform arg0, int arg1,
			HashMap<String, Object> arg2);

	public abstract void onError(Platform arg0, int arg1, Throwable arg2);

	public abstract void onCancel(Platform arg0, int arg1);
}

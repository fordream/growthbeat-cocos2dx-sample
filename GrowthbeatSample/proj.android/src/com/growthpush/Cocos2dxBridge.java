package com.growthpush;

import org.cocos2dx.lib.Cocos2dxGLSurfaceView;

import android.os.Handler;
import android.os.Looper;

import com.growthbeat.GrowthbeatCore;
import com.growthpush.bridge.ExternalFrameworkBridge;

public class Cocos2dxBridge extends ExternalFrameworkBridge {

	@Override
	protected void callbackExternalFramework(final String customFiled) {

		if (GrowthbeatCore.getInstance().getClient() == null) {
			new Handler(Looper.getMainLooper()).post(new Runnable() {
				@Override
				public void run() {
					callbackExternalFramework(customFiled);
				}
			});
			return;
		}

		Cocos2dxGLSurfaceView.getInstance().queueEvent(new Runnable() {
			@Override
			public void run() {
				didOpenRemoteNotification(customFiled);
			}
		});

	}

	/*
	 * Remote notification callback method (JNI interface)
	 * 
	 * @param json json string
	 */
	static native void didOpenRemoteNotification(String json);

}

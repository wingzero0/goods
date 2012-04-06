package com.mygoods.object;

import java.io.IOException;
import java.net.MalformedURLException;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;
import com.facebook.android.Facebook.DialogListener;

public class FbRequest {
	private static FbRequest fbRequest = null;
	public Facebook fb = null;
	public AsyncFacebookRunner mAsyncRunner = null;
	public String access_token;
	public long expires;
	protected Activity caller;

	public static FbRequest FbRequestInstance(){
		if (FbRequest.fbRequest == null){
			FbRequest.fbRequest = new FbRequest();
		}
		return FbRequest.fbRequest;
	}
	private FbRequest(){
		this.fb = new Facebook("196747777104741");
		this.mAsyncRunner = new AsyncFacebookRunner(this.fb);
	}
	public void FbLogin(Activity caller, final FbAuthorizeCallBack callback, int requestCode){
		this.caller = caller;
		this.fb.authorize(caller, new String[] { "email" }, requestCode, 
			new DialogListener() {
			@Override
			public void onComplete(Bundle values) {
				Log.d("kit", "LoginComplete");
				access_token = fb.getAccessToken();
				expires = fb.getAccessExpires();
				callback.AuthorizeCallBack();
			}

			@Override
			public void onFacebookError(FacebookError error) {
				Log.d("kit", "fb error");
				Log.d("kit", error.toString());
			}

			@Override
			public void onError(DialogError e) {
				Log.d("kit", "error");
			}

			@Override
			public void onCancel() {
				Log.d("kit", "Login cancel");
			}
		});
	}
	public String GetPersonalInfo(){
		String path ="me";
		String jason = new String();
		
		Bundle params = new Bundle();
		params.putString("access_token", access_token);
		params.putString("fields", "email");
		
		Log.d("kit", "access_token="+access_token);
		
		try {
			jason = this.fb.request(path, params);
			Log.d("kit", jason);
		}catch (MalformedURLException e) {
		    Log.d("kit", "Invalid URL ("+path+"): "+e.getMessage());
		} catch (IOException e) {
			Log.d("kit", "Unable to execute ("+path+"): "+e.getMessage());
		}
		return jason;
	}
	public void FbLogout(){
		this.FbLogout(this.caller); // the caller when authorize
	}
	public void FbLogout(Activity caller){// may be the nell caller
		Log.d("kit", "logout fb");
        
		try{
			String ret = fb.logout(caller.getApplicationContext());
			Log.d("kit", ret);
		}catch (MalformedURLException e) {
		    // Print out the exception that occurred
		    Log.d("kit", "Invalid "+e.getMessage());
		} catch (IOException e) {
		    // Print out the exception that occurred
			Log.d("kit", "Unable "+e.getMessage());
		}
	}
}

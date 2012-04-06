// this class will deal with the social account
// login, logout, get personal information

package com.mygoods.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


import com.facebook.android.Facebook;
import com.mygoods.R;
import com.mygoods.object.FbRequest;
import com.mygoods.object.FbAuthorizeCallBack;
import com.mygoods.MainActivity;

public class LoginActivity extends Activity implements FbAuthorizeCallBack { // should be implement as an single-ton object
	/** Called when the activity is first created. */
	
	public static final int facebookCode = 0;
	public static final int mainPageCode = 1;
	
	String FILENAME = "AndroidSSO_data";
    private SharedPreferences mPrefs;
    String userEmail;
    String access_token;
    long expires;
    FbRequest fbr;
    
    @Override
	public void onCreate(Bundle savedInstanceState){
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.login);
		Button loginButton = (Button) findViewById(R.id.LoginButtion);
		loginButton.setOnClickListener(this.loginWithFb);
		
    	fbr = FbRequest.FbRequestInstance();
    	Facebook facebook = fbr.fb;
    	
		mPrefs = getPreferences(MODE_PRIVATE);
        access_token = mPrefs.getString("access_token", null);
        expires = mPrefs.getLong("access_expires", 0);
        userEmail = mPrefs.getString("userMail", null);
        
        if(access_token != null) {
        	facebook.setAccessToken(access_token);
        }
        if(expires != 0) {
        	facebook.setAccessExpires(expires);
        }
        if(facebook.isSessionValid()) {
        	Log.d("kit", "pass session valid");
        	this.StartMainPage();
        }
	}
    private OnClickListener loginWithFb = new OnClickListener() {
		public void onClick(View v) {
			fbr.FbLogin(LoginActivity.this, LoginActivity.this, LoginActivity.facebookCode);
		}
    };
    public void StartMainPage(){
    	Intent intent = new Intent(this, MainActivity.class);
		this.startActivityForResult(intent, 1);
    }
    public void AuthorizeCallBack(){
		SharedPreferences.Editor editor = mPrefs.edit();
		Facebook facebook = fbr.fb;
		access_token = facebook.getAccessToken();
		expires = facebook.getAccessExpires();
		userEmail = fbr.GetPersonalInfo();
		Log.d("kit", "access_token"+access_token);
		Log.d("kit", "expires" + expires);
		Log.d("kit", "userEmail" + userEmail);
		editor.putString("access_token", access_token);
		editor.putLong("access_expires", expires);
		editor.putString("userEmail", userEmail);
		editor.commit();
		this.StartMainPage();
    }
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == LoginActivity.facebookCode){
			fbr.fb.authorizeCallback(requestCode, resultCode, data);
		}else if (requestCode == LoginActivity.mainPageCode && resultCode == RESULT_FIRST_USER){
			Log.d("kit","logout return");
			fbr.FbLogout(LoginActivity.this);
			SharedPreferences.Editor editor = mPrefs.edit();
			editor.putString("access_token", null);
			editor.putLong("access_expires", 0);
			editor.putString("userEmail", null);
			editor.commit();
		}
	}
}
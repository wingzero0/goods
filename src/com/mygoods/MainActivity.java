// main page change into LoginActivity.java

package com.mygoods;

import com.facebook.android.Facebook;
import com.mygoods.activity.LoginActivity;
import com.mygoods.activity.socialNetWork;
import com.mygoods.activity.home;
import com.mygoods.activity.myGoods;

import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;

public class MainActivity extends TabActivity implements OnCheckedChangeListener{
    /** Called when the activity is first created. */
	private TabHost mHost;
	private RadioGroup radioderGroup;
	
	String FILENAME = "AndroidSSO_data";
    private SharedPreferences mPrefs;
    String userEmail;
    String access_token;
    long expires;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maintabs);
 
        mHost=this.getTabHost();
        
        mHost.addTab(mHost.newTabSpec("ONE").setIndicator("ONE")
        			.setContent(new Intent(this,home.class)));
        mHost.addTab(mHost.newTabSpec("TWO").setIndicator("TWO")
        		.setContent(new Intent(this,myGoods.class)));
        mHost.addTab(mHost.newTabSpec("THREE").setIndicator("THREE")
        		.setContent(new Intent(this,socialNetWork.class)));
        
        radioderGroup = (RadioGroup) findViewById(R.id.main_radio);
		radioderGroup.setOnCheckedChangeListener(this);
    }
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch(checkedId){
		case R.id.radio_button0:
			mHost.setCurrentTabByTag("ONE");
			break;
		case R.id.radio_button1:
			mHost.setCurrentTabByTag("TWO");
			break;
		case R.id.radio_button2:
			mHost.setCurrentTabByTag("THREE");
			break;
		case R.id.radio_button3:
			Log.d("kit", "call logout");
			this.setResult(RESULT_FIRST_USER);
			this.finish();
			break;
//		case R.id.radio_button4:
//			mHost.setCurrentTabByTag("FIVE");
//			break;
		}		
	}

}
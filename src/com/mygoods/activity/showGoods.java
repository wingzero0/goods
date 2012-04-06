package com.mygoods.activity;

import com.mygoods.R;
import com.mygoods.R.id;
import com.mygoods.R.layout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class showGoods extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.show_goods_activity);
		
		  Intent intent=this.getIntent();
		  Bundle bundle=intent.getExtras();
 
		  TextView nameText=(TextView) findViewById(R.id.Name);
		  nameText.setText(  bundle.getString("imageName"));
		  ImageView photoImage=(ImageView) findViewById(R.id.photo);
		  photoImage.setBackgroundResource(bundle.getInt("imageKey"));
		  TextView peopleText=(TextView) findViewById(R.id.people);
		  TextView commentText=(TextView) findViewById(R.id.comment);
		  Button moreButton=(Button) findViewById(R.id.more);
 
		 
	}
	 

}

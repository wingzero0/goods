package com.mygoods.activity;

import com.mygoods.IntentIntegrator;
import com.mygoods.IntentResult;
import com.mygoods.R;
import com.mygoods.R.id;
import com.mygoods.R.layout;
import com.mygoods.R.string;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class searhGoodsResult extends Activity {
	EditText barcodeid=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.search_goods_result_activity);
		
		  Intent intent=this.getIntent();
		  Bundle bundle=intent.getExtras();
 
		 Button moreButton=(Button) findViewById(R.id.more);
		 Button retakeButton=(Button) findViewById(R.id.retake);    
	      retakeButton.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					 IntentIntegrator integrator = new IntentIntegrator(searhGoodsResult.this);
				      integrator.initiateScan();
				}
				 
			 });
		 Button yesButton=(Button) findViewById(R.id.yes);
		 yesButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				showDialog("QQQ","rrr");
			}
			 
		 });
		 barcodeid=(EditText) findViewById(R.id.barcodeid);
		 barcodeid.setText(bundle.getString("barcode"));
	}
	  private void showDialog(String  title, String message) {
 
	        AlertDialog.Builder builder = new AlertDialog.Builder(this);
	        builder.setTitle(title);
	        builder.setMessage(message);
	        builder.setPositiveButton(R.string.Share,null);
	        builder.setNegativeButton(R.string.No_thanks, null);
	        builder.show();
	      }
	  @Override
	  public void onActivityResult(int requestCode, int resultCode, Intent intent) {
	    IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
	    if (result != null) {
	      String contents = result.getContents();
	      if (contents != null) {
	     
			  String[] str=result.toString().trim().split(" "); 
			  barcodeid.setText( str[2].replace("Raw", "").trim());
			 
			 
	      } else {
	        showDialog(getString(R.string.result_failed), getString(R.string.result_failed_why));
	      }
	    }
	   

	  }
}

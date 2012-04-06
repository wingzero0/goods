package com.mygoods.activity;
// this activity will call zxing to handle barcode detect

 

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
import android.widget.Button;


public class home extends Activity  {

	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.home_activity);
		findViewById(R.id.scan_anything).setOnClickListener(scanAnything);
	}

	  private final Button.OnClickListener scanAnything = new Button.OnClickListener() {
		    @Override
		    public void onClick(View v) {
		      IntentIntegrator integrator = new IntentIntegrator(home.this);
		      integrator.initiateScan();
		    }
		  };

		  private final Button.OnClickListener searchBookContents = new Button.OnClickListener() {
		    @Override
		    public void onClick(View v) {
		      Intent intent = new Intent("com.google.zxing.client.android.SEARCH_BOOK_CONTENTS");
		      intent.putExtra("ISBN", "9780441014989");
		      intent.putExtra("QUERY", "future");
		      startActivity(intent);
		    }
		  };
		  
		  @Override
		  public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		    IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
		    if (result != null) {
		      String contents = result.getContents();
		      if (contents != null) {
		    	  Intent newintent=new Intent(home.this,searhGoodsResult.class);
 
				  Bundle bundle=new Bundle();
				  String[] str=result.toString().trim().split(" "); 
				  bundle.putString("barcode",  str[2].replace("Raw", "").trim());
				 
				  newintent.putExtras(bundle);
 
		    	 
				  startActivity(newintent); 
		      } else {
		        showDialog(R.string.result_failed, getString(R.string.result_failed_why));
		      }
		    }
		   

		  }
		  private void showDialog(int title, CharSequence message) {
			
		        AlertDialog.Builder builder = new AlertDialog.Builder(this);
		        builder.setTitle(title);
		        builder.setMessage(message);
		        builder.setPositiveButton("OK", null);
		        builder.show();
		      }
		  
}

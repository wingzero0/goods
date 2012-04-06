package com.mygoods.activity;

import com.mygoods.CoverFlow;
import com.mygoods.R;
import com.mygoods.ReflectionImage;
import com.mygoods.R.drawable;
import com.mygoods.myGoods_.ImageAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class myGoods extends Activity {
	
	private Integer[] mImageIds = { 
			R.drawable.icon_1, 
			R.drawable.icon_2,
			R.drawable.icon_3,
			R.drawable.icon_4,
			R.drawable.icon_5};
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		CoverFlow cf = new CoverFlow(this); 
		cf.setBackgroundResource(R.color.white);
		cf.setAdapter(new ImageAdapter(this)); 
		ImageAdapter imageAdapter = new ImageAdapter(this);
		cf.setAdapter(imageAdapter);
//		cf.setAlphaMode(false); 
//		cf.setCircleMode(false);
//		cf.setSelection(3, true);  
		cf.setAnimationDuration(1000);
		cf.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				  Intent intent=new Intent();
				  intent.setClass(myGoods.this,showGoods.class);
				  Bundle bundle=new Bundle();
				  bundle.putInt("imageKey", mImageIds[arg2]);
				  bundle.putString("imageName", mImageIds[arg2].toString());
				  intent.putExtras(bundle);
				  startActivity(intent);
				
			}
			
		});
		setContentView(cf);
	}
	
	
	
	public class ImageAdapter extends BaseAdapter {
		int mGalleryItemBackground;
		private Context mContext;
		private Integer[] mImageIds = { 
				R.drawable.icon_1, 
				R.drawable.icon_2,
				R.drawable.icon_3,
				R.drawable.icon_4,
				R.drawable.icon_5};
		
		public ImageAdapter(Context c) {
			mContext = c;
		}
		public int getCount() {
			return mImageIds.length;
		}
		public Object getItem(int position) {
			return position; 
		}
		public long getItemId(int position) {
			return position;
		}
		public View getView(int position, View convertView, ViewGroup parent) {
			ReflectionImage i = new ReflectionImage(mContext);
			
			i.setImageResource(mImageIds[position]);
			i.setLayoutParams(new CoverFlow.LayoutParams(288, 228));
			i.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
			// Make sure we set anti-aliasing otherwise we get jaggies
			BitmapDrawable drawable = (BitmapDrawable) i.getDrawable();
			drawable.setAntiAlias(true);
			return i;
		}
 
		public float getScale(boolean focused, int offset) {
			return Math.max(0, 1.0f / (float) Math.pow(2, Math.abs(offset)));
		}
	}
}

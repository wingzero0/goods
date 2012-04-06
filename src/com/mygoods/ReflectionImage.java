package com.mygoods;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;
public class ReflectionImage extends ImageView {
	//是否為Reflection模式
	private boolean mReflectionMode = true;
	public ReflectionImage(Context context) {
		super(context);
	}
	public ReflectionImage(Context context, AttributeSet attrs) {
		super(context, attrs);
		//取得原始圖片的bitmap並重畫
		Bitmap originalImage = ((BitmapDrawable)this.getDrawable()).getBitmap();
		DoReflection(originalImage);
	}
	public ReflectionImage(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		Bitmap originalImage = ((BitmapDrawable)this.getDrawable()).getBitmap();
		DoReflection(originalImage);
	}
	public void setReflectionMode(boolean isRef) {
		mReflectionMode = isRef;
	}
	public boolean getReflectionMode() {
		return mReflectionMode;
	}
	//偷懶了,只重寫了setImageResource,和構造函數裡面幹了同樣的事情
	@Override
	public void setImageResource(int resId) {
		Bitmap originalImage = BitmapFactory.decodeResource(
				getResources(), resId);
		DoReflection(originalImage);
		//super.setImageResource(resId);
	}
	private void DoReflection(Bitmap originalImage) {
		final int reflectionGap = 4;							//原始圖片和反射圖片中間的間距
		int width = originalImage.getWidth();
		int height = originalImage.getHeight();
		
		//反轉
		Matrix matrix = new Matrix();
		matrix.preScale(1, -1);
	  //reflectionImage就是下面透明的那部分,可以設置它的高度為原始的3/4,這樣效果會更好些
		Bitmap reflectionImage = Bitmap.createBitmap(originalImage, 0,
				0, width, height, matrix, false);
		//創建一個新的bitmap,高度為原來的兩倍
		Bitmap bitmapWithReflection = Bitmap.createBitmap(width, (height + height), Config.ARGB_8888);
		Canvas canvasRef = new Canvas(bitmapWithReflection);
		
		//先畫原始的圖片
		canvasRef.drawBitmap(originalImage, 0, 0, null);
		//畫間距
		Paint deafaultPaint = new Paint();
		canvasRef.drawRect(0, height, width, height + reflectionGap, deafaultPaint);
		
		//畫被反轉以後的圖片
		canvasRef.drawBitmap(reflectionImage, 0, height + reflectionGap, null);
		// 創建一個漸變的蒙版放在下面被反轉的圖片上面
		Paint paint = new Paint();
		LinearGradient shader = new LinearGradient(0,
				originalImage.getHeight(), 0, bitmapWithReflection.getHeight()
						+ reflectionGap, 0x80ffffff, 0x00ffffff, TileMode.CLAMP);
		// Set the paint to use this shader (linear gradient)
		paint.setShader(shader);
		// Set the Transfer mode to be porter duff and destination in
		paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
		// Draw a rectangle using the paint with our linear gradient
		canvasRef.drawRect(0, height, width, bitmapWithReflection.getHeight()
				+ reflectionGap, paint);
	  //調用ImageView中的setImageBitmap
		this.setImageBitmap(bitmapWithReflection);
	}
}
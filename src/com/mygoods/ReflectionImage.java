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
	//�O�_��Reflection�Ҧ�
	private boolean mReflectionMode = true;
	public ReflectionImage(Context context) {
		super(context);
	}
	public ReflectionImage(Context context, AttributeSet attrs) {
		super(context, attrs);
		//���o��l�Ϥ���bitmap�í��e
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
	//���i�F,�u���g�FsetImageResource,�M�c�y��Ƹ̭��F�F�P�˪��Ʊ�
	@Override
	public void setImageResource(int resId) {
		Bitmap originalImage = BitmapFactory.decodeResource(
				getResources(), resId);
		DoReflection(originalImage);
		//super.setImageResource(resId);
	}
	private void DoReflection(Bitmap originalImage) {
		final int reflectionGap = 4;							//��l�Ϥ��M�Ϯg�Ϥ����������Z
		int width = originalImage.getWidth();
		int height = originalImage.getHeight();
		
		//����
		Matrix matrix = new Matrix();
		matrix.preScale(1, -1);
	  //reflectionImage�N�O�U���z����������,�i�H�]�m�������׬���l��3/4,�o�ˮĪG�|��n��
		Bitmap reflectionImage = Bitmap.createBitmap(originalImage, 0,
				0, width, height, matrix, false);
		//�Ыؤ@�ӷs��bitmap,���׬���Ӫ��⭿
		Bitmap bitmapWithReflection = Bitmap.createBitmap(width, (height + height), Config.ARGB_8888);
		Canvas canvasRef = new Canvas(bitmapWithReflection);
		
		//���e��l���Ϥ�
		canvasRef.drawBitmap(originalImage, 0, 0, null);
		//�e���Z
		Paint deafaultPaint = new Paint();
		canvasRef.drawRect(0, height, width, height + reflectionGap, deafaultPaint);
		
		//�e�Q����H�᪺�Ϥ�
		canvasRef.drawBitmap(reflectionImage, 0, height + reflectionGap, null);
		// �Ыؤ@�Ӻ��ܪ��X����b�U���Q���઺�Ϥ��W��
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
	  //�ե�ImageView����setImageBitmap
		this.setImageBitmap(bitmapWithReflection);
	}
}
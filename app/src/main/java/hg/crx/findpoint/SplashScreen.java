package hg.crx.findpoint;

import com.baidu.mapapi.SDKInitializer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

public class SplashScreen extends Activity {
    /**Called when the activity is first created. */
	@Override
	//界面样式
	public void onCreate(Bundle icicle){
		SDKInitializer.initialize(getApplicationContext());	//百度地图sdk初始化
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(icicle);
		getWindow().setFormat(PixelFormat.RGBA_8888);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_DITHER);
		setContentView(R.layout.splash_screen);
		new Handler().postDelayed(new Runnable(){
			public void run(){
				Intent mainIntent = new Intent(SplashScreen.this,MainActivity.class);
				SplashScreen.this.startActivity(mainIntent);
				SplashScreen.this.finish();
			}
		},3000);
	}
}
		

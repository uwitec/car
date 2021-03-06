package the.muisc;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Window;

import com.js.car.R;
import com.js.car.ui.BaseActivity;
import com.js.car.ui.LoginAndRegisterActivity;


public class FashionMusicActivity extends Activity {
	
	 /**
     *闪动屏幕触碰事件类
     */
    private Thread mSplashThread;    
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SharedPreferences sp = getSharedPreferences("service", 0);
		if (sp.getBoolean("isStart", false)) {
			startActivity(new Intent(this,LoginAndRegisterActivity.class));
			finish();
		} else {
			this.requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.activity_splash);
			// 启动闪屏界面的线程
	        mSplashThread =  new Thread(){
	            @Override
	            public void run(){
	                try {
	                    synchronized(this){
	                        // 闪屏的停留时间
	                        wait(2800);
	                    }
	                }
	                catch(InterruptedException ex){
	                }

	                finish();

	                //启动下一个Activity
	                startActivity(new Intent(FashionMusicActivity.this,
							LoginAndRegisterActivity.class));
					finish();
	                try{
	                	stop();   
	                }catch(Exception e){
	                	System.out.println(e);
	                }
	            }
	        };
	        
	        mSplashThread.start();

		}
	}
	   /**
	    *闪动屏幕触碰事件
	    *即当用户触碰一个APP的图标时，便唤起闪屏线程
	    */
	   @Override
	   public boolean onTouchEvent(MotionEvent evt)
	   {
	       if(evt.getAction() == MotionEvent.ACTION_DOWN)
	       {
	           synchronized(mSplashThread){
	               mSplashThread.notifyAll();
	           }
	       }
	       return true;
	   }
}

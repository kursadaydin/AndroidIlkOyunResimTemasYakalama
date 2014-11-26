package com.example.androidilkoyunresimtemasyakalama;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.handler.IUpdateHandler;
import org.anddev.andengine.engine.handler.timer.ITimerCallback;
import org.anddev.andengine.engine.handler.timer.TimerHandler;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.anddev.andengine.entity.primitive.Line;
import org.anddev.andengine.entity.primitive.Rectangle;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.MultiTapKeyListener;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends BaseGameActivity {

	private static final int CAMERA_WIDTH = 800;
	private static final int CAMERA_HEIGHT = 480;
	private Camera camera;
	private Engine engine;
	Scene sahne;

	private Texture texSaha, texOyuncu1, texOyuncu2;
	private TextureRegion texRegSaha, texRegOyuncu1, texRegOyuncu2;
	private Sprite spriteSaha, spriteOyuncu1, spriteOyuncu2;
	private TimerHandler timer;
	
	private int ilkKonumOyuncu1X =100;
	private int ilkKonumOyuncu2X =600;
	
	private int konumY =200;

	@Override
	public Engine onLoadEngine() {
		// TODO Auto-generated method stub
		camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		EngineOptions engineOptions = new EngineOptions(true,
				ScreenOrientation.LANDSCAPE, new FillResolutionPolicy(), camera);

		engineOptions.getTouchOptions().setRunOnUpdateThread(true);

		engine = new Engine(engineOptions);

		return engine;
	}

	@Override
	public void onLoadResources() {
		// TODO Auto-generated method stub

		texSaha = new Texture(1024, 512,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		texOyuncu1 = new Texture(128, 128,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		texOyuncu2 = new Texture(128, 128,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		texRegSaha = TextureRegionFactory.createFromAsset(texSaha, this,
				"footballpitch.png", 0, 0);
		texRegOyuncu1 = TextureRegionFactory.createFromAsset(texOyuncu1, this,
				"pul_kirmizi.png", 0, 0);
		texRegOyuncu2 = TextureRegionFactory.createFromAsset(texOyuncu2, this,
				"pul_siyah.png", 0, 0);

		Texture[] textures = { texSaha, texOyuncu1, texOyuncu2 };
		engine.getTextureManager().loadTextures(textures);

	}

	@Override
	public Scene onLoadScene() {
		// TODO Auto-generated method stub

		engine.registerUpdateHandler(new FPSLogger());
		sahne = new Scene();
		
		sahne.registerUpdateHandler(new IUpdateHandler() {
			
			@Override
			public void reset() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onUpdate(float pSecondsElapsed) {
				// TODO Auto-generated method stub
				spriteOyuncu1.setPosition(ilkKonumOyuncu1X+timer.getTimerSecondsElapsed()*25, konumY);
				spriteOyuncu2.setPosition(ilkKonumOyuncu2X-timer.getTimerSecondsElapsed()*25, konumY);
				
				if (spriteOyuncu1.collidesWith(spriteOyuncu2)) {
					
					spriteOyuncu1.setPosition(ilkKonumOyuncu1X, konumY);
					spriteOyuncu2.setPosition(ilkKonumOyuncu2X, konumY);
					
					engine.unregisterUpdateHandler(timer);
				}
				
			}
		});
		
		
		spriteSaha = new Sprite(0, 0, texRegSaha);
		spriteOyuncu1 = new Sprite(ilkKonumOyuncu1X, konumY, texRegOyuncu1);
		spriteOyuncu2 = new Sprite(ilkKonumOyuncu2X, konumY, texRegOyuncu2);

		engine.registerUpdateHandler(timer =new TimerHandler(10, false, new ITimerCallback() {
			
			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				// TODO Auto-generated method stub
				
				
			}
		}));
		
	
		sahne.attachChild(spriteSaha);
		sahne.attachChild(spriteOyuncu1);
		sahne.attachChild(spriteOyuncu2);
		
		return sahne;
	}

	@Override
	public void onLoadComplete() {
		// TODO Auto-generated method stub

	}

}

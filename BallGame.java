package test;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.font.BitmapText;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;

public class BallGame extends SimpleApplication implements PhysicsCollisionListener {

	public static void main(String[] args) {
		BallGame app = new BallGame();
		app.start(); // start the game
	}
	
	private BulletAppState bulletAppState;
	
	private BitmapText hudText;
	private int points = 0;
	
	private Ball ball;
	
	@Override
	public void simpleInitApp() {
		bulletAppState = new BulletAppState();
		//bulletAppState.setDebugEnabled(true);
		stateManager.attach(bulletAppState);
		bulletAppState.getPhysicsSpace().addCollisionListener(this);
		
		setDisplayFps(true);
		setDisplayStatView(false);
				
		Ball.initMesh(assetManager);
		PointBox.initMesh(assetManager);
		Floor.initMesh(assetManager);
		
		spawnBox(0, -1, 7, 5);
		spawnBox(5, -1, -4, 7);
		spawnBox(0, -1, -7, 8);
		spawnBox(-5, -1, -4, 10);
		
		rootNode.attachChild(new Floor(bulletAppState));
		
//		Geometry geom = new Geometry("Box", BOX); // create cube geometry from the shape
//		geom.setMaterial(boxMat); // set the cube's material
//		geom.setLocalTranslation(new Vector3f(0, -1, 7));
//		geom.rotate(0f, 0.75f, 0f);
//		rootNode.attachChild(geom); // make the cube appear in the scene
//		
//		Geometry geom2 = new Geometry("Box", BOX); // create cube geometry from the shape
//		geom2.setMaterial(boxMat);
//		geom2.setLocalTranslation(new Vector3f(5, -1, -4));
//		geom2.rotate(0f, 0.75f, 0f);
//		rootNode.attachChild(geom2);
//		
//		Geometry geom3 = new Geometry("Box", BOX); // create cube geometry from the shape
//		geom3.setMaterial(boxMat);
//		geom3.setLocalTranslation(new Vector3f(0, -1, -7));
//		geom3.rotate(0f, 0.75f, 0f);
//		rootNode.attachChild(geom3);
//		
//		Geometry geom4 = new Geometry("Box", BOX); // create cube geometry from the shape
//		geom4.setMaterial(boxMat);
//		geom4.setLocalTranslation(new Vector3f(-5, -1, -4));
//		geom4.rotate(0f, 0.75f, 0f);
//		rootNode.attachChild(geom4);
//		
//		Box b5 = new Box(25f, .5f, 50f); // create cube shape
//		Geometry geom5 = new Geometry("floor", b5); // create cube geometry from the shape
//		geom5.setMaterial(floorMat);
//		geom5.setLocalTranslation(new Vector3f(0, -2, 0));
//		rootNode.attachChild(geom5);
		
		
		
		initKeys();
		initGui();
	}
	
	private void initKeys() {
		inputManager.addMapping("Launch", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
		
		inputManager.addListener(analogListener, "Launch");
	}
	
	private final AnalogListener analogListener = new AnalogListener() {
		@Override
		public void onAnalog(String name, float value, float tpf) {
			if (ball == null) {
				spawnBall();
			}
		}
	};
	
	private void spawnBall() {
		ball = new Ball(cam, bulletAppState);
		rootNode.attachChild(ball);
	}
	
	private void spawnBox(float x, float y, float z, int points) {
		PointBox box = new PointBox(new Vector3f(x, y, z), points, bulletAppState);
		rootNode.attachChild(box);
	}
	
	@Override
	public void collision(PhysicsCollisionEvent event) {
		if (ball != null && event.getNodeB().getName().equals(ball.getName())) {
			if (event.getNodeA() instanceof PointBox) {
				PointBox box = (PointBox) event.getNodeA();
				addPoints(box.getPoints());
			}
			bulletAppState.getPhysicsSpace().remove(ball.getPhysics());
			rootNode.detachChild(ball);
			ball = null;
		}
	}
	
	private void addPoints(int points) {
		this.points += points;
		hudText.setText("Points: " + this.points);
	}
	
	
	// trying this but it is not working.
	//found the code online and trying to apply it to this program
	private void initGui() {
		guiNode.detachAllChildren();
		guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
		BitmapText ch = new BitmapText(guiFont, false);
		ch.setSize(guiFont.getCharSet().getRenderedSize() * 2);
		ch.setText("+"); // fake crosshairs :)
		ch.setColor(ColorRGBA.Red);
		ch.setLocalTranslation( // center
			settings.getWidth() / 2 - guiFont.getCharSet().getRenderedSize() / 3 * 2,
			settings.getHeight() / 2 + ch.getLineHeight() / 2, 0);
		guiNode.attachChild(ch);
		
		hudText = new BitmapText(guiFont, false);
		hudText.setSize(guiFont.getCharSet().getRenderedSize());
		hudText.setColor(ColorRGBA.Blue);
		hudText.setText("Points: " + points);
		hudText.setLocalTranslation(20, settings.getHeight() - guiFont.getCharSet().getLineHeight() + 10, 0);
		guiNode.attachChild(hudText);
	}
	
	
}
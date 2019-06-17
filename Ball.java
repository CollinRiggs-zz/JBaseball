package test;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Sphere;

public class Ball extends Geometry {
	
	private static final Sphere MESH = new Sphere(16, 16, 0.5f);
	private static final float GRAVITY = -5f;

	private static Material ballMat;
	
	private RigidBodyControl ballPhy;
	
	public Ball(Camera camera, BulletAppState state) {
		super("Sphere", MESH);
		this.setMaterial(ballMat);
		this.setLocalTranslation(camera.getLocation());
		ballPhy = new RigidBodyControl(0.01f);
		this.addControl(ballPhy);
		state.getPhysicsSpace().add(ballPhy);
		ballPhy.setLinearVelocity(camera.getDirection().mult(25f));
		ballPhy.activate();
		ballPhy.setGravity(new Vector3f(0f, GRAVITY, 0f));
	}
	
	public static void initMesh(AssetManager assetManager) {
		ballMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		ballMat.setColor("Color", ColorRGBA.Red);
	}
	
	public RigidBodyControl getPhysics() {
		return ballPhy;
	}

}

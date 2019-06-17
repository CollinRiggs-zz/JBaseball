package test;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;

public class PointBox extends Geometry {
	
	public static final Box MESH = new Box(.5f, .25f, .5f);
	
	private static Material boxMat;
	
	private RigidBodyControl boxPhy;
	
	private int points;
	
	public PointBox(Vector3f loc, int points, BulletAppState state) {
		super("Box", MESH);
		this.setMaterial(boxMat);
		this.setLocalTranslation(loc);
		this.rotate(0f, 0.75f, 0f);
		boxPhy = new RigidBodyControl(0f);
		this.addControl(boxPhy);
		state.getPhysicsSpace().add(boxPhy);
		boxPhy.activate();
		this.points = points;
	}
	
	public static void initMesh(AssetManager assetManager) {
		boxMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		boxMat.setColor("Color", ColorRGBA.Brown);
	}
	
	public int getPoints() {
		return points;
	}

}

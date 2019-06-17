package test;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;

public class Floor extends Geometry {
	
	public static final Box MESH = new Box(25f, .5f, 50f);
	
	private static Material floorMat;
	
	private RigidBodyControl floorPhy;
	
	public Floor(BulletAppState state) {
		super("Box", MESH);
		this.setMaterial(floorMat);
		this.setLocalTranslation(0, -2, 0);
		floorPhy = new RigidBodyControl(0f);
		this.addControl(floorPhy);
		state.getPhysicsSpace().add(floorPhy);
		floorPhy.activate();
	}
	
	public static void initMesh(AssetManager assetManager) {
		floorMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		floorMat.setColor("Color", ColorRGBA.White);
	}

}

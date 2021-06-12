package hexamap.gl;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.VertexBuffer;
import com.jme3.scene.VertexBuffer.Type;
import com.jme3.scene.shape.Sphere;
import com.jme3.system.AppSettings;
import com.jme3.util.BufferUtils;

import hexamap.coordinates.Axial;
import hexamap.coordinates.Coordinate;
import hexamap.regions.Hexagon;
import hexamap.regions.Rhombus;

/**
 */
public class HelloWorld extends SimpleApplication {
	
	private float INIT_X = 50;
	private float INIT_Y = 50;
	private Geometry earth;

	@Override
	public void simpleInitApp() {

		flyCam.setMoveSpeed(100);
		
		try {
			Geometry geometry = new Hexamap(new Rhombus<Axial>(64, Axial.class),"Small grid");
			
			Material mat = new Material(assetManager, "Materials/Geom/Hexamap.j3md");
			mat.setColor("Color", ColorRGBA.Brown);
			mat.setInt("Size", 1);
			geometry.setMaterial(mat);
			
			rootNode.attachChild(geometry);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			Geometry geometry = new Hexamap(new Hexagon<Axial>(32, Axial.class),"Big grid");
			
			Material mat = new Material(assetManager, "Materials/Geom/Hexamap.j3md");
			mat.setColor("Color", ColorRGBA.Gray);
			mat.setInt("Size", 8);
			geometry.setMaterial(mat);
			
			rootNode.attachChild(geometry);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Node sol = new Node("sol");
        rootNode.attachChild(sol);

		Geometry sun = new Geometry("Sun", new Sphere(100, 100, (float) 30 * (float) 0.9 * (float) Math.sqrt(3)));

		Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		mat.setColor("Color", ColorRGBA.Yellow);
		sun.setMaterial(mat);

		sol.attachChild(sun);
		
		earth = new Geometry("Earth", new Sphere(100, 100, (float) 1 * (float) 0.9 * (float) Math.sqrt(3)));

		mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		mat.setColor("Color", ColorRGBA.Blue);
		earth.setMaterial(mat);
		
        earth.move(INIT_X,INIT_Y, 0);

        sol.attachChild(earth);
	}

	@Override
	public void simpleUpdate(float tpf) {
		//INIT_X+=tpf/1000;
		
        //earth.move(INIT_X,INIT_Y, 0);
	}
	
	public static void main(String[] args) {
		HelloWorld app = new HelloWorld();
		AppSettings settings = new AppSettings(true);
		settings.setRenderer(AppSettings.LWJGL_OPENGL33);
		app.setSettings(settings);
		app.start();
	}
}

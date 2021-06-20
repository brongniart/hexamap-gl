package hexamap.gl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.material.RenderState.FaceCullMode;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.VertexBuffer;
import com.jme3.scene.VertexBuffer.Type;
import com.jme3.scene.control.LodControl;
import com.jme3.scene.shape.Sphere;
import com.jme3.system.AppSettings;
import com.jme3.util.BufferUtils;

import hexamap.coordinates.Axial;
import hexamap.coordinates.Coordinate;
import hexamap.regions.Hexagon;
import hexamap.regions.Region;
import hexamap.regions.Rhombus;
import jme3tools.optimize.LodGenerator;

/**
 */
public class HelloWorld extends SimpleApplication {

	private float INIT_X = 50;
	private float INIT_Y = 50;
	private Geometry earth;

	@Override
	public void simpleInitApp() {

		flyCam.setMoveSpeed(100);
		
		HierarchialGrid grid = new HierarchialGrid("Grid",assetManager);
		rootNode.attachChild(grid);
		
		Set<Axial> list = new HashSet<Axial>();
		for (Coordinate c : new Axial().getNeigbours(16)) {
			list.add((Axial) c);
		}
		Geometry geometry = new SimpleGrid(list, "\'Ring\'");

		Material mat = new Material(assetManager, "Materials/Geom/Hexamap/SimpleGrid.j3md");
		mat.setColor("Color", ColorRGBA.Red);
		mat.setFloat("Size", 1);
		geometry.setMaterial(mat);
		
		rootNode.attachChild(geometry);
		
		list = new HashSet<Axial>();
		Region<Axial> region = new Rhombus<Axial>(256, Axial.class);
		System.out.println("size: "+region.size());
		((Rhombus<Axial>) region).switchDirection();
		for (int i = 0; i < 256; i++) {
			list.add(region.getRandom());
		}
		geometry = new SimpleGrid(list, "Random_Rhombus");

		mat = new Material(assetManager, "Materials/Geom/Hexamap/SimpleGrid.j3md");
		mat.setColor("Color", ColorRGBA.Green);
		mat.setFloat("Size", 1);
		geometry.setMaterial(mat);

		geometry.getMesh().setStatic();

		rootNode.attachChild(geometry);

		list = new HashSet<Axial>();
		region = new Hexagon<Axial>(128, Axial.class);
		for (int i = 0; i < 256; i++) {
			list.add(region.getRandom());
		}
		geometry = new SimpleGrid(list, "Random_Hexagon");
		
		mat = new Material(assetManager, "Materials/Geom/Hexamap/SimpleGrid.j3md");
		mat.setColor("Color", ColorRGBA.Yellow);
		mat.setFloat("Size", 1);
		geometry.setMaterial(mat);

		geometry.getMesh().setStatic();

		rootNode.attachChild(geometry);

		/**/
		Node sol = new Node("sol");

		Geometry sun = new Geometry("Sun", new Sphere(100, 100, (float) 30 * (float) 0.9 * (float) Math.sqrt(3)));

		mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		mat.setColor("Color", ColorRGBA.Yellow);
		sun.setMaterial(mat);

		sol.attachChild(sun);

		earth = new Geometry("Earth", new Sphere(100, 100, (float) 1 * (float) 0.9 * (float) Math.sqrt(3)));

		mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		mat.setColor("Color", ColorRGBA.Blue);
		earth.setMaterial(mat);

		earth.move(INIT_X, INIT_Y, 0);

		sol.attachChild(earth);

		// rootNode.attachChild(sol);
	}

	@Override
	public void simpleUpdate(float tpf) {
		// INIT_X+=tpf/1000;

		// earth.move(INIT_X,INIT_Y, 0);
	}

	public static void main(String[] args) {
		HelloWorld app = new HelloWorld();
		AppSettings settings = new AppSettings(true);
		settings.setRenderer(AppSettings.LWJGL_OPENGL33);
		app.setSettings(settings);
		app.start();
	}
}

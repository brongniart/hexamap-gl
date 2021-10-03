package hexamap.gl;

import java.util.HashSet;
import java.util.Set;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;
import com.jme3.system.AppSettings;

import hexamap.coordinates.Axial;
import hexamap.coordinates.Coordinate;
import hexamap.coordinates.Direction;
import hexamap.regions.Hexagon;
import hexamap.regions.Region;
import hexamap.regions.Rhombus;

/**
 */
public class HelloWorld extends SimpleApplication {

	private float INIT_X = 24;
	private float INIT_Y = 24;
	private Geometry earth;

	@Override
	public void simpleInitApp() {
		Region<Axial> region;
		Material mat;
		Geometry geometry;

		flyCam.setMoveSpeed(100);

		System.out.println("size: "+new Hexagon<Axial>(256, Axial.class).size());
		
		HierarchialGrid grid = new HierarchialGrid("Grid",assetManager);
		rootNode.attachChild(grid);
		
		Set<Axial> list = new HashSet<Axial>();
		for (Coordinate c : new Axial(0,0).getNeigbours(100)) {
			list.add((Axial) c);
		}
		geometry = new SimpleGrid(list, "\'Ring\'");

		mat = new Material(assetManager, "Materials/Geom/Hexamap/SimpleGrid.j3md");
		mat.setColor("Color", ColorRGBA.Red);
		mat.setFloat("Size", 1);
		geometry.setMaterial(mat);
		
		rootNode.attachChild(geometry);

		list = new HashSet<Axial>();
		region = new Hexagon<Axial>(20, Axial.class);
		for (int i = 0; i < 20*8; i++) {
			list.add((Axial) region.getRandom().add(new Axial(53,21)));
		}
		geometry = new SimpleGrid(list, "Random_Hexagon");
		
		mat = new Material(assetManager, "Materials/Geom/Hexamap/SimpleGrid.j3md");
		mat.setColor("Color", ColorRGBA.Yellow);
		mat.setFloat("Size", 1);
		geometry.setMaterial(mat);

		geometry.getMesh().setStatic();

		rootNode.attachChild(geometry);
		
		int size = 18;
		int samples = 16;
		
		list = new HashSet<Axial>();
		region = new Rhombus<Axial>(size, Axial.class);
		for (int i = 0; i < samples*size; i++) {
			list.add(region.getRandom());
		}
		geometry = new SimpleGrid(list, "Random_Rhombus_1");

		mat = new Material(assetManager, "Materials/Geom/Hexamap/SimpleGrid.j3md");
		mat.setColor("Color", ColorRGBA.Green);
		mat.setFloat("Size", 1);
		geometry.setMaterial(mat);

		geometry.getMesh().setStatic();

		rootNode.attachChild(geometry);

		list = new HashSet<Axial>();
		region = new Rhombus<Axial>(size, Direction.SOUTH_EAST,Axial.class);
		for (int i = 0; i < samples*size; i++) {
			list.add(region.getRandom());
		}
		geometry = new SimpleGrid(list, "Random_Rhombus_2");

		mat = new Material(assetManager, "Materials/Geom/Hexamap/SimpleGrid.j3md");
		mat.setColor("Color", ColorRGBA.Magenta);
		mat.setFloat("Size", 1);
		geometry.setMaterial(mat);

		geometry.getMesh().setStatic();

		rootNode.attachChild(geometry);

		list = new HashSet<Axial>();
		region = new Rhombus<Axial>(size, Direction.NORD_EAST,Axial.class);
		for (int i = 0; i < samples*size; i++) {
			list.add(region.getRandom());
		}
		geometry = new SimpleGrid(list, "Random_Rhombus_3");

		mat = new Material(assetManager, "Materials/Geom/Hexamap/SimpleGrid.j3md");
		mat.setColor("Color", ColorRGBA.Pink);
		mat.setFloat("Size", 1);
		geometry.setMaterial(mat);

		geometry.getMesh().setStatic();

		rootNode.attachChild(geometry);
		
		list = new HashSet<Axial>();
		region = new Hexagon<Axial>(5, Axial.class);
		for (Coordinate c : region) {
			list.add((Axial) c);
		}
		geometry = new SimpleGrid(list, "Random_Rhombus_center");

		mat = new Material(assetManager, "Materials/Geom/Hexamap/SimpleGrid.j3md");
		mat.setColor("Color", ColorRGBA.LightGray);
		mat.setFloat("Size", 1);
		geometry.setMaterial(mat);

		geometry.getMesh().setStatic();

		rootNode.attachChild(geometry);

		geometry = new SimpleGrid(new Axial[] { new Axial()}, "Center");

		mat = new Material(assetManager, "Materials/Geom/Hexamap/SimpleGrid.j3md");
		mat.setColor("Color", ColorRGBA.Orange);
		mat.setFloat("Size", 1);
		geometry.setMaterial(mat);
		
		rootNode.attachChild(geometry);

		/**/
		Node sol = new Node("sol");

		Geometry sun = new Geometry("Sun", new Sphere(100, 100, (float) 15 * (float) 0.9 * (float) Math.sqrt(3)));

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

		//rootNode.attachChild(sol);
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

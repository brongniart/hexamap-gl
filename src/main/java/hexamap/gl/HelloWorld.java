package hexamap.gl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
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
		
		try {
			Rhombus<Axial> rhombus = new Rhombus<Axial>(256, Axial.class);
			rhombus.switchDirection();
			Geometry geometry = new Hexamap(rhombus,"Tiny grid");
			
			Material mat = new Material(assetManager, "Materials/Geom/Hexamap/SimpleGrid.j3md");
			mat.setColor("Color", ColorRGBA.Brown);
			geometry.setMaterial(mat);
			
			rootNode.attachChild(geometry);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			Rhombus<Axial> rhombus = new Rhombus<Axial>(16, Axial.class);
			rhombus.switchDirection();
			rhombus.switchDirection();
			Geometry geometry = new Hexamap(rhombus,"Small grid");
			
			Material mat = new Material(assetManager, "Materials/Geom/Hexamap/SimpleGrid.j3md");
			mat.setColor("Color", ColorRGBA.LightGray);
			mat.setInt("Size",20);
			geometry.setMaterial(mat);
			
			rootNode.attachChild(geometry);

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			Set<Axial> list = new HashSet<Axial>();
			for (Coordinate c : new Axial().getAllNeigbours(13)) {
					list.add((Axial) c);
			}	
			Geometry geometry = new Hexamap(list,"\'Ring\'");
			
			Material mat = new Material(assetManager, "Materials/Geom/Hexamap/SimpleGrid.j3md");
			mat.setColor("Color", ColorRGBA.Red);
			mat.setInt("Size", 1);
			geometry.setMaterial(mat);
			rootNode.attachChild(geometry);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		try {
			Geometry geometry = new Hexamap(new Hexagon<Axial>(256, Axial.class),"Big grid");
			
			Material mat = new Material(assetManager, "Materials/Geom/Hexamap/SimpleGrid.j3md");
			mat.setColor("Color", ColorRGBA.Gray);
			mat.setInt("Size", 16);
			geometry.setMaterial(mat);
			
			rootNode.attachChild(geometry);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			Geometry geometry = new Hexamap(new Hexagon<Axial>(16, Axial.class),"Really big grid");
			
			Material mat = new Material(assetManager, "Materials/Geom/Hexamap/SimpleGrid.j3md");
			mat.setColor("Color", ColorRGBA.White);
			mat.setInt("Size", 256);
			geometry.setMaterial(mat);
			
			rootNode.attachChild(geometry);
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		/**/
		try {
			Set<Axial> list = new HashSet<Axial>();
			Rhombus<Axial> region = new Rhombus<Axial>(256, Axial.class);
			region.switchDirection();
			for (int i=0;i<4096;i++) {
				list.add(region.getRandom());
			}
			Geometry geometry = new Hexamap(list,"Random_Rhombus");
			
			Material mat = new Material(assetManager, "Materials/Geom/Hexamap/SimpleGrid.j3md");
			mat.setColor("Color", ColorRGBA.Green);
			mat.setInt("Size", 1);
			geometry.setMaterial(mat);
			rootNode.attachChild(geometry);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			Set<Axial> list = new HashSet<Axial>();
			Region<Axial> region = new Hexagon<Axial>(128, Axial.class);
			for (int i=0;i<4096;i++) {
				list.add(region.getRandom());
			}
			Geometry geometry = new Hexamap(list,"Random_Hexagon");
			
			Material mat = new Material(assetManager, "Materials/Geom/Hexamap/SimpleGrid.j3md");
			mat.setColor("Color", ColorRGBA.Yellow);
			mat.setInt("Size", 1);
			geometry.setMaterial(mat);
			rootNode.attachChild(geometry);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/**/
		Node sol = new Node("sol");
        
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
        
        //rootNode.attachChild(sol);
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

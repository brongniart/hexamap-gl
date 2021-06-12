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

/**
 */
public class HelloWorld extends SimpleApplication {
	
	private float INIT_X = 50;
	private float INIT_Y = 50;
	private Geometry earth;

	@Override
	public void simpleInitApp() {

		flyCam.setMoveSpeed(100);

		/*
		 * mesh.setBuffer(VertexBuffer.Type.Index, 1, BufferUtils.createIntBuffer(new
		 * int[]{0,1})); //2,3,4,5,6})); mesh.setBuffer(VertexBuffer.Type.Position, 2,
		 * BufferUtils.createFloatBuffer(new float[]{ 0,0, 2,-(float) Math.sqrt(3),
		 * 0,-(float) Math.sqrt(3) // 0,(float) Math.sqrt(3), }));
		 */
		Hexagon<Axial> region;
		//Rhombus<Axial> region;
		try {
			Mesh mesh = new Mesh();
			region = new Hexagon<Axial>(256, Axial.class);
			 //region = new Rhombus<Axial>(5);

			float[] points = new float[region.size() * 2];
			float[] color = new float[region.size() * 3];
			int i = 0,j=0;
			for (Coordinate c : region) {
				points[i++] = c.getX();
				points[i++] = c.getY();
				
				color[j++] = 0.1f+(.2f*i);;
				color[j++] = 0.9f-(0.2f*i);
				color[j++] = 0.5f;
			}
			mesh.setBuffer(VertexBuffer.Type.Position, 2, BufferUtils.createFloatBuffer(points));
			mesh.setBuffer(Type.Color, 3, BufferUtils.createFloatBuffer(color));
			mesh.setMode(Mesh.Mode.Points);
			// mesh.setBound(new BoundingBox(new Vector3f(0, 0, 0), 10, 10, 10));
			mesh.updateCounts();
			
			Geometry geometry = new Geometry("Region", mesh);
			geometry.updateGeometricState();
			
			Material mat = new Material(assetManager, "Materials/Geom/Hexamap.j3md");
			mat.setColor("Color", ColorRGBA.Brown);
			mat.setInt("Size", 1);
			geometry.setMaterial(mat);
			rootNode.attachChild(geometry);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Hexagon<Axial> region1;
		//Rhombus<Axial> region;
		try {
			Mesh mesh = new Mesh();
			region1 = new Hexagon<Axial>(2, Axial.class);
			 //region = new Rhombus<Axial>(5);

			float[] points = new float[region1.size() * 2];
			float[] color = new float[region1.size() * 3];
			int i = 0,j=0;
			for (Coordinate c : region1) {
				points[i++] = c.getX();
				points[i++] = c.getY();
				
				color[j++] = 0.1f+(.2f*i);;
				color[j++] = 0.9f-(0.2f*i);
				color[j++] = 0.5f;
			}
			mesh.setBuffer(VertexBuffer.Type.Position, 2, BufferUtils.createFloatBuffer(points));
			mesh.setBuffer(Type.Color, 3, BufferUtils.createFloatBuffer(color));
			mesh.setMode(Mesh.Mode.Points);
			// mesh.setBound(new BoundingBox(new Vector3f(0, 0, 0), 10, 10, 10));
			mesh.updateCounts();
			
			Geometry geometry = new Geometry("Region2", mesh);
			geometry.updateGeometricState();
			
			Material mat = new Material(assetManager, "Materials/Geom/Hexamap.j3md");
			mat.setColor("Color", ColorRGBA.Gray);
			mat.setInt("Size", 128);
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

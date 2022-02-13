package hexamap.gl;

import java.util.HashSet;
import java.util.Random;
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
import hexamap.regions.Region;
import hexamap.regions.base.Hexagon;
import hexamap.regions.base.Triangle;

/**
 */
public class HelloWorld extends SimpleApplication {

    private float INIT_X = 24;
    private float INIT_Y = 24;
    private Geometry earth;

    @Override
    public void simpleInitApp() {
        Region region;
        Material mat;
        Geometry geometry;

        flyCam.setMoveSpeed(100);

        Set<Axial> list = new HashSet<Axial>();
        for (Coordinate c : new Axial(0, 0).getNeigbours(20)) {
            list.add(new Axial(c));
        }
        geometry = new SimpleGrid(list, "\'Ring\'");

        mat = new Material(assetManager, "Materials/Geom/Hexamap/SimpleGrid.j3md");
        mat.setColor("Color", ColorRGBA.Red);
        mat.setFloat("Size", 1);
        geometry.setMaterial(mat);

        rootNode.attachChild(geometry);

        list = new HashSet<Axial>();
        region = new Hexagon(20, new Axial());
        Random rand=new Random();
        for (int i = 0; i < region.size(); i++) {
            Coordinate c = region.getRandom(rand);
            System.out.println(c);
            list.add((Axial) c);
            System.out.println(list.size()+":"+region.size());
        }
        System.out.println(list.size()+":"+region.size());
        geometry = new SimpleGrid(list, "Random_Hexagon");

        mat = new Material(assetManager, "Materials/Geom/Hexamap/SimpleGrid.j3md");
        mat.setColor("Color", ColorRGBA.Yellow);
        mat.setFloat("Size", 1);
        geometry.setMaterial(mat);

        geometry.getMesh().setStatic();

        rootNode.attachChild(geometry);

        list = new HashSet<Axial>();
        region = new Triangle(Direction.NORD_EAST,5, new Axial());
        for (Coordinate c : region) {
            list.add(new Axial(c));
        }
        geometry = new SimpleGrid(list, "Triangle");

        mat = new Material(assetManager, "Materials/Geom/Hexamap/SimpleGrid.j3md");
        mat.setColor("Color", ColorRGBA.LightGray);
        mat.setFloat("Size", 1);
        geometry.setMaterial(mat);

        geometry.getMesh().setStatic();

        rootNode.attachChild(geometry);
        
        /**/
        Node sol = new Node("sol");

        Geometry sun = new Geometry("Sun", new Sphere(100, 100, (float) 1 * (float) 0.9 * (float) Math.sqrt(3)));

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

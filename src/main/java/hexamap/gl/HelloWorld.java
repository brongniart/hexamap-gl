package hexamap.gl;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer;
import com.jme3.scene.shape.Sphere;
import com.jme3.system.AppSettings;
import com.jme3.util.BufferUtils;

/**
 * Created by michael on 23.02.15.
 */
public class HelloWorld extends SimpleApplication {
    @Override
    public void simpleInitApp() { 
        Mesh mesh = new Mesh();
        /* 
        mesh.setBuffer(VertexBuffer.Type.Index, 1, BufferUtils.createIntBuffer(new int[]{0,1})); //2,3,4,5,6}));
        mesh.setBuffer(VertexBuffer.Type.Position, 2, BufferUtils.createFloatBuffer(new float[]{
                0,0,
                2,-(float) Math.sqrt(3),
                0,-(float) Math.sqrt(3)
 //               0,(float) Math.sqrt(3),
            }));
        */
        //Axial coordinate = new Axial();
        
        mesh.setBuffer(VertexBuffer.Type.Index, 1, BufferUtils.createIntBuffer(new int[]{0,1,2,3,4,5,6}));
        mesh.setBuffer(VertexBuffer.Type.Position, 2, BufferUtils.createFloatBuffer(new float[]{
                0,0,
                0, -1,
                0, 1,
                1, -1,
                -1, 1,
                -1, 0,
                1, 0
            }));
        mesh.setMode(Mesh.Mode.Points);
        //mesh.setBound(new BoundingBox(new Vector3f(0, 0, 0), 10, 10, 10));
        mesh.updateCounts();
        Geometry geometry = new Geometry("Test", mesh);
        geometry.updateGeometricState();
        geometry.setMaterial(new Material(assetManager, "Materials/Geom/Hexamap.j3md"));
        geometry.getMaterial().getAdditionalRenderState().setFaceCullMode(RenderState.FaceCullMode.Off);
        //geometry.setMaterial(assetManager.loadMaterial("Materials/Geom/SimpleTess.j3md"));
        rootNode.attachChild(geometry);

        Geometry geometry1 = new Geometry("T1", new Sphere(100, 100, (float) 1*(float) 0.9*(float) Math.sqrt(3)));
        //geometry1.setMaterial(new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md"));
        //rootNode.attachChild(geometry1);
    }

    public static void main(String[] args) {
        HelloWorld app = new HelloWorld();
        AppSettings settings = new AppSettings(true);
        settings.setRenderer(AppSettings.LWJGL_OPENGL33);
        app.setSettings(settings);
        app.start();
    }
}

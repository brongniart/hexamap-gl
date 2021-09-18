package hexamap.gl;

import java.util.ArrayList;
import java.util.Set;

import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer;
import com.jme3.util.BufferUtils;

import hexamap.coordinates.Axial;
import hexamap.regions.Region;

public class SimpleGrid  extends Geometry {
	
	public SimpleGrid(Set<Axial> region,String name) {
		super(name,new Mesh());
		createMesh(region.toArray(new Axial[region.size()]));
	}
	
	public SimpleGrid(Region<Axial> region,String name) {
		super(name,new Mesh());
		createMesh(region.toArray(new Axial[region.size()]));
		
		/*
		boolean odd=true;
		int lastDistance=0;
		List<Axial> list = new ArrayList<>();
		for (Axial coord: region) {
			
			if (lastDistance!=coord.distance(new Axial())) {
				lastDistance=coord.distance(new Axial());
				list.add(coord);
				odd=true;
			} else {
				if (odd) {
					list.add(coord);
				}
				odd=!odd;
			}
		}
		createMesh((Axial[]) list.toArray(new Axial[list.size()]));
		*/
	}

	public SimpleGrid(Axial[] region, String name) {
		super(name,new Mesh());
		createMesh(region);
	}
	
	private void createMesh(Axial[] region) {
		float[] points = new float[region.length * 2];
		int i = 0;
		for (Axial c : region) {
			//System.out.println(c);
			points[i++] = c.getX();
			points[i++] = c.getY();
		}
		
		mesh.setBuffer(VertexBuffer.Type.Position, 2, BufferUtils.createFloatBuffer(points));
		//mesh.setBuffer(Type.Color, 3, BufferUtils.createFloatBuffer(color));
		mesh.setMode(Mesh.Mode.Points);
		mesh.updateCounts();
		
		setCullHint(CullHint.Never); 
	}
}

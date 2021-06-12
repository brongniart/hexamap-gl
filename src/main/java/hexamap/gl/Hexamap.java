package hexamap.gl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer;
import com.jme3.scene.VertexBuffer.Type;
import com.jme3.util.BufferUtils;

import hexamap.coordinates.Axial;
import hexamap.coordinates.Coordinate;
import hexamap.regions.Region;

public class Hexamap  extends Geometry {
	
	public Hexamap(Region<Axial> region,String name) {
		super(name,new Mesh());
		System.err.println("size: "+region.size());
		float[] points = new float[region.size() * 2];
		float[] color = new float[region.size() * 3];
		int i = 0,j=0;
		for (Coordinate c : region) {
			System.err.println(c);
			points[i++] = c.getX();
			points[i++] = c.getY();
			
			color[j++] = 0.1f+(.2f*i);;
			color[j++] = 0.9f-(0.2f*i);
			color[j++] = 0.5f;
		}
		mesh.setBuffer(VertexBuffer.Type.Position, 2, BufferUtils.createFloatBuffer(points));
		mesh.setBuffer(Type.Color, 3, BufferUtils.createFloatBuffer(color));
		mesh.setMode(Mesh.Mode.Points);
		mesh.updateCounts();
	}
	
	public Hexamap(List<Axial> coordinates,String name) {
		super(name,new Mesh());
		
		float[] points = new float[coordinates.size() * 2];
		float[] color = new float[coordinates.size() * 3];
		int i = 0,j=0;
		for (Coordinate c : coordinates) {
			points[i++] = c.getX();
			points[i++] = c.getY();
			
			color[j++] = 0.1f+(.2f*i);;
			color[j++] = 0.9f-(0.2f*i);
			color[j++] = 0.5f;
		}
		mesh.setBuffer(VertexBuffer.Type.Position, 2, BufferUtils.createFloatBuffer(points));
		mesh.setBuffer(Type.Color, 3, BufferUtils.createFloatBuffer(color));
		mesh.setMode(Mesh.Mode.Points);
		mesh.updateCounts();
	}
}

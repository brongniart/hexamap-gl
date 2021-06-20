package hexamap.gl;

import java.util.Set;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;

import hexamap.coordinates.Axial;
import hexamap.regions.Hexagon;
import hexamap.regions.Rhombus;

public class HierarchialGrid extends Node {
	
	// private SimpleGrid highlights;
	private AssetManager assetManager;
	SimpleGrid[] levels = new SimpleGrid[4]; 

	public HierarchialGrid(String name, AssetManager _assetManager) {
		super(name);
		assetManager = _assetManager;

		// highlights = new SimpleGrid(_highlights, "highlights");
		// highlights.setMaterial(new Material(assetManager,
		// "Materials/Geom/Hexamap/SimpleGrid.j3md"));
		// attachChild(highlights);

		createGrid();
		addControl(new GridLodControl());
	}

	private void createGridLevel(int level,int sizeGrid, float sizeHexagon, ColorRGBA color, boolean pointy) {
		levels[level] = new SimpleGrid(new Hexagon<Axial>(sizeGrid, Axial.class), "Level_"+level);
		
		Material m_level = new Material(assetManager, "Materials/Geom/Hexamap/SimpleGrid.j3md");
		m_level.setFloat("Size", sizeHexagon);
		m_level.setColor("Color", color);
		m_level.setBoolean("Pointy", pointy);
		
		levels[level].setMaterial(m_level);
		attachChild(levels[level]);
	}
	
	private void createGrid() {
		float inc = 1; //(float) Math.sqrt(3);
		boolean pointy = false;
		boolean switchOrientation = false;

		int size = 180;

		float colorShift = 0.95f / levels.length;
		ColorRGBA color = new ColorRGBA(0.05f, 0.05f, 0.05f, 1.0f);
		ColorRGBA shitf = new ColorRGBA(colorShift, colorShift, colorShift, 1.0f);
		
		createGridLevel(0,size,inc,color,false);
		
		size = Math.max(0,size/3);
		if (pointy) { // || switchOrientation) {
			inc *= (float) Math.pow(Math.sqrt(3),3);
		} else {
			inc *= 9.0f;
		}
		color = color.add(shitf);
		
		for (int level = 1; level < levels.length; level++) {
			createGridLevel(level, size, inc,color, pointy);
			
			size = Math.max(0,size/3);
			if (pointy || switchOrientation) {
				inc *= (float) Math.pow(Math.sqrt(3),3);
			} else {
				inc *= 9.0f;
			}
			color = color.add(shitf);
			if (switchOrientation) {
				pointy = !pointy;
			}
		}
	}

	public class GridLodControl extends AbstractControl {
		private float dist;

		@Override
		public void setSpatial(Spatial spatial) {
			super.setSpatial(spatial);
			if (!(spatial instanceof HierarchialGrid)) {
				throw new IllegalStateException("This control only accept HierarchialGrid spacial");
			}
		}

		@Override
		protected void controlUpdate(float tpf) {
			for (int level = 0; level < levels.length; level++) {
				Geometry g_level = levels[level];
				float size = g_level.getMaterial().getParamValue("Size");
				
				//System.err.println(size);
				/*if (size< dist/50) {
					g_level.removeFromParent();
				} else if (size > dist) {
					g_level.removeFromParent();
				} else {
					g_level.removeFromParent();
					attachChild(g_level);
				}*/
			}
		}

		@Override
		protected void controlRender(RenderManager rm, ViewPort vp) {
			int width = rm.getCurrentCamera().getWidth();
			dist = rm.getCurrentCamera().distanceToNearPlane(getWorldTranslation());
		}
	}

}

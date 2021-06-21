package hexamap.gl;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
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
	int pow_max = 8;
	int pow_min = 1;
	SimpleGrid[] levels = new SimpleGrid[1+pow_max/(2*pow_min)];

	public HierarchialGrid(String name, AssetManager _assetManager) {
		super(name);
		assetManager = _assetManager;

		// highlights = new SimpleGrid(_highlights, "highlights");
		// highlights.setMaterial(new Material(assetManager,
		// "Materials/Geom/Hexamap/SimpleGrid.j3md"));
		// attachChild(highlights);

		createGrid();
		//addControl(new GridLodControl());
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
		
		float sizeHexagon = 1;
		int sizeGrid = (int) Math.pow(2, pow_max);
		
		boolean pointy = true;
		boolean switchOrientation = false;
		float inc = (int) Math.pow(2, pow_min);
		
		float colorShift = 0.95f / (float) levels.length;
		ColorRGBA color = new ColorRGBA(0.05f, 0.05f, 0.05f,  0.05f);
		ColorRGBA shitf = new ColorRGBA(colorShift, colorShift, colorShift, 1.0f);
		
		createGridLevel(0,sizeGrid,sizeHexagon,color,false);
		
		sizeGrid = (int) Math.max(0,sizeGrid/inc);
		
		if (pointy) {
			sizeHexagon *= (float) Math.sqrt(3)*inc;
		} else {
			sizeHexagon *= 3*inc;
		}
		color = color.add(shitf);

		color = color.add(shitf);
		
		for (int level = 1; level < levels.length; level++) {
			if (level%2==0) {
				createGridLevel(level, sizeGrid, sizeHexagon,color, pointy);
			}
			color = shitf.add(shitf);
			sizeGrid /=  pow_max/2;
			if (switchOrientation) { 
				sizeHexagon *= (float) Math.sqrt(3)*inc;
			} else {
				sizeHexagon *= inc;
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
			
			for (int level =  1; level < levels.length; level+=2) {
				Geometry p_level = levels[level-1];
				float p_size = p_level.getMaterial().getParamValue("Size");
				
				Geometry g_level = levels[level];
				float size = g_level.getMaterial().getParamValue("Size");
				
				if (dist < size*4*(levels.length-level/2)) {
					attachChild(p_level);
				} else if (dist >= size*4*(levels.length-level/2)) {
					p_level.removeFromParent();
				}
			}
			for (int level =  1; level < levels.length; level+=2) {
				Geometry p_level = levels[level-1];
				//p_level.removeFromParent();
			}
		}

		@Override
		protected void controlRender(RenderManager rm, ViewPort vp) {
			int width = rm.getCurrentCamera().getWidth();
			dist = rm.getCurrentCamera().distanceToNearPlane(getWorldTranslation());
		}
	}

}

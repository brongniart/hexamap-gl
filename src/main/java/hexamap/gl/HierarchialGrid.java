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
import hexamap.regions.base.Hexagon;

public class HierarchialGrid extends Node {
	
	private AssetManager assetManager;
	private SimpleGrid[] levels = new SimpleGrid[3];
	private int[] sizeGridLevel = new int[3];
	private float[] sizeHexagonLevel = new float[3];
	private Material[] materialLevel = new Material[3];
	
	public int sizeStart = 36*4;
	public float inc = 6;
	public boolean pointy = false;
	public boolean switchOrientation = false;
	
	public HierarchialGrid(String name, AssetManager _assetManager) {
		super(name);
		assetManager = _assetManager;
		createGrid();
		
		addControl(new GridLodControl(levels));
	}

	private void createGridLevel(int level, int sizeGrid, float sizeHexagon, ColorRGBA color, boolean pointy) {
		System.out.println("Level:" + level + ", size:" + sizeGrid);
		sizeHexagonLevel[level] = sizeHexagon;
		sizeGridLevel[level] = sizeGrid;
		
		materialLevel[level] = new Material(assetManager, "Materials/Geom/Hexamap/SimpleGrid.j3md");
		materialLevel[level].setFloat("Size", sizeHexagon);
		materialLevel[level].setColor("Color", color);
		materialLevel[level].setBoolean("Pointy", pointy);
	

		levels[level] = new SimpleGrid(new Hexagon(sizeGridLevel[level], new Axial()), "Level_" + level);
		levels[level].setMaterial(materialLevel[level]);
	}

	private void createGrid() {

		float sizeHexagon = 1;
		int sizeGrid = sizeStart;
		
		float colorShift = 0.95f / (float) levels.length;
		ColorRGBA color = new ColorRGBA(0.05f, 0.05f, 0.05f, 0.05f);
		ColorRGBA shitf = new ColorRGBA(colorShift, colorShift, colorShift, 1.0f);

		createGridLevel(0, sizeGrid, sizeHexagon, color, false);

		sizeGrid = (int) Math.max(0, sizeGrid / inc);

		if (pointy) {
			sizeHexagon *= (float) Math.sqrt(3) * inc / 2;
		} else {
			sizeHexagon *= inc;
		}
		color = color.add(shitf);

		for (int level = 1; level < levels.length; level++) {
			createGridLevel(level, sizeGrid, sizeHexagon, color, pointy);
			color = color.add(shitf);

			if (switchOrientation) {
				sizeHexagon *= (float) Math.sqrt(3) * inc;
				sizeGrid /= (int) Math.sqrt(3) * inc;
			} else {
				sizeHexagon *= inc;
				sizeGrid /= inc;
			}

			if (switchOrientation) {
				pointy = !pointy;
			}
		}
	}

	private class GridLodControl extends AbstractControl {
		
		private SimpleGrid[] levels;
		private float dist;
		
		public GridLodControl(SimpleGrid[] _levels) {
			levels=_levels;
		}
		
		@Override
		public void setSpatial(Spatial spatial) {
			if (!(spatial instanceof HierarchialGrid)) {
				throw new IllegalStateException("This control only accept HierarchialGrid spacial");
			}
			super.setSpatial(spatial);
		}

		@Override
		protected void controlUpdate(float tpf) {
			System.err.println(dist);
			for (int level = 0; level < levels.length; level += 1) {
				if (levels[level]!=null) {
					Geometry p_level = levels[level];
					p_level.removeFromParent();
				}
			}
			
			for (int level = 0; level < levels.length; level += 1) {
				if (dist/250<sizeHexagonLevel[level]) {
					System.out.println(sizeHexagonLevel[level]);
					
					attachChild(levels[level]);
				}
			}
		}

		@Override
		protected void controlRender(RenderManager rm, ViewPort vp) {
			//int width = rm.getCurrentCamera().getWidth();
			dist = rm.getCurrentCamera().distanceToNearPlane(getWorldTranslation());
		}
	}

}

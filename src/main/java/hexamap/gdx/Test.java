/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hexamap.gdx;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 *
 */
public class Test implements ApplicationListener {

    private Texture image;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    
    private final float SIZE_X = 800;
    private final float SIZE_Y = 480;

    public Test() {
    }
    
    @Override
    public void create() {
        image = new Texture(Gdx.files.internal("drop.png"));

        camera = new OrthographicCamera();
        camera.setToOrtho(false, SIZE_X, SIZE_Y);
        batch = new SpriteBatch();
    }

    @Override
    public void resize(int i, int i1) {
        //throw new UnsupportedOperationException("Todo");
    }

    @Override
    public void render() {
        camera.update();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(image, (SIZE_X-image.getHeight())/2, (SIZE_Y-image.getWidth())/2);
        batch.end();
    }

    @Override
    public void pause() {
        //throw new UnsupportedOperationException("Todo: pause");
    }

    @Override
    public void resume() {
        //throw new UnsupportedOperationException("Todo: resume");
    }

    @Override
    public void dispose() {
        image.dispose();
        batch.dispose();
    }
}

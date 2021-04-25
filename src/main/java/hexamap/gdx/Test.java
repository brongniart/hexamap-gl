/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hexamap.gdx;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 *
 */
public class Test implements ApplicationListener {

    private Texture image;
    private OrthographicCamera camera;
    private SpriteBatch batch;

    public Test() {
    }

    @Override
    public void create() {
        image = new Texture(Gdx.files.internal("glop.png"));

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        batch = new SpriteBatch();
    }

    @Override
    public void resize(int i, int i1) {
        throw new UnsupportedOperationException("Todo");
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);  //clears the buffer 
        camera.update();

        batch.setProjectionMatrix(camera.combined);
        batch.draw(image, 0, 0);
        batch.end();
    }

    @Override
    public void pause() {
        throw new UnsupportedOperationException("Todo");
    }

    @Override
    public void resume() {
        throw new UnsupportedOperationException("Todo");
    }

    @Override
    public void dispose() {
        image.dispose();
        batch.dispose();
    }
}

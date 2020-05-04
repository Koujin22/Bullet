package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;

abstract class GameObject {

    BodyDef bodyDef;

    Animation<TextureRegion> animacion;
    float x, y;
    final Vector2 size;
    float stateTime;
    SpriteBatch batch;
    Body body;
    float ppm;
    Fixture fixture;

    GameObject(Vector2 size, float x, float y, Texture textura, SpriteBatch batch){
        this.size = size;
        this.batch = batch;
        this.x = x;
        this.y = y;
        TextureRegion region = new TextureRegion(textura);
        TextureRegion[][] texturaPersonaje = region.split((int)size.x,(int)size.y);
        TextureRegion[] animationFrames = new TextureRegion[texturaPersonaje.length*texturaPersonaje[0].length];
        int index = 0;
        for (int i = 0; i < texturaPersonaje.length; i++) {{
            for (int j = 0; j < texturaPersonaje[0].length; j++) {
                animationFrames[index++] = texturaPersonaje[i][j];
            }
        }

        }
        animacion = new Animation<>( 0.15f, animationFrames);
        animacion.setPlayMode(Animation.PlayMode.LOOP);

        this.bodyDef = new BodyDef();
        stateTime = 0f;
    }

    GameObject(Vector2 size, float x, float y, Texture textura, SpriteBatch batch, float ppm){
        this.ppm = ppm;
        this.batch = batch;
        this.x = x;
        this.y = y;
        TextureRegion region = new TextureRegion(textura);
        TextureRegion[][] texturaPersonaje = region.split((int)size.x,(int)size.y);
        TextureRegion[] animationFrames = new TextureRegion[texturaPersonaje.length*texturaPersonaje[0].length];
        int index = 0;
        for (int i = 0; i < texturaPersonaje.length; i++) {
            for (int j = 0; j < texturaPersonaje[0].length; j++) {
                animationFrames[index++] = texturaPersonaje[i][j];
            }
        }
        this.size = new Vector2(size.x/ppm, size.y/ppm);

        animacion = new Animation<>( 0.15f, animationFrames);
        animacion.setPlayMode(Animation.PlayMode.LOOP);
        this.bodyDef = new BodyDef();
        stateTime = 0f;
    }

    void render(float delta){
        stateTime += delta;

        batch.draw(animacion.getKeyFrame(stateTime, true), x-.05f, y, size.x, size.y);

    }

    float getX() {
        return x;
    }

    void setX(float x) {
        this.x = x;
    }

    BodyDef getBodyDef() {
        return bodyDef;
    }

    void setBodyDef(BodyDef bodyDef) {
        this.bodyDef = bodyDef;
    }

    Animation<TextureRegion> getAnimacion() {
        return animacion;
    }

    void setAnimacion(Animation<TextureRegion> animacion) {
        this.animacion = animacion;
    }

    float getY() {
        return y;
    }

    void setY(float y) {
        this.y = y;
    }

    Vector2 getSize() {
        return size;
    }

    Body getBody() {
        return body;
    }

    void setBody(Body body) {
        this.body = body;
    }

    Fixture getFixture() {
        return fixture;
    }

    void setFixture(Fixture fixture) {
        this.fixture = fixture;
    }

    public abstract void start();

}

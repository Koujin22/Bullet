package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;

class Personaje extends GameObject {


    private EstadoMovimiento estado = EstadoMovimiento.QUIETO;
    private float max_velocity = 15;
    private float KnobY = 0;
    private float start, curr, total;
    private int cuenta;

    Personaje(Vector2 size, float x, float y, Texture textura, SpriteBatch batch, float max_velocity) {
        super(size, x, y, textura, batch);
        this.max_velocity = max_velocity;
        set2DBox();
    }

    Personaje(Vector2 size, float x, float y, Texture textura, SpriteBatch batch, float ppm, float max_velocity) {
        super(size, x, y, textura, batch, ppm);
        this.max_velocity = max_velocity;
        set2DBox();
    }

    private void set2DBox(){
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x+size.x/2, y+size.y/2);
    }

    void updateKKnobY(float y){
        this.KnobY  = y;
    }

    void updatePos(float delta, float knoby){
        if(estado == EstadoMovimiento.CAMINANDO){
            this.x += max_velocity* delta;
            this.y += max_velocity * knoby*delta;

            body.setLinearVelocity(max_velocity, max_velocity * knoby);
        }
    }

    @Override
    void render(float delta){
        super.render(delta);

    }

    @Override
    public void start() {
        estado = EstadoMovimiento.CAMINANDO;
    }
}

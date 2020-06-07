package mx.itesm.BulletTimeReloaded;

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
    private boolean fall = false, slow = false, invert = false;

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
            float velx = max_velocity;
            float vely = max_velocity * knoby;
            if(fall) vely -= max_velocity+.5f;
            if(slow) velx *= .8f;
            if(invert) vely = 0-vely;
            this.x += velx * delta;
            this.y += vely * delta;
            body.setLinearVelocity(velx, vely );
        }
    }

    void setFall(boolean fall){
        this.fall = fall;
    }

    void setSlow(boolean slow){
        this.slow = slow;
    }

    @Override
    void render(float delta){
        super.render(delta);

    }

    @Override
    public void start() {
        estado = EstadoMovimiento.CAMINANDO;
    }

    public boolean isSlow() {
        return slow;
    }

    public void setInvert(boolean b) {
        invert = b;
    }
}

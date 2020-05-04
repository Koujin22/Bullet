package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

class Nivel extends Pantalla implements InputProcessor {

    private final float ANCHO = Juego.ANCHO;
    private final float ALTO = Juego.ALTO;

    private final float PPM = 200f;

    private EstadoMovimiento estado = EstadoMovimiento.QUIETO;

    private Personaje bala;
    private Mapa map;

    private Stage escena2D, escenaHUD;
    private OrthographicCamera camera2D, cameraHUD;
    private Viewport vista2D, vistaHUD;

    private World world;
    private Box2DDebugRenderer debug2d;
    private InputMultiplexer inputMultiplexer;
    private float knobYPer = 0;

    private Touchpad pad;
    private float max_velocity;
    private boolean disposing = false;

    Nivel(Juego juego, TiledMap tiledMap, float max_velocity) {
        super(juego);
        this.max_velocity = max_velocity;
        setViews();
        crearHUD();
        addPlayer();
        setWorld();
        setInputProcesors();
        //TODO: assetmanager

        this.map = new Mapa((SpriteBatch) escena2D.getBatch(), tiledMap, world, camera2D, PPM);
    }

    private void setWorld(){



        Box2D.init();

        world = new World(new Vector2(0,0), true);
        debug2d = new Box2DDebugRenderer();
        world.setContactListener(new ContactListener(){

            @Override
            public void beginContact(Contact contact) {

                switch ((String)contact.getFixtureB().getUserData()){
                    case "looser":
                        endLevel(false);
                        break;
                    case "winner":
                        endLevel(true);
                        break;
                    case "fall":
                        bala.setFall(true);
                        break;
                    case "slow":
                        bala.setSlow(true);
                        break;
                    default:
                        break;
                }



            }

            @Override
            public void endContact(Contact contact) {

                switch ((String)contact.getFixtureB().getUserData()){
                    case "fall":
                        bala.setFall(false);
                        break;
                    case "slow":
                        bala.setSlow(false);
                        break;
                    default:
                        break;
                }

            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

                System.out.println("contacto");
            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

                System.out.println("contacto");
            }
        });

        addPlyToWorld();

    }

    private void addPlyToWorld(){
        bala.setBody(world.createBody(bala.getBodyDef()));
        PolygonShape box = new PolygonShape();
        box.setAsBox(bala.getSize().x/2, bala.getSize().y/2);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = box;
        fixtureDef.isSensor = true;
        fixtureDef.density = 0f;
        fixtureDef.friction = 0f;
        fixtureDef.restitution = 0.6f;
        bala.setFixture(bala.getBody().createFixture(fixtureDef));
        box.dispose();
    }

    private void setSound(){
        //audioFondo = manager.get("musicaNueva.mp3");
        //audioFondo.setLooping(true); //infinita
        //if(on_off) {
        //    audioFondo.play();
        //}
        //efectos
        //efecto = manager.get("moneda.mp3");
        //TODO
    }

    private void addPlayer(){
        Texture ply = new Texture("spritePrincipal3.png");
        bala = new Personaje(new Vector2(64,64), 100/PPM, 350/PPM,ply, (SpriteBatch) escena2D.getBatch(), PPM, max_velocity);
    }

    private void setInputProcesors(){

        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(this);
        inputMultiplexer.addProcessor(escenaHUD);
        Gdx.input.setInputProcessor(inputMultiplexer);

    }

    private void setViews(){
        camera2D = new OrthographicCamera();
        camera2D.setToOrtho(false, ANCHO, ALTO);
        camera2D.position.set(ANCHO/2, ALTO/2,0);
        camera2D.update();

        cameraHUD = new OrthographicCamera();
        cameraHUD.setToOrtho(false, ANCHO, ALTO);
        cameraHUD.position.set(ANCHO/2, ALTO/2, 0);
        cameraHUD.update();

        vista2D = new ExtendViewport(ANCHO/PPM,ALTO/PPM, camera2D);
        vistaHUD = new ExtendViewport(ANCHO, ALTO, cameraHUD);
        escenaHUD = new Stage(vistaHUD);
        escena2D = new Stage(vista2D);
    }

    private void crearHUD(){

        Skin skin = new Skin();
        skin.add("fondo", new Texture("Joystick.png"));
        skin.add("boton", new Texture("SmallHandle.png"));

        Touchpad.TouchpadStyle estilo = new Touchpad.TouchpadStyle();
        estilo.background = skin.getDrawable("fondo");
        estilo.knob = skin.getDrawable("boton");

        //crear pad
        pad = new Touchpad(64,estilo);
        pad.setBounds(16,16,256,256);
        pad.setColor(1,1,1,0.8f);
        pad.setSize(pad.getWidth(), pad.getHeight());

        //eventos
        pad.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Touchpad pad = (Touchpad)actor;
                knobYPer = pad.getKnobPercentY();

            }
        });


        escenaHUD.addActor(pad);

    }

    private void updateCameraPos(float delta){
        if(estado == EstadoMovimiento.CAMINANDO){
            float velx = max_velocity*delta;
            if(bala.isSlow()) velx *= .8f;
            cameraHUD.translate(velx, 0);
            camera2D.translate(velx, 0);
            pad.setPosition(pad.getX()+velx, pad.getY());
        }
    }

    private void endLevel(boolean win){
        if(win) System.out.println("ganaste");
        else System.out.println("perdiste");
        juego.initPantallas(false);

    }




    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        borrarPantalla(.11f, .42f, .60f);
        escena2D.getBatch().setProjectionMatrix(camera2D.combined);
        escenaHUD.getBatch().setProjectionMatrix(cameraHUD.combined);

        bala.updatePos(delta, knobYPer);
        updateCameraPos(delta);
        camera2D.update();
        cameraHUD.update();
        map.render(delta);

        escena2D.getBatch().begin();
        bala.render(delta);
        escena2D.getBatch().end();

        escena2D.draw();
        escenaHUD.draw();


        if(!disposing) {
            debug2d.render(world, camera2D.combined);
            world.step(1 / 60f, 6, 2);
        } else if(!world.isLocked()){
            world.dispose();
        }
    }

    public float getKnobYPer(){
        return  knobYPer;
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        //TODO: assetManager dispose
        this.disposing = true;
        map.dispose();
        escenaHUD.dispose();
        escena2D.dispose();
        debug2d.dispose();

    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.SPACE){
            bala.start();
            estado = EstadoMovimiento.CAMINANDO;
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}

package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

class Mapa {


    private OrthographicCamera  scrollingCamera;

    private TiledMapTileLayer collisionObjectLayer;
    private float tilesize;
    private TiledMap mapa;
    private OrthogonalTiledMapRenderer rendererMapa;

    private MapObjects objects;


    Mapa(SpriteBatch batch, TiledMap mapatmp, World world, OrthographicCamera scrollingCamera, float ppm) {



        this.scrollingCamera = scrollingCamera;

        mapa = new TmxMapLoader().load("mapaTutorial.tmx");



        objects = mapa.getLayers().get(1).getObjects();

        for (RectangleMapObject rectangleMapObject : objects.getByType(RectangleMapObject.class)){
            Rectangle rectangle = rectangleMapObject.getRectangle();
            BodyDef groundBodyDef = new BodyDef();
            groundBodyDef.position.set((rectangle.getX()+rectangle.width/2)*2.5f/ppm, (rectangle.getY()+rectangle.height/2)*2.5f/ppm);
            Body groundBody = world.createBody(groundBodyDef);
            PolygonShape groundBox = new PolygonShape();
            groundBox.setAsBox((rectangle.getWidth()/2)*2.5f/ppm, (rectangle.getHeight()/2)*2.5f/ppm);
            groundBody.createFixture(groundBox, 0.0f);
            groundBox.dispose();
        }

        rendererMapa = new OrthogonalTiledMapRenderer(mapa,2.5f/ppm);






    }


    void render(float delta){

        rendererMapa.setView(scrollingCamera );
        rendererMapa.render();

    }
}

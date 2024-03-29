package mx.itesm.BulletTimeReloaded;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

class Mapa {


    private OrthographicCamera  scrollingCamera;
    private TiledMap mapa;
    private OrthogonalTiledMapRenderer rendererMapa;

    private MapObjects objects;


    Mapa(SpriteBatch batch, TiledMap mapatmp, World world, OrthographicCamera scrollingCamera, float ppm, int nivelActual) {



        this.scrollingCamera = scrollingCamera;

        mapa = mapatmp;

        System.out.println(nivelActual);
        float scaleFactor = 2.5f;

        for (MapLayer layer : mapa.getLayers()){
            if(layer.getName().equals("escenario")) continue;
            objects = layer.getObjects();

            for (RectangleMapObject rectangleMapObject : objects.getByType(RectangleMapObject.class)){
                Rectangle rectangle = rectangleMapObject.getRectangle();
                BodyDef groundBodyDef = new BodyDef();
                groundBodyDef.position.set((rectangle.getX()+rectangle.width/2)*scaleFactor/ppm, (rectangle.getY()+rectangle.height/2)*scaleFactor/ppm);
                Body groundBody = world.createBody(groundBodyDef);
                PolygonShape groundBox = new PolygonShape();
                groundBox.setAsBox((rectangle.getWidth()/2)*scaleFactor/ppm, (rectangle.getHeight()/2)*scaleFactor/ppm);
                groundBody.createFixture(groundBox, 0.0f);

                groundBody.getFixtureList().get(0).setUserData(layer.getName());
                groundBox.dispose();

            }
        }


        rendererMapa = new OrthogonalTiledMapRenderer(mapa, scaleFactor / ppm, batch);

    }


    void render(float delta){

        rendererMapa.setView(scrollingCamera );
        rendererMapa.render();

    }

    void dispose(){
        mapa.dispose();
        rendererMapa.dispose();

    }

}

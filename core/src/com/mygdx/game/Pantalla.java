package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Representa el comportamiento genérico de cualquier pantalla que forma
 * parte del juego
 */
class Pantalla implements Screen
{

    private final float ANCHO = Juego.ANCHO;;
    private final float ALTO = Juego.ALTO;;

    // Atributos disponibles solo en las subclases
    // Todas las pantallas tienen una cámara y una vista
    private OrthographicCamera camara;
    private Viewport vista;

    private final Juego juego;
    private final Texture texturaFondo;
    private final Stage stage;
    private final ArrayList<Texto> texto = new ArrayList<>();


    // Constructor, inicializa los objetos camara, vista, batch
    Pantalla(Juego juego, Texture texturaFondo) {

        // Crea la cámara con las dimensiones del mundo
        camara = new OrthographicCamera(ANCHO, ALTO);
        // En el centro de la pantalla. (x,y) de la cámara en el centro geométrico
        camara.position.set(ANCHO / 2, ALTO / 2, 0);
        camara.update();
        // La vista que escala los elementos gráficos
        vista = new StretchViewport(ANCHO, ALTO, camara);


        this.juego = juego;
        this.texturaFondo = texturaFondo;
        this.stage = new Stage(vista);
    }

    // Borra la pantalla con fondo negro
    private void borrarPantalla() {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    void createBtn(Texture texturaBoton, Texture textureBtnPress, float x, float y, ClickListener listener){
        TextureRegionDrawable btn = new TextureRegionDrawable(new TextureRegion((texturaBoton)));
        TextureRegionDrawable btnPress = new TextureRegionDrawable(new TextureRegion((textureBtnPress)));

        ImageButton btnImg = new ImageButton(btn, btnPress);
        btnImg.setPosition(x-btnImg.getWidth()/2, y);

        btnImg.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                System.out.println("clicke");
            }
        });

        this.stage.addActor(btnImg);

    }

    void createBtn(ImageButton.ImageButtonStyle style, float x, float y, ClickListener listener){
        ImageButton btnImg = new ImageButton(style);
        btnImg.setName("test");
        btnImg.setPosition(x-btnImg.getWidth()/2, y);

        btnImg.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                System.out.println("clicke");
                System.out.println(event);
            }
        });


        this.stage.addActor(btnImg);
    }

    void creacionTerminada(){
        Gdx.input.setInputProcessor(this.stage);
    }

    void addTexto(String archivo, String mensaje, float x, float y){
        texto.add(new Texto(archivo,
                mensaje,
                x,
                y,
                stage.getBatch()));

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        System.out.println("render");
        borrarPantalla();
        stage.getBatch().setProjectionMatrix(camara.combined);
        stage.getBatch().begin();
        stage.getBatch().draw(texturaFondo, 0, 0);
        for (int i = 0; i<texto.size(); i++){
            texto.get(i).render();
        }
        stage.getBatch().end();
        stage.draw();
    }

    // Notifica a la vista, que el tamaño de la pantalla física ha cambiado
    @Override
    public void resize(int width, int height) {
        vista.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
        for (int i = 0; i<texto.size(); i++){
            texto.get(i).dispose();
        }
        texturaFondo.dispose();
    }

}

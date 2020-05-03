package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.io.FileWriter;

public class Juego extends com.badlogic.gdx.Game {


	private Pantalla pantallaMenu;
	private Pantalla pantallaAcerca;
	private Pantalla pantallaOpciones;

	private AssetManager manager;


	static final float ANCHO = 1280;
	static final float ALTO = 720;
	
	@Override
	public void create () {
		manager = new AssetManager();
		Opciones.CargarOpciones();
		initPantallas();

		setScreen(pantallaMenu);
	}

	private void initPantallas(){
		agregarAssetsPantallas();
		createMenu();
		createAcerca();
		createOptiones();
	}

	private void agregarAssetsPantallas(){
		manager.load("fondoSpace.jpg", Texture.class);
		manager.load("btnJugar.png", Texture.class);
		manager.load("btnJugarPresionado.png", Texture.class);
		manager.load("btnAcercaDe.png", Texture.class);
		manager.load("btnAcercaDePresionado.png", Texture.class);
		manager.load("btnOpciones.png", Texture.class);
		manager.load("btnOpcionesPresionado.png", Texture.class);
		manager.load("btnOpcionesPresionado.png", Texture.class);
		manager.load("btnAtras.png", Texture.class);
		manager.load("btnAtrasPresionado.png", Texture.class);
		manager.load("btnMusicaSi.png", Texture.class);
		manager.load("btnMusicaNo.png", Texture.class);
		manager.finishLoading();

	}

	private void createMenu(){
		pantallaMenu = new Pantalla(this, manager.get("fondoSpace.jpg", Texture.class));

		pantallaMenu.createBtn(manager.get("btnJugar.png", Texture.class),
				manager.get("btnJugarPresionado.png", Texture.class),
				ANCHO/2,
				6*ALTO/10,
				new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y) {
						super.clicked(event, x, y);
						//juego.setScreen(new PantallaJuego(juego));
					}
				});

		pantallaMenu.createBtn(manager.get("btnAcercaDe.png", Texture.class),
				manager.get("btnAcercaDePresionado.png", Texture.class),
				ANCHO/2,
				2*ALTO/20,
				new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y) {
						super.clicked(event, x, y);
						System.out.println("acerca");
					}
				});

		pantallaMenu.createBtn(manager.get("btnOpciones.png", Texture.class),
				manager.get("btnOpcionesPresionado.png", Texture.class),
				ANCHO/2,
				7*ALTO/20,
				new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y) {
						super.clicked(event, x, y);
					}
				});

		pantallaMenu.addTexto("fuenteTecno.fnt", "Bullet Time Reloaded", ANCHO/2, ALTO-ALTO/12);

		pantallaMenu.creacionTerminada();

	}

	private void createAcerca(){
		pantallaAcerca = new Pantalla(this, manager.get("fondoSpace.jpg", Texture.class));

		pantallaAcerca.createBtn(manager.get("btnAtras.png", Texture.class),
				manager.get("btnAtrasPresionado.png", Texture.class),
				ANCHO/2,
				2*ALTO/10,
				new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y) {
						super.clicked(event, x, y);
						setScreen(pantallaMenu);
					}
				});

		pantallaAcerca.addTexto("fuenteTecno.fnt",
				"Acerca De",
				ANCHO/2,
				ALTO-ALTO/12);
		pantallaAcerca.addTexto("fuenteTecnoChica.fnt",
				"Desarrolladores",
				ANCHO/2,
				ALTO-ALTO/4);
		pantallaAcerca.addTexto("fuenteTecnoChica.fnt",
				"Ivan Honc, Bruno Vazquez, Jesus Alcala y Emiliano Heredia",
				ANCHO/2,
				ALTO-5*ALTO/16);
        pantallaAcerca.addTexto("fuenteTecnoChica.fnt",
				"Desarrollado para la clase de desarrollo de videojuegos.",
				ANCHO/2,
				ALTO-7*ALTO/16);
        pantallaAcerca.addTexto("fuenteTecnoChica.fnt",
				"Semestre Febrero-Junio 2020",
				ANCHO/2,
				ALTO-9*ALTO/16);

		pantallaAcerca.creacionTerminada();

	}

	void createOptiones(){

		pantallaOpciones = new Pantalla(this, manager.get("fondoSpace.jpg", Texture.class));

		pantallaOpciones.createBtn(manager.get("btnAtras.png", Texture.class),
				manager.get("btnAtrasPresionado.png", Texture.class),
				ANCHO/2,
				2*ALTO/10,
				new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y) {
						super.clicked(event, x, y);
						setScreen(pantallaMenu);
					}
				});

		ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle(
				new TextureRegionDrawable(new TextureRegion((manager.get("btnMusicaSi.png", Texture.class)))),
				new TextureRegionDrawable(new TextureRegion((manager.get("btnMusicaNo.png", Texture.class)))),
				new TextureRegionDrawable(new TextureRegion((manager.get("btnJugar.png", Texture.class)))),
				new TextureRegionDrawable(new TextureRegion((manager.get("btnAcercaDe.png", Texture.class)))),
				new TextureRegionDrawable(new TextureRegion((manager.get("btnOpciones.png", Texture.class)))),
				new TextureRegionDrawable(new TextureRegion((manager.get("btnAtras.png", Texture.class))))

		);

		pantallaOpciones.createBtn(style,
				ANCHO/2,
				ALTO / 2,
				new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y) {
						super.clicked(event, x, y);
					}
				});


		pantallaOpciones.creacionTerminada();
	}


}

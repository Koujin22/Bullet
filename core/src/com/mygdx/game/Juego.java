package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.io.FileWriter;

public class Juego extends com.badlogic.gdx.Game {


	private PantallaMenu pantallaMenu;
	private PantallaMenu pantallaAcerca;
	private PantallaMenu pantallaOpciones;

	private Nivel currentLevel;

	private AssetManager manager;


	static final float PPM = 32f;
	static final float ANCHO = 1280;
	static final float ALTO = 704;
	
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
		pantallaMenu.setActiveScreen();
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

	private void freeRecursosPantallas(){
		manager.clear();
		pantallaMenu.dispose();
		pantallaOpciones.dispose();
		pantallaAcerca.dispose();
	}

	private void createMenu(){
		pantallaMenu = new PantallaMenu(this, manager.get("fondoSpace.jpg", Texture.class));

		pantallaMenu.createBtn(manager.get("btnJugar.png", Texture.class),
				manager.get("btnJugarPresionado.png", Texture.class),
				ANCHO/2,
				6*ALTO/10,
				new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y) {
						super.clicked(event, x, y);
						//juego.setScreen(new PantallaJuego(juego));
						iniciarJuego();
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
						pantallaAcerca.setActiveScreen();
						setScreen(pantallaAcerca);

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
						pantallaOpciones.setActiveScreen();
						setScreen(pantallaOpciones);
					}
				});

		pantallaMenu.addTexto("fuenteTecno.fnt", "Bullet Time Reloaded", ANCHO/2, ALTO-ALTO/12);


	}

	private void createAcerca(){
		pantallaAcerca = new PantallaMenu(this, manager.get("fondoSpace.jpg", Texture.class));

		pantallaAcerca.createBtn(manager.get("btnAtras.png", Texture.class),
				manager.get("btnAtrasPresionado.png", Texture.class),
				ANCHO/2,
				2*ALTO/10,
				new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y) {
						super.clicked(event, x, y);
						getScreen().hide();
						pantallaMenu.setActiveScreen();
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


	}

	void createOptiones(){

		pantallaOpciones = new PantallaMenu(this, manager.get("fondoSpace.jpg", Texture.class));

		pantallaOpciones.createBtn(manager.get("btnAtras.png", Texture.class),
				manager.get("btnAtrasPresionado.png", Texture.class),
				ANCHO/2,
				2*ALTO/10,
				new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y) {
						super.clicked(event, x, y);
						getScreen().hide();
						pantallaMenu.setActiveScreen();
						setScreen(pantallaMenu);
					}
				});

		ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
		if(Opciones.sonido) {
			style.imageChecked = new TextureRegionDrawable(new TextureRegion((manager.get("btnMusicaNo.png", Texture.class))));
			style.imageUp = new TextureRegionDrawable(new TextureRegion((manager.get("btnMusicaSi.png", Texture.class))));
		} else {
			style.imageUp = new TextureRegionDrawable(new TextureRegion((manager.get("btnMusicaNo.png", Texture.class))));
			style.imageChecked = new TextureRegionDrawable(new TextureRegion((manager.get("btnMusicaSi.png", Texture.class))));
		}

		pantallaOpciones.createBtn(style,
				ANCHO/2,
				ALTO / 2,
				new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y) {
						super.clicked(event, x, y);
						((ImageButton)event.getStage().getActors().get(event.getButton())).setChecked(Opciones.toggleSonido());
						Opciones.SaveOptions();
					}
				});


	}

	void iniciarJuego(){
		freeRecursosPantallas();
		currentLevel = new Nivel(this, new TiledMap(), 1f);
		setScreen(currentLevel);

	}

}

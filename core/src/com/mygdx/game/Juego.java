package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.io.File;
import java.io.FileWriter;

public class Juego extends com.badlogic.gdx.Game {


	private PantallaMenu pantallaMenu;
	private PantallaMenu pantallaAcerca;
	private PantallaMenu pantallaOpciones;

	private Nivel currentLevel;
	private int currentLvl = 0;

	private final String[] LEVEL_FILES = new String[]{ "nivel3.tmx","PrimerNivel.tmx","mapaTutorial.tmx",  "nivel4.tmx", "nivel5.tmx"};

	private AssetManager manager;


	static final float PPM = 32f;
	static final float ANCHO = 1280;
	static final float ALTO = 704;

	static int highscore = 0;
	static int score = 0;
	static Preferences prefs;

	@Override
	public void create () {
		getPreferences();
		manager = new AssetManager();
		Opciones.CargarOpciones();
		initPantallas();
		setScreen(pantallaMenu);
	}

	void getPreferences(){
		prefs = Gdx.app.getPreferences("Bullet");
		highscore = prefs.getInteger("highscore");
	}

	void initPantallas(){
		agregarAssetsPantallas();
		createMenu();
		createAcerca();
		createOptiones();
		pantallaMenu.setActiveScreen();
	}
	void initPantallas(boolean nul){
		agregarAssetsPantallas();
		createMenu();
		createAcerca();
		createOptiones();
		pantallaMenu.setActiveScreen();
		setScreen(pantallaMenu);

		currentLevel.dispose();
	}

	private void agregarAssetsPantallas(){

		manager.load("fondopn.png", Texture.class);
		manager.load("btn-play.png", Texture.class);
		manager.load("btn-play-presionado.png", Texture.class);
		manager.load("btn-about.png", Texture.class);
		manager.load("btn-about-export-presionado.png", Texture.class);
		manager.load("btn-configt.png", Texture.class);
		manager.load("btn-configt-presionado.png", Texture.class);
		manager.load("back.png", Texture.class);
		manager.load("backPressed.png", Texture.class);
		manager.load("btn-music-off.png", Texture.class);
		manager.load("btn-music-off-presionado.png", Texture.class);
		manager.load("btn-music-on.png", Texture.class);
		manager.load("btn-music-on-presionado.png", Texture.class);
		manager.finishLoading();

	}

	private void freeRecursosPantallas(){
		manager.clear();
		pantallaMenu.dispose();
		pantallaOpciones.dispose();
		pantallaAcerca.dispose();
		pantallaMenu = null;
		pantallaAcerca = null;
		pantallaOpciones = null;
	}

	private void createMenu(){
		pantallaMenu = new PantallaMenu(this, manager.get("fondopn.png", Texture.class));

		pantallaMenu.createBtn(manager.get("btn-play.png", Texture.class),
				manager.get("btn-play-presionado.png", Texture.class),
				ANCHO/2,
				4*ALTO/8,
				new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y) {
						super.clicked(event, x, y);
						//juego.setScreen(new PantallaJuego(juego));
						iniciarJuego();
					}
				});

		pantallaMenu.createBtn(manager.get("btn-about.png", Texture.class),
				manager.get("btn-about-export-presionado.png", Texture.class),
				ANCHO/2,
				4*ALTO/15,
				new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y) {
						super.clicked(event, x, y);
						pantallaAcerca.setActiveScreen();
						setScreen(pantallaAcerca);
					}
				});

		pantallaMenu.createBtn(manager.get("btn-configt.png", Texture.class),
				manager.get("btn-configt-presionado.png", Texture.class),
				ANCHO/2,
				ALTO/30,
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
		pantallaAcerca = new PantallaMenu(this, manager.get("fondopn.png", Texture.class));

		pantallaAcerca.createBtn(manager.get("back.png", Texture.class),
				manager.get("backPressed.png", Texture.class),
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
		pantallaAcerca.addBack(pantallaMenu);
	}

	void createOptiones(){

		pantallaOpciones = new PantallaMenu(this, manager.get("fondopn.png", Texture.class));

		pantallaOpciones.createBtn(manager.get("back.png", Texture.class),
				manager.get("backPressed.png", Texture.class),
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
			style.imageUp = new TextureRegionDrawable(new TextureRegion((manager.get("btn-music-on.png", Texture.class))));
			style.imageChecked = new TextureRegionDrawable(new TextureRegion((manager.get("btn-music-off.png", Texture.class))));
			style.imageCheckedOver = new TextureRegionDrawable(new TextureRegion((manager.get("btn-music-off-presionado.png", Texture.class))));
		} else {
			style.imageUp = new TextureRegionDrawable(new TextureRegion((manager.get("btn-music-off.png", Texture.class))));
			style.imageChecked = new TextureRegionDrawable(new TextureRegion((manager.get("btn-music-on.png", Texture.class))));
			style.imageCheckedOver = new TextureRegionDrawable(new TextureRegion((manager.get("btn-music-on-presionado.png", Texture.class))));
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

		pantallaOpciones.addBack(pantallaMenu);
	}

	void iniciarJuego(){
		freeRecursosPantallas();
		currentLvl = 0;
		currentLevel = new Nivel(this, new TmxMapLoader().load(LEVEL_FILES[0]), 1.5f, currentLvl);
		setScreen(currentLevel);

	}

	void iniciarJuego(String filename){
		currentLevel.dispose();
		currentLevel = new Nivel(this, new TmxMapLoader().load(filename), 1.5f+currentLvl/2, currentLvl);
		setScreen(currentLevel);

	}

	void nextLevel(){
		currentLvl++;
		score ++;
		if (highscore<score){
			prefs.putInteger("highscore", score);
		}
		iniciarJuego(LEVEL_FILES[currentLvl]);

	}


}


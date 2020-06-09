package mx.itesm.BulletTimeReloaded;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Juego extends com.badlogic.gdx.Game {


	private mx.itesm.BulletTimeReloaded.PantallaMenu pantallaMenu;
	private mx.itesm.BulletTimeReloaded.PantallaMenu pantallaAcerca;
	private mx.itesm.BulletTimeReloaded.PantallaMenu pantallaOpciones;
	private mx.itesm.BulletTimeReloaded.PantallaMenu pantallaCreditos;


	private mx.itesm.BulletTimeReloaded.Nivel currentLevel;
	private int currentLvl = 0;

	private final String[] LEVEL_FILES = new String[]{"PrimerNivel.tmx","mapaTutorial.tmx",  "nivel3.tmx", "nivel4.tmx", "nivel5.tmx"};

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
		mx.itesm.BulletTimeReloaded.Opciones.CargarOpciones();
		initPantallas();
		setScreen(pantallaMenu);
	}

	void getPreferences(){
		prefs = Gdx.app.getPreferences("Bullet");
		highscore = prefs.getInteger("highscore");
	}

	void lostPantalla(){
		agregarAssetsPantallas();
		createLost();
	}

	private void createLost(){
		mx.itesm.BulletTimeReloaded.PantallaMenu lost = new mx.itesm.BulletTimeReloaded.PantallaMenu(this, manager.get("fondopn.png", Texture.class));
		initPantallas(false, true);
		lost.createBtn(manager.get("back.png", Texture.class),
				manager.get("backPressed.png", Texture.class),
				ANCHO/2,
				4*ALTO/8,
				new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y) {
						super.clicked(event, x, y);
						pantallaMenu.setActiveScreen();
						setScreen(pantallaMenu);
						currentLevel.dispose();
					}
				});

		lost.addTexto("fuenteGanarPerder.fnt", "Perdiste", ANCHO/2, ALTO-ALTO/12);
		lost.addBack(pantallaMenu, true);
		lost.setActiveScreen();
		setScreen(lost);
		//currentLevel.dispose();
	}
	void winPantalla(){
		agregarAssetsPantallas();
		createWin();
	}

	private void createWin() {
		mx.itesm.BulletTimeReloaded.PantallaMenu win = new mx.itesm.BulletTimeReloaded.PantallaMenu(this, manager.get("fondopn.png", Texture.class));
		initPantallas(false, true);
		win.createBtn(manager.get("back.png", Texture.class),
				manager.get("backPressed.png", Texture.class),
				ANCHO/2,
				4*ALTO/8,
				new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y) {
						super.clicked(event, x, y);
						pantallaMenu.setActiveScreen();
						setScreen(pantallaMenu);
						currentLevel.dispose();
					}
				});

		win.addTexto("fuenteGanarPerder.fnt", "Felicidades, ganaste!!!", ANCHO/2, ALTO-ALTO/12);
		win.addBack(pantallaMenu, true);
		win.setActiveScreen();
		setScreen(win);
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

	void initPantallas(boolean nul, boolean nule){
		agregarAssetsPantallas();
		createMenu();
		createAcerca();
		createOptiones();
	}

	private void agregarAssetsPantallas(){
		manager.load("btn-resume.png", Texture.class);
		manager.load("btn-resume-presionado.png", Texture.class);
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
		pantallaCreditos.dispose();
		pantallaMenu = null;
		pantallaAcerca = null;
		pantallaOpciones = null;
		pantallaCreditos = null;
	}

	private void createMenu(){
		pantallaMenu = new mx.itesm.BulletTimeReloaded.PantallaMenu(this, manager.get("fondopn.png", Texture.class));

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
		pantallaAcerca = new mx.itesm.BulletTimeReloaded.PantallaMenu(this, manager.get("fondopn.png", Texture.class));



		pantallaAcerca.createBtn(manager.get("back.png", Texture.class),
				manager.get("backPressed.png", Texture.class),
				ANCHO/2,
				ALTO/26,
				new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y) {
						super.clicked(event, x, y);
						getScreen().hide();
						pantallaMenu.setActiveScreen();
						setScreen(pantallaMenu);
					}
				});
		pantallaAcerca.createBtn(manager.get("btn-resume.png", Texture.class),
				manager.get("btn-resume-presionado.png", Texture.class),
				ANCHO/2,
				ALTO/5,
				new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y) {
						super.clicked(event, x, y);
						getScreen().hide();
						pantallaCreditos.setActiveScreen();
						setScreen(pantallaCreditos);
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
		createCreditos();

	}

	void createCreditos(){


		pantallaCreditos = new mx.itesm.BulletTimeReloaded.PantallaMenu(this, manager.get("fondopn.png", Texture.class));

		pantallaCreditos.addTexto("fuenteTecno.fnt",
				"Acerca De",
				ANCHO/2,
				ALTO-ALTO/12);
		pantallaCreditos.addTexto("fuenteTecnoChica.fnt",
				"Desarrolladores",
				ANCHO/2,
				ALTO-ALTO/4);
		pantallaCreditos.addTexto("fuenteTecnoChica.fnt",
				"Ivan Honc, Bruno Vazquez, Jesus Alcala y Emiliano Heredia",
				ANCHO/2,
				ALTO-5*ALTO/16);
		pantallaCreditos.addTexto("fuenteTecnoChica.fnt",
				"Desarrollado para la clase de desarrollo de videojuegos.",
				ANCHO/2,
				ALTO-7*ALTO/16);
		pantallaCreditos.addTexto("fuenteTecnoChica.fnt",
				"Semestre Febrero-Junio 2020",
				ANCHO/2,
				ALTO-9*ALTO/16);
		pantallaCreditos.createBtn(manager.get("back.png", Texture.class),
				manager.get("backPressed.png", Texture.class),
				ANCHO/2,
				ALTO/5,
				new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y) {
						super.clicked(event, x, y);
						getScreen().hide();
						pantallaAcerca.setActiveScreen();
						setScreen(pantallaAcerca);
					}
				});
		pantallaCreditos.addBack(pantallaAcerca);

	}


	void createOptiones(){

		pantallaOpciones = new mx.itesm.BulletTimeReloaded.PantallaMenu(this, manager.get("fondopn.png", Texture.class));

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
		if(mx.itesm.BulletTimeReloaded.Opciones.sonido) {
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
						((ImageButton)event.getStage().getActors().get(event.getButton())).setChecked(mx.itesm.BulletTimeReloaded.Opciones.toggleSonido());
						mx.itesm.BulletTimeReloaded.Opciones.SaveOptions();
					}
				});

		pantallaOpciones.addBack(pantallaMenu);
	}

	void iniciarJuego(){
		freeRecursosPantallas();
		currentLvl = 0;
		score = 0;
		currentLevel = new mx.itesm.BulletTimeReloaded.Nivel(this, new TmxMapLoader().load(LEVEL_FILES[0]), 1.5f, currentLvl, highscore);
		setScreen(currentLevel);

	}

	void iniciarJuego(String filename){
		currentLevel.dispose();
		currentLevel = new mx.itesm.BulletTimeReloaded.Nivel(this, new TmxMapLoader().load(filename), 1.5f+currentLvl/2, currentLvl, highscore);
		setScreen(currentLevel);

	}

	void nextLevel(){
		currentLvl++;
		score ++;
		if (highscore<score){
			highscore = score;
			prefs.putInteger("highscore", score);
		}
		iniciarJuego(LEVEL_FILES[currentLvl]);

	}


}


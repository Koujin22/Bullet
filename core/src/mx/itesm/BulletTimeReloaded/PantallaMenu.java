package mx.itesm.BulletTimeReloaded;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.ArrayList;

class PantallaMenu extends mx.itesm.BulletTimeReloaded.Pantalla implements InputProcessor {


    private Texture texturaFondo;
    private final Stage stage;
    private final ArrayList<Texto> texto = new ArrayList<>();
    boolean backBoton = false;
    boolean inicarPantalla = false;
    private InputMultiplexer inputMultiplexer;
    PantallaMenu previous;

    // Constructor, inicializa los objetos camara, vista, batch
    PantallaMenu(Juego juego, Texture texturaFondo) {
        super(juego);
        this.texturaFondo = texturaFondo;
        this.stage = new Stage(vista);
    }
    PantallaMenu(Juego juego) {
        super(juego);

        this.stage = new Stage(vista);
    }

    Stage getStage(){
        return stage;
    }

    void createBtn(Texture texturaBoton, Texture textureBtnPress, float x, float y, ClickListener listener){
        TextureRegionDrawable btn = new TextureRegionDrawable(new TextureRegion((texturaBoton)));
        TextureRegionDrawable btnPress = new TextureRegionDrawable(new TextureRegion((textureBtnPress)));

        ImageButton btnImg = new ImageButton(btn, btnPress);
        btnImg.setPosition(x-btnImg.getWidth()/2, y);

        btnImg.addListener(listener);

        this.stage.addActor(btnImg);

    }

    void createBtn(ImageButton.ImageButtonStyle style, float x, float y, ClickListener listener){
        ImageButton btnImg = new ImageButton(style);
        btnImg.setName("test");
        btnImg.setPosition(x-btnImg.getWidth()/2, y);

        btnImg.addListener(listener);


        this.stage.addActor(btnImg);
    }

    void setActiveScreen(){
        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(this);
        inputMultiplexer.addProcessor(this.stage);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    void addTexto(String archivo, String mensaje, float x, float y){
        Texto txt = new Texto(archivo,
                mensaje,
                x,
                y);

        stage.addActor(txt);
        texto.add(txt);


    }

    void addBack(PantallaMenu previous){
        this.previous = previous;
        backBoton = true;
        Gdx.input.setCatchKey(Input.Keys.BACK, true);
    }
    void addBack(PantallaMenu previous, boolean iniciarPantalla){
        this.inicarPantalla = iniciarPantalla;
        this.previous = previous;
        backBoton = true;
        Gdx.input.setCatchKey(Input.Keys.BACK, true);
    }
    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        borrarPantalla();
        stage.getBatch().setProjectionMatrix(camara.combined);
        stage.getBatch().begin();
        if(texturaFondo!=null) stage.getBatch().draw(texturaFondo, 0, 0);
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
        borrarPantalla();
    }

    @Override
    public void dispose() {
        stage.dispose();
        for (int i = 0; i<texto.size(); i++){
            texto.get(i).dispose();
        }
        if(texturaFondo!=null) texturaFondo.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        if(inicarPantalla==true && backBoton == true && keycode == Input.Keys.BACK){
            juego.initPantallas();
            juego.getScreen().hide();
            previous.setActiveScreen();
            juego.setScreen(previous);
        }
        else if(backBoton == true && keycode == Input.Keys.BACK){
            juego.getScreen().hide();
            previous.setActiveScreen();
            juego.setScreen(previous);
        }
        else if(previous==null){
            System.exit(0);
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
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

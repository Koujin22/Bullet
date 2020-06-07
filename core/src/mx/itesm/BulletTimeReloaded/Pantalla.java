package mx.itesm.BulletTimeReloaded;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Representa el comportamiento genérico de cualquier pantalla que forma
 * parte del juego
 */
abstract class Pantalla implements Screen{
    final float PPM = Juego.PPM;
    final float ANCHO = Juego.ANCHO;;
    final float ALTO = Juego.ALTO;;

    // Atributos disponibles solo en las subclases
    // Todas las pantallas tienen una cámara y una vista
    OrthographicCamera camara;
    Viewport vista;

    final Juego juego;

    Pantalla(Juego juego){
        // Crea la cámara con las dimensiones del mundo
        camara = new OrthographicCamera(ANCHO, ALTO);
        // En el centro de la pantalla. (x,y) de la cámara en el centro geométrico
        camara.position.set(ANCHO / 2, ALTO / 2, 0);
        camara.update();
        // La vista que escala los elementos gráficos
        vista = new StretchViewport(ANCHO, ALTO, camara);
        this.juego = juego;
    }

    // Borra la pantalla con fondo negro
    void borrarPantalla() {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    void borrarPantalla(float r, float g, float b) {
        Gdx.gl.glClearColor(r,g,b,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }


}

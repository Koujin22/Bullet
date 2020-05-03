package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

class Texto {

    private BitmapFont font;
    private String mensaje;
    private float x;
    private float y;
    private Batch batch;

    Texto(String archivo, String mensaje, float x, float y, Batch batch){
        this.mensaje = mensaje;
        this.x = x;
        this.y = y;
        this.batch = batch;
        font = new BitmapFont(Gdx.files.internal(archivo));//archivo.fnt

    }
    void render() {
        GlyphLayout glyph = new GlyphLayout();
        glyph.setText(font,mensaje);
        float anchoTexto = glyph.width;
        font.draw(batch, glyph,x-anchoTexto/2, y);
    }

    void dispose(){
        font.dispose();
    }

}
package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

class Texto extends Actor {

    private BitmapFont font;
    private String mensaje;
    private float x;
    private float y;

    Texto(String archivo, String mensaje, float x, float y){
        this.mensaje = mensaje;
        this.x = x;
        this.y = y;
        font = new BitmapFont(Gdx.files.internal(archivo));//archivo.fnt

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        GlyphLayout glyph = new GlyphLayout();
        glyph.setText(font,mensaje);
        float anchoTexto = glyph.width;
        font.draw(batch, glyph,x-anchoTexto/2, y);
    }

    void dispose(){
        font.dispose();
    }

}
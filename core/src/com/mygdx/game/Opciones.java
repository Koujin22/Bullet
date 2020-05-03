package com.mygdx.game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

final class Opciones {

    static boolean sonido = true;


    private Opciones(){};

    static boolean CargarOpciones(){
        boolean cargo = true;
        try{

            BufferedReader reader = new BufferedReader(new FileReader("settings.txt"));
            sonido = Boolean.parseBoolean(reader.readLine());
            reader.close();

        }catch (Exception e){
            cargo = false;
            e.printStackTrace();;
        }

        return  cargo;
    }

    static boolean SaveOptions(){
        boolean guardo = true;
        try{
            FileWriter file = new FileWriter("settings.txt");
            file.write(String.valueOf(sonido));
            file.close();
        } catch (IOException e) {
            guardo = false;
            e.printStackTrace();
        }
        return guardo;
    }

    static boolean toggleSonido(){
        sonido= !sonido;
        return sonido;
    }

}

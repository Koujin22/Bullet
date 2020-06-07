package mx.itesm.BulletTimeReloaded;

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
            System.out.println(sonido);
            sonido = Boolean.parseBoolean(reader.readLine());
            System.out.println(sonido);
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
            System.out.println(String.valueOf(sonido));
            file.write(String.valueOf(sonido));
            file.close();
        } catch (IOException e) {
            guardo = false;
            e.printStackTrace();
        }
        return guardo;
    }

    static boolean toggleSonido(){
        System.out.println(sonido);
        sonido= !sonido;
        System.out.println(sonido);
        return sonido;
    }

}

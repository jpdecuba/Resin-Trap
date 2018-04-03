package FileSave;

import java.io.*;
import java.util.List;

public class SaveFiles {

    private  String path = System.getenv("APPDATA") + "\\Honeypot\\Preferences.bin";

    public boolean WritePreferences(Preferences pref){


        try {
            FileOutputStream fileOut = new FileOutputStream(path);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(pref);
            out.close();
            fileOut.close();
            return true;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }

    }


    public Preferences ReadPreferences(){

        try
        {
            FileInputStream fileIn = new FileInputStream(path);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            Preferences pref = (Preferences) in.readObject();
            in.close();
            fileIn.close();
            return pref;
        } catch (FileNotFoundException e) {
            //e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new Preferences();
    }
}

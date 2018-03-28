package Model.Preset;

public class Preset {
    private String naam = "default preset";
    private Soort soort = Soort.Custom;
    private Type type = Type.None;

    public Preset(Type t, Soort s){
        this.type = t;
        this.soort = s;
    }


}

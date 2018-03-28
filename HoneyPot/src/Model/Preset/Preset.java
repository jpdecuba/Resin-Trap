package Model.Preset;

import HoneyPot.lowinteraction.LIModule;
import HoneyPot.protocol.FtpProtocol;
import HoneyPot.protocol.MySQLProtocol;
import HoneyPot.protocol.SmtpProtocol;
import Main.Main;

import java.util.ArrayList;

public class Preset {
    private String naam = "default preset";
    private Soort soort = Soort.Express;
    private Type type = Type.None;
    private ArrayList<LIModule> services = new ArrayList<LIModule>();

    public Preset(){
    }

    public void SetTypeAndSoort(Type t, Soort s){
        this.type = t;
        this.soort = s;
        FillServices();
    }

    private void FillServices(){
        services.clear();
        switch(soort){
            case Express:
                //Express function
                return;
            case Home:
                services.add(new LIModule(new FtpProtocol(), Main.honeypot));
                break;
            case Company_Small:
                switch (type) {
                    case Type_1:
                        services.add(new LIModule(new SmtpProtocol(), Main.honeypot));
                        break;
                    case Type_2:
                        services.add(new LIModule(new MySQLProtocol(), Main.honeypot));
                        break;
                        default:
                            return;
                }
                break;
            case Company_Big:
                switch (type) {
                    case Type_1:
                        services.add(new LIModule(new MySQLProtocol(), Main.honeypot));
                        services.add(new LIModule(new FtpProtocol(), Main.honeypot));
                        break;
                    case Type_2:
                        services.add(new LIModule(new SmtpProtocol(), Main.honeypot));
                        services.add(new LIModule(new FtpProtocol(), Main.honeypot));
                        break;
                    default:
                        return;
                }
                break;
            default:
                    return;
        }
    }

    public ArrayList<LIModule> getServices() {
        return services;
    }
}

package Model.Preset;

import HoneyPot.lowinteraction.LIModule;
import HoneyPot.protocol.FtpProtocol;
import HoneyPot.protocol.IrcProtocol;
import HoneyPot.protocol.MySQLProtocol;
import HoneyPot.protocol.SmtpProtocol;
import Main.Main;

import javax.naming.LimitExceededException;
import java.io.Serializable;
import java.util.ArrayList;

public class Preset  implements Serializable{
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
        LIModule mSQL = new LIModule(new MySQLProtocol(), Main.honeypot);
        LIModule mFTP = new LIModule(new FtpProtocol(), Main.honeypot);
        LIModule mIRC = new LIModule(new IrcProtocol(), Main.honeypot);
        LIModule mSMTP = new LIModule(new SmtpProtocol(), Main.honeypot);

        services.add(mSQL);
        services.add(mFTP);
        services.add(mIRC);
        services.add(mSMTP);

        for (LIModule m: services) {
            Main.honeypot.RegisterService(m);
            Main.honeypot.startPort(m.getPort());
        }

        switch(soort){
            case Express:
                //Express function
                return;
            case Home:
                for(LIModule m : services){
                    if(m == mSQL){ Main.honeypot.DeRegisterService(m); }
                    else if(m == mSMTP){ Main.honeypot.DeRegisterService(m); }
                    else if(m == mIRC){ Main.honeypot.DeRegisterService(m); }
                }
                break;
            case Company_Small:
                switch (type) {
                    case Type_1:
                        for(LIModule m : services){
                            if(m == mIRC){ Main.honeypot.DeRegisterService(m); }
                            else if(m == mSQL){ Main.honeypot.DeRegisterService(m); }
                            else if(m == mFTP){ Main.honeypot.DeRegisterService(m); }
                        }
                        break;
                    case Type_2:
                        for(LIModule m : services){
                            if(m == mFTP){ Main.honeypot.DeRegisterService(m); }
                            else if(m == mSMTP){ Main.honeypot.DeRegisterService(m); }
                            else if(m == mIRC){ Main.honeypot.DeRegisterService(m); }
                        }
                        break;
                        default:
                            return;
                }
                break;
            case Company_Big:
                switch (type) {
                    case Type_1:
                        for(LIModule m : services){
                            if(m == mIRC){ Main.honeypot.DeRegisterService(m); }
                            else if(m == mSQL){ Main.honeypot.DeRegisterService(m); }
                        }
                        break;
                    case Type_2:
                        for(LIModule m : services){
                            if(m == mIRC){ Main.honeypot.DeRegisterService(m); }
                            else if(m == mSMTP){ Main.honeypot.DeRegisterService(m); }
                        }
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

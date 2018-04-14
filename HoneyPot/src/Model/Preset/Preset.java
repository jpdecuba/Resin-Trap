package Model.Preset;

import HoneyPot.lowinteraction.ILIModule;
import HoneyPot.lowinteraction.LIModule;
import HoneyPot.protocol.*;
import Main.Main;
import netwerkscan.PresetsScan;

import javax.naming.LimitExceededException;
import java.io.Serializable;
import java.util.ArrayList;

public class Preset  implements Serializable{
    private Soort soort = Soort.Express;
    private Type type = Type.None;
    private ArrayList<ILIModule> services = new ArrayList<ILIModule>();
    private transient  PresetsScan scanner;

    private ArrayList<Integer> ports;

    public Preset(){
        scanner = new PresetsScan();
    }

    public void SetTypeAndSoort(Type t, Soort s){
        this.type = t;
        this.soort = s;
        if (soort == Soort.Express)
            ExpressFill();
        else
            FillServices();
    }
    public void Start(){
        ports = new ArrayList<>();
        for(ILIModule m : services) {

            if(m.OnorOff()){
                ports.add(m.getPort());
            }

        }



        //Main.StartHoneypotServices(services);
        InitializeServices();
        for (ILIModule m : services){
            if(!ports.contains(m.getPort())){
                Main.honeypot.DeRegisterService(m);
            }
        }
        Main.StartHoneypotServices(services);
    }

    public void ExpressFill(){
        int hosts = scanner.checkHosts();
        if (hosts <= 25){
            soort = Soort.Home;
        } else if(hosts <= 50) {
            soort = Soort.Company_Small;
            type = Type.Type_2;
        } else if(hosts > 50){
            soort = Soort.Company_Big;
            type = Type.Type_2;
        }
        FillServices();
    }

    private void FillServices(){
        InitializeServices();

        switch(soort){

            case Home:
                for(ILIModule m : services){
                    if(
                        m.getProtocol().getClass().equals(MySQLProtocol.class) ||
                        m.getProtocol().getClass().equals(SmtpProtocol.class) ||
                        m.getProtocol().getClass().equals(IrcProtocol.class)
                    ){
                        Main.honeypot.DeRegisterService(m);
                    }
                }
                break;

            case Company_Small:
                switch (type) {

                    case Type_1:
                        for(ILIModule m : services){
                            if(
                                m.getProtocol().getClass().equals(IrcProtocol.class) ||
                                m.getProtocol().getClass().equals(MySQLProtocol.class) ||
                                m.getProtocol().getClass().equals(FtpProtocol.class)
                            ){
                                Main.honeypot.DeRegisterService(m);
                            }
                        }
                        break;

                    case Type_2:
                        for(ILIModule m : services){
                            if(
                                m.getProtocol().getClass().equals(IrcProtocol.class) ||
                                m.getProtocol().getClass().equals(SmtpProtocol.class) ||
                                m.getProtocol().getClass().equals(FtpProtocol.class)
                            ){
                                Main.honeypot.DeRegisterService(m);
                            }
                        }
                        break;


                    default: return;
                }
                break;

            case Company_Big:
                switch (type) {

                    case Type_1:
                        for(ILIModule m : services){
                            if(
                                m.getProtocol().getClass().equals(IrcProtocol.class) ||
                                m.getProtocol().getClass().equals(MySQLProtocol.class)
                            ){
                                Main.honeypot.DeRegisterService(m);
                            }
                        }
                        break;

                    case Type_2:
                        for(ILIModule m : services){
                            if(
                                m.getProtocol().getClass().equals(IrcProtocol.class) ||
                                m.getProtocol().getClass().equals(SmtpProtocol.class)
                            ){
                                Main.honeypot.DeRegisterService(m);
                            }
                        }
                        break;

                    default: return;
                }
                break;

            default: return;
        }

        Main.setPreset(this);
        Main.StartHoneypotServices(services);
    }

    private void InitializeServices(){
        services.clear();

        LIModule mSQL = new LIModule(new MySQLProtocol(), Main.honeypot);
        LIModule mFTP = new LIModule(new FtpProtocol(), Main.honeypot);
        LIModule mIRC = new LIModule(new IrcProtocol(), Main.honeypot);
        LIModule mSMTP = new LIModule(new SmtpProtocol(), Main.honeypot);

        services.add(mSQL);
        services.add(mFTP);
        services.add(mIRC);
        services.add(mSMTP);

        for (ILIModule m: services) {
            Main.honeypot.RegisterService(m);
            Main.honeypot.startPort(m.getPort());
        }
    }

    public ArrayList<ILIModule> getServices() {
        return services;
    }
}

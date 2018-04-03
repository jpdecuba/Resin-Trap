package FileSave;

import Model.Preset.Preset;
import Model.User;

import java.io.Serializable;

public class Preferences implements Serializable{

    private Preset preset = null;
    private User Account = null;

    public Preset getPreset() {
        return preset;
    }

    public void setPreset(Preset preset) {
        this.preset = preset;
    }

    public User getAccount() {
        return Account;
    }

    public void setAccount(User account) {
        Account = account;
    }
}

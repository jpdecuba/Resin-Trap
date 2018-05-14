package Client.FileSave;

import Client.Model.Preset.Preset;
import Shared.Model.User;

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

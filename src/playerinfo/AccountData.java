package playerinfo;

import java.io.Serializable;

public class AccountData implements Serializable {
    private String username, passwordHash;
    private int totalStars, totalTime;
    public AccountData(String username, String passwordHash, int totalStars, int totalTime){
        this.username = username;
        this.passwordHash = passwordHash;
        this.totalStars = totalStars;
        this.totalTime = totalTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public int getTotalStars() {
        return totalStars;
    }

    public void setTotalStars(int totalStars) {
        this.totalStars = totalStars;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }
}

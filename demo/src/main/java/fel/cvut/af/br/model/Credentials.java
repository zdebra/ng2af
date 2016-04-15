package fel.cvut.af.br.model;

import java.io.Serializable;

/**
 * This object is used as a parsed credentials from body of request
 */
public class Credentials implements Serializable {

    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

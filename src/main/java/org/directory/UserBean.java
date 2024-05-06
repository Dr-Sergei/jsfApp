package org.directory;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;


@Named("user")
@SessionScoped
public class UserBean implements Serializable {
    private String name;
    private String password;
    private String email;

    // Getter und Setter für username und password

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    // Methoden für die Registrierung und Anmeldung
    public String register() {
        if (RegistrationManager.registerUser(name, password, email)) {
            return "registration_success"; // Navigationsfall für erfolgreiche Registrierung
        } else {
            return "registration_failure"; // Navigationsfall für fehlgeschlagene Registrierung
        }
    }

    public String login() {
        if (LoginManager.loginUser(name, password)) {
            return "welcome"; // Navigationsfall für erfolgreiche Anmeldung
        } else {
            return "login_failure"; // Navigationsfall für fehlgeschlagene Anmeldung
        }
    }
}

package edu.praktikum;

public class CourierForLogin {
    private String login;
    private String password;

    public static class Builder {
        private String login = null;
        private String password = null;

        public CourierForLogin.Builder login(String val) {
            this.login = val;
            return this;
        }

        public CourierForLogin.Builder password(String val) {
            this.password = val;
            return this;
        }

        public CourierForLogin build() {
            return new CourierForLogin(this);
        }
    }

    public CourierForLogin(Builder builder) {
        login = builder.login;
        password = builder.password;
    }

    public CourierForLogin(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

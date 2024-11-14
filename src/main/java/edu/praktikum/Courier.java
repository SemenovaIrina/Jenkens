package edu.praktikum;

public class Courier {
    private String login;
    private String password;
    private String firstName;

    public static class Builder {
        private String login = null;
        private String password = null;
        private String firstName = null;

        public Builder login(String val) {
            this.login = val;
            return this;
        }

        public Builder password(String val) {
            this.password = val;
            return this;
        }

        public Builder firstName(String val) {
            this.firstName = val;
            return this;
        }

        public Courier build() {
            return new Courier(this);
        }
    }

    public Courier(Builder builder) {
        login = builder.login;
        password = builder.password;
        firstName = builder.firstName;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}



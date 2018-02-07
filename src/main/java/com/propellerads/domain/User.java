package com.propellerads.domain;

public class User {

    private String login;
    private String password;
    private State state;

    public enum State {
        DISABLED, LOCKED
    }

    public User withLogin(final String login) {
        this.login = login;
        return this;
    }

    public User withPassword(final String password) {
        this.password = password;
        return this;
    }

    public User withState(final State state) {
        this.state = state;
        return this;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public State getState() {
        return state;
    }

}

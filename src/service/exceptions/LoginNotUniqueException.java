package service.exceptions;

import service.exceptions.ServiceException;

public class LoginNotUniqueException extends ServiceException {
    private String login;

    public LoginNotUniqueException(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }
}

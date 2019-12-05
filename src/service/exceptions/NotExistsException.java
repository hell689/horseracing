package service.exceptions;

import service.exceptions.ServiceException;

public class NotExistsException extends ServiceException {
    private Long id;

    public NotExistsException(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}

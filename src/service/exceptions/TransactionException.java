package service.exceptions;

import service.exceptions.ServiceException;

public class TransactionException extends ServiceException {

    public TransactionException (Throwable cause){
        super(cause);
    }
}

package service;


import domain.Client;
import service.exceptions.ServiceException;

import java.util.List;

public interface ClientService {
    Client findById(Long id) throws ServiceException;

    Client findByLoginAndPassword(String login, String password) throws ServiceException;

    List<Client> findAll() throws ServiceException;

    void save(Client client) throws ServiceException;

    void changePassword(Long userId, String oldPassword, String newPassword) throws ServiceException;

    void delete(Long id) throws ServiceException;
}

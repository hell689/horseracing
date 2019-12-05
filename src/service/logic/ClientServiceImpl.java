package service.logic;

import dao.ClientDao;
import dao.DaoException;
import dao.UserDao;
import domain.Client;
import service.ClientService;
import service.exceptions.LoginNotUniqueException;
import service.exceptions.NotExistsException;
import service.exceptions.ServiceException;


import java.util.List;

public class ClientServiceImpl extends BaseService implements ClientService {
    private ClientDao clientDao;

    public void setClientDao(ClientDao clientDao){
        this.clientDao = clientDao;
    }

    @Override
    public Client findById(Long id) throws ServiceException {
        try {
            return clientDao.read(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public Client findByLogin(String login) throws ServiceException {
        try {
            return clientDao.readByLogin(login);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Client findByLoginAndPassword(String login, String password) throws ServiceException {
        try {
            return clientDao.readByLoginAndPassword(login, password);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Client> findAll() throws ServiceException {
        try {
            return clientDao.readAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void save(Client client) throws ServiceException {
        try {
            //getTransaction().start();
            if(client.getId() != null) {
                Client storedClient = clientDao.read(client.getId());
                if(storedClient != null) {
                    client.setPassword(storedClient.getPassword());
                    if(storedClient.getLogin().equals(client.getLogin()) || clientDao.readByLogin(client.getLogin()) == null) {
                        clientDao.update(client);
                    } else {
                        throw new LoginNotUniqueException(client.getLogin());
                    }
                } else {
                    throw new NotExistsException(client.getId());
                }
            } else {
                //user.setPassword(defaultPassword);
                if(clientDao.readByLogin(client.getLogin()) == null) {
                    Long id = clientDao.create(client);
                    client.setId(id);
                } else {
                    throw new LoginNotUniqueException(client.getLogin());
                }
            }
            //getTransaction().commit();
        } catch(DaoException e) {
            try { getTransaction().rollback(); } catch(ServiceException e1) {}
            throw new ServiceException(e);
        } catch(ServiceException e) {
            try { getTransaction().rollback(); } catch(ServiceException e1) {}
            throw e;
        }
    }

    @Override
    public void changePassword(Long clientId, String oldPassword, String newPassword) throws ServiceException {

    }

    @Override
    public void delete(Long id) throws ServiceException {
        try {
            clientDao.delete(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void update(Client client) throws ServiceException{
        try {
            clientDao.update(client);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}

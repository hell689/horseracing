package service;

import domain.User;
import service.exceptions.ServiceException;

import java.util.List;

public interface UserService {
    User findById(Long id) throws ServiceException;

    User findByLoginAndPassword(String login, String password) throws ServiceException;

    List<User> findAll() throws ServiceException;

    void save(User user) throws ServiceException;

    void changePassword(Long userId, String oldPassword, String newPassword) throws ServiceException;

    void delete(Long id) throws ServiceException;
}

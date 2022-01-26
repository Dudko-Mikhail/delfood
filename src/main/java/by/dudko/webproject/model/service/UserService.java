package by.dudko.webproject.model.service;

import by.dudko.webproject.exception.ServiceException;

import java.util.Map;

public interface UserService {
    boolean signUp(Map<String, String> userData) throws ServiceException;
}

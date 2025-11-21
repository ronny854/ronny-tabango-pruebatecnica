package com.example.crud_simple_ronny.service;
import com.example.crud_simple_ronny.model.UserModel;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class UserService {
    private Map<Long, UserModel> users = new HashMap<>();
    private long counter = 1;


    public UserService() {
        users.put(counter, new UserModel(counter++, "Juan Pérez", "juan@example.com"));
        users.put(counter, new UserModel(counter++, "Ana Torres", "ana@example.com"));
        users.put(counter, new UserModel(counter++, "Carlos Silva", "carlos@example.com"));
        users.put(counter, new UserModel(counter++, "Ronny Tabango", "pruebatecnica@example.com"));
    }


    public Collection<UserModel> getAllUsers() { return users.values(); }


    public UserModel getUser(Long id) { return users.get(id); }


    public UserModel createUser(UserModel user) {
        user.setId(counter++);
        users.put(user.getId(), user);
        return user;
    }


    public UserModel updateUser(Long id, UserModel user) {
        if (!users.containsKey(id)) return null;
        user.setId(id);
        users.put(id, user);
        return user;
    }


    public boolean deleteUser(Long id) {
        return users.remove(id) != null;
    }
}

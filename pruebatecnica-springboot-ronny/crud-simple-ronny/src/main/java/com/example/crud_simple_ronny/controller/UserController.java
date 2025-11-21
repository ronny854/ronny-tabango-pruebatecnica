package com.example.crud_simple_ronny.controller;
import com.example.crud_simple_ronny.model.UserModel;
import com.example.crud_simple_ronny.service.UserService;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    public Collection<UserModel> getAllUsers() {
        return userService.getAllUsers();
    }


    @GetMapping("/{id}")
    public UserModel getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }


    @PostMapping
    public UserModel createUser(@RequestBody UserModel user) {
        return userService.createUser(user);
    }


    @PutMapping("/{id}")
    public UserModel updateUser(@PathVariable Long id, @RequestBody UserModel user) {
        return userService.updateUser(id, user);
    }


    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id) ? "Usuario eliminado" : "No encontrado";
    }
}

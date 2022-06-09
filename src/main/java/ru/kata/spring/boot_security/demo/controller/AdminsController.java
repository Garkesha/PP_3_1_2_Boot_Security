package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminsController {
    private UserService userService;
    private RoleService roleService;

    @Autowired
    public AdminsController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String adminPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        model.addAttribute("user", userService.loadUserByUsername(userDetails.getUsername()));
        model.addAttribute("users", userService.getAll());
        model.addAttribute("newUser", new User());
        model.addAttribute("roleSet", roleService.getAllRoles());
        return "/admin";
    }

    @PostMapping("/create")
    public String createUser(@ModelAttribute User user, @RequestParam(value = "check", required = false) Long[] check) {
        if (check == null) {
            user.setOneRole((Role) roleService.getRoleByName("ROLE_USER"));
        } else {
            for (Long l : check) {
                if (l != null) {
                    user.setOneRole(roleService.getRoleById(l));
                }
            }
        }
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deletedById(id);
        return "redirect:/admin";
    }

    @PostMapping(value = "/update/{id}")
    public String updateUser(@ModelAttribute User user, @RequestParam(value = "check", required = false) Long[] check) {
        if (check == null) {
            user.setOneRole((Role) roleService.getRoleByName("ROLE_USER"));
            userService.saveUser(user);
        } else {
            for (Long l : check) {
                if (l != null) {
                    user.setOneRole(roleService.getRoleById(l));
                    userService.saveUser(user);
                }
            }
        }
        return "redirect:/admin";
    }
}

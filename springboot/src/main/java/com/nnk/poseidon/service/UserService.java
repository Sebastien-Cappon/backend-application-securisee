package com.nnk.poseidon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;

import com.nnk.poseidon.domain.User;
import com.nnk.poseidon.repository.IUserRepository;

import jakarta.validation.Valid;

@Service
public class UserService implements IUserService {
	
	@Autowired
    private IUserRepository iUserRepository;

	@Override
	public String home(Model model)
    {
        model.addAttribute("users", iUserRepository.findAll());
        return "user/list";
    }

	@Override
    public String addUser(User bid) {
        return "user/add";
    }

	@Override
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        User user = iUserRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        user.setPassword("");
        model.addAttribute("user", user);
        return "user/update";
    }
    
	@Override
    public String validate(@Valid User user, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));
            iUserRepository.save(user);
            model.addAttribute("users", iUserRepository.findAll());
            return "redirect:/user/list";
        }
        return "user/add";
    }

	@Override
    public String updateUser(@PathVariable("id") Integer id, @Valid User user,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "user/update";
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        user.setId(id);
        iUserRepository.save(user);
        model.addAttribute("users", iUserRepository.findAll());
        return "redirect:/user/list";
    }

	@Override
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
        User user = iUserRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        iUserRepository.delete(user);
        model.addAttribute("users", iUserRepository.findAll());
        return "redirect:/user/list";
    }
}
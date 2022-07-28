package com.example.web_task_manager.controller;

import com.example.web_task_manager.dto.request.UserDTO;
import com.example.web_task_manager.entity.User;
import com.example.web_task_manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private UserService userService;

    @GetMapping("")
    private String showLogin(Model model){
        model.addAttribute("userDTO",new UserDTO());
        return "login";
    }
    @GetMapping("/register")
    private String showRegister(Model model){
        model.addAttribute("userDTO",new UserDTO());
        return "register";
    }
    @PostMapping("/login_success")
    private String login(@ModelAttribute("userDTO") UserDTO userDTO, BindingResult result, Model model){
        if (result.hasErrors()){
            model.addAttribute("userDTO",userDTO);
            System.out.println("Khong co gi");
            result.toString();
            return "login";
        }
        User user = userService.findByMail(userDTO.getUsername());
        System.out.println(user);
        if(user.getPassword().equals(userDTO.getPassword())){
            System.out.println("Đăng nhập thành công");
//            model.addAttribute("admin",admin);
            return "redirect:/tasklist";
        }
        return "redirect:";
    }@PostMapping("/register-new")
    private String newUser( @ModelAttribute("userDTO") UserDTO userDTO, BindingResult result, Model model){
        if (result.hasErrors()){
            model.addAttribute("userDTO",userDTO);
            result.toString();
            return "register";
        }
        User user = userService.findByMail(userDTO.getUsername());
        System.out.println(user);
        if(user!=null){
            System.out.println("Email đã được sử dụnng");
//            model.addAttribute("admin",admin);
            return "register";
        }
        if (userDTO.getPassword().equalsIgnoreCase(userDTO.getRepeatPassword())){
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            userDTO.setPassword(encoder.encode(userDTO.getPassword()));
            userService.save(userDTO);
            model.addAttribute("userDTO", userDTO);
            System.out.println("Successfully");
            return "redirect:/tasklist";
        }else {
            model.addAttribute("userDTO", userDTO);
            System.out.println("Password is't same");
            return "register";
        }
    }
}

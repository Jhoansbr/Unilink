package com.example.Unilink.Controller;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.Unilink.Modelo.Rol;
import com.example.Unilink.Modelo.User;
import com.example.Unilink.interfaces.UserRepository;
import com.example.Unilink.service.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import com.example.Unilink.utils.JwtUtil;

@Controller
public class LoginController {

	@Autowired
	private UserService userService;

	@GetMapping("/login")
	public String showLoginForm(Model model) {
		model.addAttribute("user", new User());
		return "login";
	}

	@PostMapping("/login")
	public String login(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "login";
		}
		Optional<User> authenticatedUser = userService.authenticateUser(user.getEmail(), user.getContrasena());
		if (authenticatedUser.isPresent()) {
			// Aquí generar token JWT y crear cookie (omito por simplicidad)
			model.addAttribute("usuario", authenticatedUser.get().getEmail());
			model.addAttribute("rol", authenticatedUser.get().getRol());
			return "home";
		} else {
			model.addAttribute("error", "Credenciales incorrectas");
			return "login";
		}
	}

	@GetMapping("/register")
	public String showRegisterForm(Model model) {
		model.addAttribute("user", new User());
		return "register";
	}

	@PostMapping("/register")
	public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "register";
		}
		userService.registerUser(user);
		return "redirect:/login";
	}
}

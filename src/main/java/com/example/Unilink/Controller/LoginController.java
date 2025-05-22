package com.example.Unilink.Controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.Unilink.Modelo.User;
import com.example.Unilink.interfaces.UserRepository;
import com.example.Unilink.utils.JwtUtil;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class LoginController {

	@Autowired
	private UserRepository usuarioRepository;

	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping("/login")
	public String login(@RequestParam String email,
			@RequestParam String password,
			HttpServletResponse response,
			RedirectAttributes redirectAttributes) {

		Optional<User> userOpt = usuarioRepository.findByEmailAndContrasena(email, password);

		if (userOpt.isPresent()) {
			String token = jwtUtil.generateToken(email);

			Cookie cookie = new Cookie("jwt", token);
			cookie.setHttpOnly(true);
			cookie.setPath("/");
			cookie.setMaxAge(3600); // 1 hora
			response.addCookie(cookie);
			System.out.print("Esta entrando en el controlador ");
			int rol = userOpt.get().getRol().getIdRol().intValue();
			if (rol == 1) {
				return "redirect:/admin/dashboard";
			} else {
				return "redirect:/cliente/inicio";
			}
		} else {
				redirectAttributes.addFlashAttribute("error", "Credenciales inválidas");
				return "redirect:/entrar";
			}
		}

		@GetMapping("/logout")
		public String logout(HttpServletResponse response) {
			Cookie cookie = new Cookie("jwt", null);
			cookie.setMaxAge(0);
			cookie.setPath("/");
			response.addCookie(cookie);
			return "redirect:/entrar";
		}

		@GetMapping("/admin/dashboard")
		public String adminHome() {
			return "admin/index"; // vista admin/index.html
		}

		@GetMapping("/cliente/inicio")
		public String clienteHome() {
			return "blog/index"; // vista cliente/inicio.html
	}
}
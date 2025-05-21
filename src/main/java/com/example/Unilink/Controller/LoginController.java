package com.example.Unilink.Controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.Unilink.Modelo.User;
import com.example.Unilink.interfaces.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import com.example.Unilink.utils.JwtUtil;
@Controller
public class LoginController {
	
	@Autowired
	private UserRepository usuarioRepository;
	
	@Autowired
	private JwtUtil jwtUtil;
	String vemail="";
	String vpassword="";
	
	@PostMapping("/login")
	public String login(@RequestParam String email, @RequestParam String password, HttpServletResponse response,
			RedirectAttributes redirectAttributes, Model model) {
		vemail=email;
		vpassword=password;
		User user = usuarioRepository.findByEmailAndContrasena(email, password).orElse(null);

		if (user != null) {
			String token = jwtUtil.generateToken(email);

			Cookie cookie = new Cookie("jwt", token);
			cookie.setHttpOnly(true);
			cookie.setPath("/");
			cookie.setMaxAge(3600); // 1 hora
			response.addCookie(cookie);
			System.out.println("Cookie JWT generada para el usuario: " + email);
			int rol = user.getRol();
			if (rol == 1) {
				return "redirect:/admin/dashboard";
			} else {
                return "redirect:/cliente/inicio";
			}
		} else {
			redirectAttributes.addFlashAttribute("error", "Acceso inválido. Por favor, inténtelo otra vez.");
			return "redirect:/entrar";
		}
	}

	@GetMapping("/admin/dashboard")
	public String adminHome(Model model) {
		String displayName = getLoggedUserDisplayName();
		model.addAttribute("displayName", displayName != null ? displayName : "Usuario no identificado");
		return "admin/index";
	}

	@GetMapping("/cliente/inicio")
	public String clienteHome(Model model) {
		
		 


	  
		return "blog/index";
	}

	@GetMapping("/custom-logout")
	public String logout(HttpServletResponse response) {
		Cookie cookie = new Cookie("jwt", null);
		cookie.setHttpOnly(true);
		cookie.setPath("/");
		cookie.setMaxAge(0);
		response.addCookie(cookie);
		System.out.println("Cookie JWT eliminada al cerrar sesión");
		return "redirect:/entrar";
	}

	private String getLoggedUserDisplayName() {
		try {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			if (userDetails != null) {
				String username = userDetails.getUsername();
				return username.split("@")[0];
			}
		} catch (Exception e) {
			System.out.println("Error al obtener el usuario autenticado: " + e.getMessage());
		}
		return null;
	}
}
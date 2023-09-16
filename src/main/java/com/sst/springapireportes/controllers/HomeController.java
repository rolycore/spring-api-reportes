package com.sst.springapireportes.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api/v1")
@Tag(name = "Home",description = "Pagina de Inicio")
@SecurityRequirement(name = "bearer-key")
public class HomeController {
    @GetMapping("/home")
    public String getHomePage() {
        return "¡Bienvenido a la página de inicio!";
    }
}

package com.DecoMood.controller;

import com.DecoMood.entity.Usuario;
import com.DecoMood.service.EmailService;
import com.DecoMood.service.StrockService;
import com.DecoMood.service.UsuarioService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ControllerDecomood {

    @Autowired
    private EmailService emailService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private StrockService strockService;

    @GetMapping("/")
    public String indice(Model modelo){
        modelo.addAttribute("colecciones",strockService.listarStocks());
    return "index";
    }

    @PostMapping("/Formulario")
    public String mostrarFormulario(@ModelAttribute Usuario usuario, Model modelo) {

        boolean salida = usuarioService.registrarUsuario(usuario);

        if(salida){
            modelo.addAttribute("mensaje","Enviado correctamente");
        }else{
            modelo.addAttribute("mensajeError","El correo no es correcto!");
        }
        return "formPresupuesto";
    }

    @GetMapping("/presupuesto")
    public String hacerPresupuesto(Model modelo){
        modelo.addAttribute("muebles",strockService.listarStocks());
        return "/formPresupuesto";
    }

}


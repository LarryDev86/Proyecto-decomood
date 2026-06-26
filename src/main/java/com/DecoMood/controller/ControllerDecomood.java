package com.DecoMood.controller;

import com.DecoMood.service.ClaseEmail;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ControllerDecomood {

    @Autowired
    private ClaseEmail emialClass;

    @GetMapping("/")
    public String indice(){
        return "index";
    }

    @GetMapping("/Formulario")
    public String mostrarFormulario(){
        return "Formulario";
    }

    @GetMapping("/gestion")
    public String gestionDelFormulario(@RequestParam("nombre") String nombre,
                                     @RequestParam("email") String email,
                                     @RequestParam("descripcion") String descripcion, Model modelo) throws MessagingException {

        if(comprobarDatosFormulario(nombre, email, descripcion)){
            modelo.addAttribute("nombre",nombre);
            return "salidaOk";
        }

        return "salidaError";
    }

    public boolean comprobarDatosFormulario(String nombre, String email, String descripcion) throws MessagingException {
        if(email.contains("@") && email.contains("gmail") && email.contains(".com") || email.contains("gmail") && email.contains(".es") ||
                email.contains("hotmail") && email.contains(".com") ||  email.contains("hotmail") && email.contains(".es") ||
                email.contains("icloud") && email.contains(".com") ){
            emialClass.enviarEmail(nombre, email, descripcion);
        }else{
            return false;
        }
        return true;
    }

    @GetMapping("/presupuesto")
    public String hacerPresupuesto(){
        return "/formPresupuesto";
    }

}


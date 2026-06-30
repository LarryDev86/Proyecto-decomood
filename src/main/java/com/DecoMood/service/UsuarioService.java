package com.DecoMood.service;

import com.DecoMood.entity.Stock;
import com.DecoMood.entity.Usuario;
import com.DecoMood.repository.StockRepository;
import com.DecoMood.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private EmailService emailService;

    //Metodo para registrar todos los usuarios que solicitan un presupuesto para inventario.
    public boolean registrarUsuario(Usuario usuario){
        if(!comprobarDatosFormulario(usuario)){
            return false;
        }
        emailService.enviarEmail(usuario);
        return true;
    }

    public boolean comprobarDatosFormulario(Usuario usuario){
        if(usuario.getEmail().contains("@") && usuario.getEmail().contains("gmail") && usuario.getEmail().contains(".com") || usuario.getEmail().contains("gmail") && usuario.getEmail().contains(".es") ||
                usuario.getEmail().contains("hotmail") && usuario.getEmail().contains(".com") ||  usuario.getEmail().contains("hotmail") && usuario.getEmail().contains(".es") ||
                usuario.getEmail().contains("icloud") && usuario.getEmail().contains(".com") ){

            Stock stock = stockRepository.findById(usuario.getStock().getId()).orElseThrow();
            usuario.setStock(stock);
            usuarioRepository.save(usuario);
        }else{
            return false;
        }
        return true;
    }
}

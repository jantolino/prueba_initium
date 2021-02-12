/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prueba.atencion_al_cliente.controller;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author ja2c
 */
@Controller
public class IndexController {
    
    @RequestMapping("/index") 
    public String index() {        
        return "index";
    }
    
}

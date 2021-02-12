/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prueba.atencion_al_cliente.controller;

import com.prueba.atencion_al_cliente.domain.AtencionCliente;
import com.prueba.atencion_al_cliente.service.AtencionClienteService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ja2c
 */
@RestController
@RequestMapping("/api")
public class AtencionClienteController {

    @Autowired
    private AtencionClienteService atencionClienteService;

    @PostMapping("/guardar-ticket")
    public void guardarTicket(@RequestBody AtencionCliente atencionCliente) {
        atencionClienteService.guardarTicket(atencionCliente);
    }

    @GetMapping("/mostrar-tickets-activos")
    public List<AtencionCliente> listaTicketsPendientes() {
        return atencionClienteService.getListaTicketPendientes();
    }
}

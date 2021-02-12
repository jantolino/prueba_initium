/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prueba.atencion_al_cliente.service;

import com.prueba.atencion_al_cliente.domain.AtencionCliente;
import java.util.List;

/**
 *
 * @author ja2c
 */
public interface AtencionClienteService {

    public void guardarTicket(AtencionCliente atencionCliente);    

    public void eliminarTicket(AtencionCliente atencionCliente);

    public List<AtencionCliente> getListaTicketPendientes();
    
    public int seleccionarCola();   
    
    public void validaTiempoEnCola();
  
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prueba.atencion_al_cliente.repository;

import com.prueba.atencion_al_cliente.domain.AtencionCliente;
import java.util.List;

/**
 *
 * @author ja2c
 */
public interface AtencionClienteRepository {
    
   public void guardarTicket(AtencionCliente atencionCliente) throws Exception;
   
   public AtencionCliente getTicket(int id) throws Exception;
   
   public void eliminarTicket(AtencionCliente atencionCliente) throws Exception;
   
   public List<AtencionCliente> getListaTicketPendientes() throws Exception;
}

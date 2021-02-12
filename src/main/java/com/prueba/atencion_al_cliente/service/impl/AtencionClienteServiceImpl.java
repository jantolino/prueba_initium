/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prueba.atencion_al_cliente.service.impl;

import com.prueba.atencion_al_cliente.domain.AtencionCliente;
import com.prueba.atencion_al_cliente.repository.AtencionClienteRepository;
import com.prueba.atencion_al_cliente.service.AtencionClienteService;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 *
 * @author ja2c
 */
@Service
public class AtencionClienteServiceImpl implements AtencionClienteService {

    @Autowired
    private AtencionClienteRepository atencionClienteRepository;
    private static final Logger logger = Logger.getLogger(AtencionClienteServiceImpl.class.getName());

    @Override
    public void guardarTicket(AtencionCliente atencionCliente) {

        AtencionCliente atencionClienteLocal = null;

        try {
            atencionCliente.setLine(seleccionarCola());
            atencionCliente.setTimeMillis(System.currentTimeMillis());
            atencionClienteLocal = atencionClienteRepository.getTicket(atencionCliente.getIdClient());
            if (atencionClienteLocal != null) {
                if (!atencionClienteLocal.isActive()) {
                    logger.log(Level.INFO, "Guardando ticket del cliente");
                    atencionClienteRepository.guardarTicket(atencionCliente);
                } else {
                    logger.log(Level.WARNING, "Ya posee un ticket activo: ");
                }
            } else {
                logger.log(Level.INFO, "Guardando ticket del cliente");
                atencionClienteRepository.guardarTicket(atencionCliente);
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Ha ocurrido un error al guardar registro: ", ex.getMessage());
        }
    }

    @Override
    public void eliminarTicket(AtencionCliente atencionCliente) {
        try {
            logger.log(Level.WARNING, "Eliminando ticket del cliente: ");
            atencionClienteRepository.eliminarTicket(atencionCliente);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error: ", ex);
        }
    }

    @Override
    public List<AtencionCliente> getListaTicketPendientes() {
        List<AtencionCliente> lista = null;
        try {
            logger.log(Level.INFO, "Consultando lista de clientes");
            lista = atencionClienteRepository.getListaTicketPendientes();
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error", ex);
        }
        return lista;
    }

    @Override
    public int seleccionarCola() {

        logger.log(Level.INFO, "Seleccionando cola para el cliente");

        long tiempoActual = 0L;
        long tiempoColaUno = 2L;
        long tiempoColaDos = 3L;
        int colaSeleccionada = 0;

        for (AtencionCliente atencionCliente : getListaTicketPendientes()) {
            if (atencionCliente != null) {
                tiempoActual = System.currentTimeMillis();
                if (atencionCliente.getLine() == 1) {
                    tiempoColaUno = tiempoColaUno + atencionCliente.getDateRegister().getTime();
                } else {
                    tiempoColaDos = tiempoColaDos + atencionCliente.getDateRegister().getTime();
                }
            }
        }

        tiempoColaUno = tiempoColaUno - tiempoActual;
        tiempoColaDos = tiempoColaDos - tiempoActual;

        if (tiempoColaUno < tiempoColaDos) {
            colaSeleccionada = 1;
        } else {
            colaSeleccionada = 2;
        }
        logger.log(Level.INFO, "La cola seleccionada es: {0}", colaSeleccionada);
        return colaSeleccionada;
    }

    @Override
    public void validaTiempoEnCola() {

        logger.log(Level.INFO, "Validando tiempo en cola de los clientes");

        long tiempoTrasncurrido = 0;
        long tiempoRegistroTicket = 0;
        long tiempoActual = 0;

        for (AtencionCliente atencionCliente : getListaTicketPendientes()) {

            tiempoRegistroTicket = atencionCliente.getTimeMillis();
            tiempoActual = System.currentTimeMillis();
            tiempoTrasncurrido = (tiempoActual - tiempoRegistroTicket) /1000;           
            
            logger.log(Level.INFO, "El tiempo transcurrido del cliente{0} Es de {1}", new Object[]{atencionCliente.toString(), tiempoTrasncurrido});

            if (atencionCliente.getLine() == 1) {
                if (tiempoTrasncurrido > 120) {
                    try {
                        atencionClienteRepository.eliminarTicket(atencionCliente);
                    } catch (Exception ex) {
                        logger.log(Level.SEVERE, null, ex);
                    }
                }
            } else {
                if (tiempoTrasncurrido > 180) {
                    try {
                        atencionClienteRepository.eliminarTicket(atencionCliente);
                    } catch (Exception ex) {
                        logger.log(Level.SEVERE, null, ex);
                    }
                }
            }

        }
    }

    @Scheduled(initialDelay = 30000, fixedDelay = 30000)
    public void Task() {
        validaTiempoEnCola();
    }

}

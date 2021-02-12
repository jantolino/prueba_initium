/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prueba.atencion_al_cliente.repository.impl;

import com.prueba.atencion_al_cliente.domain.AtencionCliente;
import com.prueba.atencion_al_cliente.repository.AtencionClienteRepository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ja2c
 */
@Repository
public class AtencionClienteRepositoryImpl implements AtencionClienteRepository {

    @Autowired
    private JdbcTemplate jdbc;

    @Value("${initium.crear_ticket}")
    private String CREAR_TICKET;

    @Value("${initium.mostrar_ticket}")
    private String MOSTRAR_TICKET;

    @Value("${initium.eliminar_ticket}")
    private String ELIMINAR_TICKET;

    @Value("${initium.mostrar_tickets}")
    private String MOSTRAR_TICKETS;
    
    private static final Logger logger = Logger.getLogger(AtencionClienteRepositoryImpl.class.getName());

    @Override
    public void guardarTicket(AtencionCliente atencionCliente) throws Exception {
        logger.log(Level.INFO, "Guardando Registro {0}", atencionCliente.toString());
        jdbc.update(CREAR_TICKET,
                atencionCliente.getIdClient(),
                atencionCliente.getName(),
                atencionCliente.getLine(),
                atencionCliente.getTimeMillis()
        );
    }

    @Override
    public AtencionCliente getTicket(int idClient) throws Exception {
        
        AtencionCliente atencionCliente = null;        
        RowMapper<AtencionCliente> rowMapper = new BeanPropertyRowMapper(AtencionCliente.class);
        logger.log(Level.INFO, "Consultando cliente con id: {0}", idClient);
        try {            
            atencionCliente = jdbc.queryForObject(MOSTRAR_TICKET, rowMapper, idClient);
        } catch (EmptyResultDataAccessException ex) {
            logger.log(Level.WARNING, "No se encontro ningun resultado ", ex.getMessage());
        }
        return atencionCliente;
    }

    @Override
    public void eliminarTicket(AtencionCliente atencionCliente) throws Exception {
        logger.log(Level.INFO, "Eliminando registro: {0}", atencionCliente.toString());
        jdbc.update(ELIMINAR_TICKET, atencionCliente.getId());
    }

    @Override
    public List<AtencionCliente> getListaTicketPendientes() throws Exception {
        logger.log(Level.INFO, "Obteniendo listado de clientes con tickets activos...");
        RowMapper<AtencionCliente> rowMapper = new AtencionClienteRowMap();
        List<AtencionCliente> list = jdbc.query(MOSTRAR_TICKETS, rowMapper);
        return list;
    }

    private class AtencionClienteRowMap implements RowMapper<AtencionCliente> {

        @Override
        public AtencionCliente mapRow(ResultSet rs, int i) throws SQLException {
            AtencionCliente ac = new AtencionCliente();
            ac.setId(rs.getInt("id"));
            ac.setIdClient(rs.getInt("id_client"));
            ac.setName(rs.getString("name"));
            ac.setLine(rs.getInt("line"));
            ac.setActive((rs.getInt("active") == 1));
            ac.setTimeMillis(rs.getLong("time_millis"));
            ac.setDateRegister(rs.getTimestamp("date_register"));
            return ac;
        }
    }

}

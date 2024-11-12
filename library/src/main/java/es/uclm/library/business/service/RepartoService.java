package es.uclm.library.business.service;

import es.uclm.library.business.entity.CodigoPostal;
import es.uclm.library.business.entity.Repartidor;
import es.uclm.library.business.entity.ServicioEntrega;
import es.uclm.library.persistence.RepartidorDAO;
import es.uclm.library.persistence.ServicioEntregaDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RepartoService {

    private static final Logger logger = LoggerFactory.getLogger(RepartoService.class);

    @Autowired
    private ServicioEntregaDAO servicioEntregaDAO;

    @Autowired
    private RepartidorDAO repartidorDAO;


}

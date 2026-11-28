package com.sp.forjagym.service.cliente.impl;

import com.sp.forjagym.entity.cliente.ClienteEntity;
import com.sp.forjagym.mapper.cliente.ClienteMapper;
import com.sp.forjagym.model.cliente.Cliente;
import com.sp.forjagym.Repository.cliente.ClienteRepository;
import com.sp.forjagym.service.cliente.ClienteService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService{
    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;

    public ClienteServiceImpl(ClienteRepository clienteRepository, ClienteMapper clienteMapper) {
        this.clienteRepository = clienteRepository;
        this.clienteMapper = clienteMapper;
    }

    @Override
    public Cliente registrar(Cliente cliente) {
        if (cliente.getNombre() == null || cliente.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre del cliente es obligatorio");
        }
        if (clienteRepository.existsByCorreo(cliente.getCorreo())) {
            throw new IllegalArgumentException("Ya existe un cliente con ese correo");
        }

        if (clienteRepository.existsByDni(cliente.getDni())) {
            throw new IllegalArgumentException("Ya existe un cliente con ese documento");
        }

        ClienteEntity guardado = clienteRepository.save(clienteMapper.toEntity(cliente));
        return clienteMapper.toModel(guardado);
    }

    @Override
    public List<Cliente> listarTodos() {
        return clienteMapper.toModelList(clienteRepository.findAll());
    }

    @Override
    public Cliente buscarPorId(Long idCliente) {
        return clienteMapper.toModel(buscarEntidad(idCliente));
    }

    @Override
    public boolean existeEmail(String correo) {
        return clienteRepository.existsByCorreo(correo);
    }

    @Override
    public Cliente buscarPorEmail(String correo) {
        return clienteRepository.findByCorreo(correo)
                .map(clienteMapper::toModelParaLogin)
                .orElse(null);
    }

    @Override
    public Cliente actualizarPerfil(Long idCliente, String nombre, String apellido, String telefono, String contactoEmergencia, String correo) {
        ClienteEntity cliente = buscarEntidad(idCliente);
        if (!cliente.getCorreo().equals(correo) && clienteRepository.existsByCorreo(correo)) {
            throw new IllegalArgumentException("Ya existe un cliente con ese correo");
        }
        cliente.setNombre(nombre);
        cliente.setApellido(apellido);
        cliente.setCorreo(correo);
        cliente.setContactoEmergencia(contactoEmergencia);
        cliente.setTelefono(telefono);
        return clienteMapper.toModel(clienteRepository.save(cliente));
    }

    @Override
    public boolean existeNumDocumento(String dni) {
        return clienteRepository.existsByDni(dni);
    }

    private ClienteEntity buscarEntidad(Long idCliente) {
        return clienteRepository.findById(idCliente)
                .orElseThrow(() -> new IllegalArgumentException("El cliente no existe"));
    }
}


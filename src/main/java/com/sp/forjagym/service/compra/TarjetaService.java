package com.sp.forjagym.service.compra;

import com.sp.forjagym.entity.cliente.TarjetaEntity;
import com.sp.forjagym.model.cliente.Tarjeta;
import java.util.List;

public interface TarjetaService {
    Tarjeta guardar(Long idCliente, TarjetaEntity tarjetaEntity);
    Tarjeta buscarPorId(Long id);
    Tarjeta buscarPorNumero(String numeroTarjeta);
    List<Tarjeta> listar();
    //List<Tarjeta> listarPorCliente(Long idCliente);
    //void descartarTarjeta(Long idCliente, Long idTarjeta);
    void vincular(Long idCliente, Long idTarjeta);
    Tarjeta validarPago(Long idCliente, String numero, String titular, String vencimiento, String cvv);
}
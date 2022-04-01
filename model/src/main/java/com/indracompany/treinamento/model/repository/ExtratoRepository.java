package com.indracompany.treinamento.model.repository;

import com.indracompany.treinamento.model.entity.EntradaExtrato;

import java.util.Date;
import java.util.List;

public interface ExtratoRepository extends GenericCrudRepository<EntradaExtrato, Long> {

    List<EntradaExtrato> findExtratoByClienteAndDataHoraBetween(Long idCliente, Date dataInicial, Date dataFinal);
}

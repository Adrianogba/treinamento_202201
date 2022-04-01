package com.indracompany.treinamento.model.service;

import com.indracompany.treinamento.exception.AplicacaoException;
import com.indracompany.treinamento.exception.ExceptionValidacoes;
import com.indracompany.treinamento.model.dto.EntradaExtratoDTO;
import com.indracompany.treinamento.model.entity.Cliente;
import com.indracompany.treinamento.model.entity.EntradaExtrato;
import com.indracompany.treinamento.model.repository.ExtratoRepository;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExtratoService extends GenericCrudService<EntradaExtrato, Long, ExtratoRepository>{
	
	public List<EntradaExtratoDTO> getExtrato(long clientId, String dataInicial, String dataFinal) throws AplicacaoException {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		Date dateDataInicial;
		Date dateDataFinal;

		try {
			dateDataInicial = formatter.parse(dataInicial);
			dateDataFinal = formatter.parse(dataFinal);
		} catch (ParseException e) {
			throw new AplicacaoException(ExceptionValidacoes.ERRO_DATA_INVALIDA);
		}

		List<EntradaExtratoDTO> extrato = repository.findExtratoByClienteAndDataHoraBetween(clientId, dateDataInicial, dateDataFinal)
			.stream()
			.map(e -> new EntradaExtratoDTO(e.getOperacao().name(), e.getDescricao(), e.getDataHora(), e.getCliente().getId()))
			.collect(Collectors.toList());

		if (extrato.isEmpty()) {
			throw new AplicacaoException(ExceptionValidacoes.ERRO_EXTRATO_VAZIO);
		}

		return extrato;
	}

	public void salvarEntradaExtrato(Cliente cliente, EntradaExtrato.OperacoesEnum operacao, String descricao) {
		EntradaExtrato entradaExtrato = new EntradaExtrato();
		entradaExtrato.setOperacao(operacao);
		entradaExtrato.setDescricao(descricao);
		entradaExtrato.setCliente(cliente);
		entradaExtrato.setDataHora(new Date());
		super.salvar(entradaExtrato);
	}

}

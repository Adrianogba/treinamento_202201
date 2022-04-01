package com.indracompany.treinamento.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class EntradaExtratoDTO implements Serializable{

	public EntradaExtratoDTO(String operacao, String descricao, Date dataHora, Long clienteId) {
		this.operacao = operacao;
		this.descricao = descricao;
		this.dataHora = dataHora;
		this.clienteId = clienteId;
	}
	
	private static final long serialVersionUID = 7241294582609797095L;

	private String operacao;
	
	private String descricao;
	
	private Date dataHora;
	
	private Long clienteId;


}

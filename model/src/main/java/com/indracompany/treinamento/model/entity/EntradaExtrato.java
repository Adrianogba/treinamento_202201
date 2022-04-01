package com.indracompany.treinamento.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;


//Coloquei o nome extrato_adrianogba para não conflitar com as tabelas que os outros do curso fossem criar.
@Entity
@Table(name = "extrato_adrianogba")
@Data
@EqualsAndHashCode(callSuper = true)
public class EntradaExtrato extends GenericEntity<Long>{

	public enum OperacoesEnum {
		CONSULTA_SALDO, TRANSFERENCIA_ENVIADA_SUCESSO, TRANSFERENCIA_NAO_ENVIADA, TRANSFERENCIA_RECEBIDA, DEPOSITO, SAQUE, EXTRATO
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	private OperacoesEnum operacao;

	@Column(length = 255)
	private String descricao;

	@Temporal(TemporalType.DATE)
	private Date dataHora;

	@ManyToOne
	@JoinColumn(name = "fk_cliente_id")
	private Cliente cliente;
	
}

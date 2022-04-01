package com.indracompany.treinamento.model.repository;

import com.indracompany.treinamento.model.entity.Cliente;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClienteRepository extends GenericCrudRepository<Cliente, Long>{
	
	Cliente findByCpf(String cpf);

	List<Cliente> findByNomeStartingWith(String nome);

	@Query(value = "select cli.* from contas con, clientes cli where con.fk_cliente_id=cli.id and con.agencia = :agencia and con.numero = :numero", nativeQuery = true)
	Cliente findByAgenciaAndNumeroConta(
		@Param("agencia") String agencia,
		@Param("numero") String numero
	);
	

}

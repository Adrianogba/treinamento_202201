package com.indracompany.treinamento.controller.rest;

import com.indracompany.treinamento.model.dto.*;
import com.indracompany.treinamento.model.entity.EntradaExtrato;
import com.indracompany.treinamento.model.service.ExtratoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("rest/extrato")
public class ExtratoRestGeneric extends EmptyGenericCrudRest<EntradaExtrato, Long, ExtratoService> {
	
	@Autowired
	private ExtratoService extratoService;

	@GetMapping(value = "/consultar-extrato/{clientId}/{dataInicial}/{dataFinal}", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<List<EntradaExtratoDTO>> consultarContasPorCpf(
			@PathVariable Long clientId,
			@PathVariable String dataInicial,
			@PathVariable String dataFinal
	){
		List<EntradaExtratoDTO> extratoList = extratoService.getExtrato(clientId, dataInicial, dataFinal);
		return new ResponseEntity<List<EntradaExtratoDTO>>(extratoList, HttpStatus.OK);
	}
}

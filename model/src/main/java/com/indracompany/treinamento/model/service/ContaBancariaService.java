package com.indracompany.treinamento.model.service;

import com.indracompany.treinamento.exception.AplicacaoException;
import com.indracompany.treinamento.exception.ExceptionValidacoes;
import com.indracompany.treinamento.model.dto.ConsultaContaBancariaDTO;
import com.indracompany.treinamento.model.dto.TransferenciaBancariaDTO;
import com.indracompany.treinamento.model.entity.Cliente;
import com.indracompany.treinamento.model.entity.ContaBancaria;
import com.indracompany.treinamento.model.repository.ContaBancariaRepository;
import com.indracompany.treinamento.util.ExtratoUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class ContaBancariaService extends GenericCrudService<ContaBancaria, Long, ContaBancariaRepository> {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ExtratoService extratoService;

    public double consultarSaldo(String agencia, String numero) {
        ContaBancaria c = consultarConta(agencia, numero);
        Cliente cliente = c.getCliente();
        new ExtratoUtils().registrarConsulta(extratoService, cliente, agencia, numero);
        return c.getSaldo();
    }

    public void depositar(String agencia, String numeroConta, double valor) {
        ContaBancaria conta = consultarConta(agencia, numeroConta);
        Cliente cliente = conta.getCliente();

        conta.setSaldo(conta.getSaldo() + valor);

        new ExtratoUtils().registrarDeposito(extratoService, cliente, valor, agencia, numeroConta);

        super.salvar(conta);
    }

    public void sacar(String agencia, String numeroConta, double valor) {
        ContaBancaria conta = consultarConta(agencia, numeroConta);
        Cliente cliente = conta.getCliente();

        if (conta.getSaldo() < valor) {
            new ExtratoUtils().registrarSaqueSaldoInsuficiente(extratoService, cliente, valor, agencia, numeroConta);

            throw new AplicacaoException(ExceptionValidacoes.ERRO_SALDO_INEXISTENTE);
        }

        conta.setSaldo(conta.getSaldo() - valor);
        new ExtratoUtils().registrarSaqueBemSucedido(extratoService, cliente, valor, agencia, numeroConta);

        super.salvar(conta);
    }

    @Transactional(rollbackFor = Exception.class)
    public void transferir(TransferenciaBancariaDTO dto) {

        ContaBancaria conta = consultarConta(dto.getAgenciaOrigem(), dto.getNumeroContaOrigem());
        double valor = dto.getValor();
        Cliente clienteOrigem = clienteService.buscarCliente(dto.getAgenciaOrigem(), dto.getNumeroContaOrigem());

        if (conta.getSaldo() < valor) {
            new ExtratoUtils().registrarTransferenciaSaldoInsuficiente(extratoService, clienteOrigem, valor, dto.getAgenciaOrigem(), dto.getNumeroContaOrigem());
            throw new AplicacaoException(ExceptionValidacoes.ERRO_SALDO_INEXISTENTE);
        }

        conta.setSaldo(conta.getSaldo() - valor);
        this.depositar(dto.getAgenciaDestino(), dto.getNumeroContaDestino(), dto.getValor());

        Cliente clienteDestino = clienteService.buscarCliente(dto.getAgenciaDestino(), dto.getNumeroContaDestino());

        new ExtratoUtils().registrarTransferenciaComSucesso(
            extratoService,
            clienteOrigem,
            clienteDestino,
            valor,
            dto.getAgenciaOrigem(),
            dto.getNumeroContaOrigem(),
            dto.getAgenciaDestino(),
            dto.getNumeroContaDestino()
        );

    }

    public ContaBancaria consultarConta(String agencia, String numeroConta) {
        ContaBancaria c = repository.findByAgenciaAndNumero(agencia, numeroConta);

        if (c == null) {
            throw new AplicacaoException(ExceptionValidacoes.ERRO_CONTA_INVALIDA);
        }

        return c;
    }

    public List<ConsultaContaBancariaDTO> obterContasPorCpf(String cpf) {

        List<ConsultaContaBancariaDTO> listaContasRetorno = new ArrayList<>();
        Cliente cli = clienteService.buscarCliente(cpf);

        List<ContaBancaria> listaContasCliente = repository.findByCliente(cli);
        for (ContaBancaria conta : listaContasCliente) {
            ConsultaContaBancariaDTO dtoConta = new ConsultaContaBancariaDTO();
            BeanUtils.copyProperties(conta, dtoConta);
            dtoConta.setCpf(conta.getCliente().getCpf());
            dtoConta.setNomeTitular(conta.getCliente().getNome());
            listaContasRetorno.add(dtoConta);
        }


        return listaContasRetorno;
    }
}

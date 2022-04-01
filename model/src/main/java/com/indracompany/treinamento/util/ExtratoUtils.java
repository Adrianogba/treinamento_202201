package com.indracompany.treinamento.util;

import com.indracompany.treinamento.model.entity.Cliente;
import com.indracompany.treinamento.model.entity.EntradaExtrato;
import com.indracompany.treinamento.model.service.ExtratoService;

public class ExtratoUtils {

    public void registrarConsulta(ExtratoService extratoService, Cliente cliente, String agencia, String numero) {
        extratoService.salvarEntradaExtrato(
            cliente,
            EntradaExtrato.OperacoesEnum.CONSULTA_SALDO,
            "Consulta de saldo efetuada da conta " + agencia + "-" + numero
        );
    }
    public void registrarDeposito(ExtratoService extratoService, Cliente cliente, double valor, String agencia, String numero) {
        extratoService.salvarEntradaExtrato(
                cliente,
                EntradaExtrato.OperacoesEnum.DEPOSITO,
                "Deposito no valor de " + valor +
                        " efetuado com sucesso em sua conta " + agencia + "-" + numero
        );
    }

    public void registrarSaqueSaldoInsuficiente(ExtratoService extratoService, Cliente cliente, double valor, String agencia, String numero) {
        extratoService.salvarEntradaExtrato(
                cliente,
                EntradaExtrato.OperacoesEnum.SAQUE,
                "Tentativa de saque no valor de " + valor +
                        " sem sucesso devido a saldo insuficiente em sua conta " + agencia + "-" + numero + "."
        );
    }

    public void registrarSaqueBemSucedido(ExtratoService extratoService, Cliente cliente, double valor, String agencia, String numero) {
        extratoService.salvarEntradaExtrato(
                cliente,
                EntradaExtrato.OperacoesEnum.SAQUE,
                "Tentativa de saque no valor de " + valor +
                        " sem sucesso devido a saldo insuficiente em sua conta " + agencia + "-" + numero + "."
        );
    }

    public void registrarTransferenciaSaldoInsuficiente(ExtratoService extratoService, Cliente cliente, double valor, String agencia, String numero) {
        extratoService.salvarEntradaExtrato(
                cliente,
                EntradaExtrato.OperacoesEnum.TRANSFERENCIA_NAO_ENVIADA,
                "Tentativa de transferencia no valor de " + valor +
                        " sem sucesso devido a saldo insuficiente em sua conta " +
                        agencia + "-" + numero + "."
        );
    }
    public void registrarTransferenciaComSucesso(ExtratoService extratoService, Cliente clienteOrigem, Cliente clienteDestino, double valor, String agenciaOrigem, String numeroOrigem, String agenciaDestino, String numeroDestino) {
        extratoService.salvarEntradaExtrato(
                clienteOrigem,
                EntradaExtrato.OperacoesEnum.TRANSFERENCIA_ENVIADA_SUCESSO,
                "Transferencia no valor de " + valor + " enviada de sua conta " +
                        agenciaOrigem + "-" + numeroOrigem +
                        " para a conta " + agenciaDestino + "-" + numeroDestino +
                        " pertencente a " + clienteDestino.getNome() + "."
        );
        extratoService.salvarEntradaExtrato(
                clienteDestino,
                EntradaExtrato.OperacoesEnum.TRANSFERENCIA_RECEBIDA,
                "Transferencia no valor de " + valor + " recebida em sua conta " +
                        agenciaDestino + "-" + numeroDestino +
                        " vinda da conta " + agenciaOrigem + "-" + numeroOrigem +
                        " pertencente a " + clienteOrigem.getNome() + "."
        );
    }

}

import { Component, OnInit } from '@angular/core';
import {ContasService} from "../../services/contas.service";
import {IConta} from "../../interfaces/conta";
import Swal from "sweetalert2";
import {ISaque} from "../../interfaces/saque";
import {ICliente} from "../../interfaces/cliente";
import {IDeposito} from "../../interfaces/deposito";
import {ITransferencia} from "../../interfaces/transferencia";

@Component({
  selector: 'app-contas',
  templateUrl: './contas.component.html',
  styleUrls: ['./contas.component.css']
})
export class ContasComponent implements OnInit {

  constructor(private contasService: ContasService) { }

  ngOnInit(): void {
    this.listarTodos();
  }

  contas: IConta[] = [];

  listarTodos() {
    this.contasService.listarTodasContas().subscribe((result: IConta[]) => {
      this.contas = result;
      console.log(this.contas);
    });
  }

  saque(conta: IConta) {
    Swal.fire({
      title: 'Insira o valor a ser sacado',
      input: 'number',
      inputAttributes: {
        autocapitalize: 'off'
      },
      showCancelButton: true,
      confirmButtonText: 'Saque',
      showLoaderOnConfirm: true,
      preConfirm: (valor) => {
        const saque: ISaque = {
          agencia: conta.agencia,
          numeroConta: conta.numero,
          valor: valor
        }
        return this.contasService.saque(saque).subscribe(result => {
          Swal.fire({
            title: 'Tudo certo!',
            text: 'Saque efetuado com sucesso.',
            icon: 'success',
            timer: 3000,
            timerProgressBar: true,
          });
          this.listarTodos();
        }, error => {
          console.log(error);
          Swal.fire({
            title: 'Deu ruim!',
            text: 'Saque não foi possível.',
            icon: 'error',
            timer: 3000,
            timerProgressBar: true,
          });
        })
      },
      allowOutsideClick: () => !Swal.isLoading()
    })
  }
  deposito(conta: IConta) {

    Swal.fire({
      title: 'Insira o valor a ser depositado',
      input: 'number',
      inputAttributes: {
        autocapitalize: 'off'
      },
      showCancelButton: true,
      confirmButtonText: 'Deposito',
      showLoaderOnConfirm: true,
      preConfirm: (valor) => {
        const saque: IDeposito = {
          agencia: conta.agencia,
          numeroConta: conta.numero,
          valor: valor
        }
        return this.contasService.deposito(saque).subscribe(result => {
          Swal.fire({
            title: 'Tudo certo!',
            text: 'Deposito efetuado com sucesso.',
            icon: 'success',
            timer: 3000,
            timerProgressBar: true,
          });
          this.listarTodos();
        }, error => {
          console.log(error);
          Swal.fire({
            title: 'Deu ruim!',
            text: 'Depósito não foi possível.',
            icon: 'error',
            timer: 3000,
            timerProgressBar: true,
          });
        })
      },
      allowOutsideClick: () => !Swal.isLoading()
    })

  }
  transferenciaSelecionarValor(contaOrigem: IConta, contas: IConta[]) {
    Swal.fire({
      title: 'Primeiro insira o valor a ser transferido',
      footer: 'o valor maximo para esta transferencia é ' + contaOrigem.saldo,
      input: 'number',
      inputAttributes: {
        autocapitalize: 'off',
      },
      showCancelButton: true,
      confirmButtonText: 'Selecionar conta',
      showLoaderOnConfirm: true,
      preConfirm: (valor) => {
        this.transferenciaSelecionarConta(contaOrigem, contas, valor)
      }
    })
  }

  async transferenciaSelecionarConta(contaOrigem: IConta, contas: IConta[], valor: number) {

    const swalWithBootstrapButtons = Swal.mixin({
      customClass: {
        confirmButton: 'btn btn-success',
        cancelButton: 'btn btn-danger'
      },
      buttonsStyling: false
    })

    const result = new Map(contas.map(i => [i.id, 'id:'+i.id+' Agencia ' + i.agencia + ', numero ' + i.numero]));

    swalWithBootstrapButtons.fire({
      title: 'Selecione a conta de destino',
      text: `O valor de ${valor} será transferido para a conta selecionada`,
      input: 'select',
      inputOptions: result,
      inputPlaceholder: 'Selecione a conta',
      showCancelButton: true,
      confirmButtonText: 'Transferir',
      cancelButtonText: 'Alterar valor',
    }).then((result) => {
      if (result.isConfirmed) {
        let contaDestino = contas.find((obj) => {
          return obj.id.toString() === result.value.toString();
        });
        if (contaDestino == undefined) {
          return
        }
        const transferencia: ITransferencia = {
          agenciaDestino: contaDestino.agencia,
          agenciaOrigem: contaOrigem.agencia,
          numeroContaDestino: contaDestino.numero,
          numeroContaOrigem: contaOrigem.numero,
          valor: valor,
        }
        this.contasService.transferencia(transferencia).subscribe(result => {
          Swal.fire({
            title: 'Tudo certo!',
            text: 'Transferencia realizada com sucesso.',
            icon: 'success',
            timer: 3000,
            timerProgressBar: true,
          });
          this.listarTodos();
        }, error => {
          console.log(error);
          Swal.fire({
            title: 'Deu ruim!',
            text: 'Transferencia não foi possível.',
            icon: 'error',
            timer: 3000,
            timerProgressBar: true,
          });
        })


      } else if (result.dismiss === Swal.DismissReason.cancel) {
        this.transferenciaSelecionarValor(contaOrigem, contas)
      }
    })
  }

}

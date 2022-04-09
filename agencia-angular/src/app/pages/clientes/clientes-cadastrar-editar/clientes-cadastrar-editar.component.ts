import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {ICliente} from "../../../interfaces/cliente";
import {ClientesService} from "../../../services/clientes.service";
import Swal from 'sweetalert2';
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-clientes-cadastrar-editar',
  templateUrl: './clientes-cadastrar-editar.component.html',
  styleUrls: ['./clientes-cadastrar-editar.component.css']
})
export class ClientesCadastrarEditarComponent implements OnInit {

  emptyCliente: ICliente = {
   id: null,
   nome: '',
   cpf: '',
   email: '',
   observacoes: '',
   ativo: true
  }

  formCliente: FormGroup = this.preencherFormGroup(this.emptyCliente, false);

  constructor(
    private clienteService: ClientesService,
    private router: Router,
    private activatedRoute: ActivatedRoute
  ) { }

  ngOnInit(): void {
    const id = this.activatedRoute.snapshot.paramMap.get('id');
    if (Number(id) && id != null) {
      this.clienteService.buscarPorId(id).subscribe( result => {
        this.formCliente = this.preencherFormGroup(result, true)
      })
    }
  }

  preencherFormGroup(cliente: ICliente, disableCpf: boolean): FormGroup  {
    return new FormGroup({
      id: new FormControl(cliente.id),
      nome: new FormControl(cliente.nome, Validators.required),
      cpf: new FormControl({value: cliente.cpf, disabled: disableCpf}, Validators.required),
      email: new FormControl(cliente.email, [Validators.required, Validators.email]),
      observacoes: new FormControl(cliente.observacoes),
      ativo: new FormControl(cliente.ativo),
    })
  }

  estaEditando(): boolean {
    return !this.formCliente.get('id')?.value
  }

  enviar() {
    const cliente: ICliente = this.formCliente.value;
    this.clienteService.cadastrarEditar(cliente).subscribe(result => {
      Swal.fire({
        icon: 'success',
        title: `${cliente.nome} cadastrado com sucesso!`,
        showConfirmButton: false,
        timer: 1500
      })
      this.router.navigate(['/clientes']);

    })

  }



}

import {Component, OnInit} from '@angular/core';
import {ClientesService} from "../../services/clientes.service";

@Component({
  selector: 'app-clientes',
  templateUrl: './clientes.component.html',
  styleUrls: ['./clientes.component.css']
})
export class ClientesComponent implements OnInit {

  constructor(private clienteService: ClientesService) {
  }

  ngOnInit(): void {
    this.listarTodos();
  }

  clientes: any[] = [];

  listarTodos() {
    this.clienteService.listarTodosClientes().subscribe((result: any) => {
      this.clientes = result;
      console.log(this.clientes);
    });
  }

}
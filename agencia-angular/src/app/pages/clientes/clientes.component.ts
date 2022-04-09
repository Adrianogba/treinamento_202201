import {Component, OnInit} from '@angular/core';
import {ClientesService} from "../../services/clientes.service";
import {ICliente} from "../../interfaces/cliente";
import Swal from "sweetalert2";

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

  clientes: ICliente[] = [];

  listarTodos() {
    this.clienteService.listarTodos().subscribe((result: ICliente[]) => {
      this.clientes = result;
      console.log(this.clientes);
    });
  }

  confirmarRemover(cliente: ICliente) {
    Swal.fire({
      title: 'Remover cliente',
      text: `Tem certeza que deseja remover ${cliente.nome}?`,
      // icon: 'warning',
      imageUrl: 'https://i.pinimg.com/originals/24/37/8d/24378d3680edd2ee5532f19530608fe5.gif',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Sim, desejo remove-lo(a)',
      // backdrop: `
      //   #FFFFFF
      //   url("https://i.pinimg.com/originals/24/37/8d/24378d3680edd2ee5532f19530608fe5.gif")
      //   left top
      //   no-repeat
      // `
    }).then((result) => {
      if (result.isConfirmed) {
        this.clienteService.remover(cliente.id).subscribe(result => {
          Swal.fire({
            title: 'Tudo certo!',
            text: 'Cliente removido com sucesso.',
            icon: 'success',
            timer: 3000,
            timerProgressBar: true,
          });
          this.listarTodos();
        }, error => {
          console.log(error);
          Swal.fire({
            title: 'Deu ruim!',
            text: 'Cliente não foi removido.',
            icon: 'error',
            timer: 3000,
            timerProgressBar: true,
          });
        })
      }
    })
  }

}

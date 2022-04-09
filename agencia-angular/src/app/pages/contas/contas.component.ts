import { Component, OnInit } from '@angular/core';
import {ContasService} from "../../services/contas.service";
import {IConta} from "../../interfaces/conta";

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

}

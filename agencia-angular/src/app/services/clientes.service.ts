import { Injectable } from '@angular/core';
import { environment } from "../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {ICliente} from "../interfaces/cliente";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ClientesService {
  api = environment.api;
  endpoint = 'clientes';
  constructor(private http: HttpClient) { }

  listarTodos() {
    return this.http.get<ICliente[]>(`${this.api}/${this.endpoint}/`);
  }

  buscarPorId(idCliente: string): Observable<ICliente> {
    return this.http.get<ICliente>(`${this.api}/${this.endpoint}/${idCliente}/`);
  }

  cadastrarEditar(cliente: ICliente) {
    if (cliente.id != null) {
      return this.http.post(`${this.api}/${this.endpoint}/`, cliente);
    } else {
      return this.http.put(`${this.api}/${this.endpoint}/${cliente.id}/`, cliente);
    }
  }

  remover(idCliente: number) {
    return this.http.delete(`${this.api}/${this.endpoint}/${idCliente}/`);
  }
}

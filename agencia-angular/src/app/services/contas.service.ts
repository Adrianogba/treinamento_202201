import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {IConta} from "../interfaces/conta";
import {Observable} from "rxjs";
import {ICliente} from "../interfaces/cliente";
import {ISaque} from "../interfaces/saque";
import {IDeposito} from "../interfaces/deposito";
import {ITransferencia} from "../interfaces/transferencia";

@Injectable({
  providedIn: 'root'
})
export class ContasService {
  api = environment.api;
  endpoint = 'contas';
  transferenciaEndpoint = `${this.endpoint}/transferencia`;
  saqueEndpoint = `${this.endpoint}/saque`;
  depositoEndpoint = `${this.endpoint}/deposito`;
  constructor(private http: HttpClient) { }

  listarTodasContas() {
    return this.http.get<IConta[]>(`${this.api}/${this.endpoint}/`);
  }

  saque(saque: ISaque) {
    return this.http.put(`${this.api}/${this.saqueEndpoint}/`, saque);
  }

  deposito(deposito: IDeposito) {
    return this.http.put(`${this.api}/${this.depositoEndpoint}/`, deposito);
  }

  transferencia(transferencia: ITransferencia) {
    return this.http.put(`${this.api}/${this.transferenciaEndpoint}/`, transferencia);
  }
}

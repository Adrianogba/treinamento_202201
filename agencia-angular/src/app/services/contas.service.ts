import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class ContasService {
  api = environment.api;
  endpoint = 'contas';
  constructor(private http: HttpClient) { }

  listarTodasContas() {
    return this.http.get(`${this.api}/${this.endpoint}/`);
  }
}

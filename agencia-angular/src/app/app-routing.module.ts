import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {ClientesComponent} from "./pages/clientes/clientes.component";
import {ContasComponent} from "./pages/contas/contas.component";
import {ClientesCadastrarEditarComponent} from "./pages/clientes/clientes-cadastrar-editar/clientes-cadastrar-editar.component";

const routes: Routes = [
  {path: 'clientes', component: ClientesComponent},
  {path: 'contas', component: ContasComponent},
  {path: 'clientes/cadastrar', component: ClientesCadastrarEditarComponent},
  {path: 'clientes/editar/:id', component: ClientesCadastrarEditarComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

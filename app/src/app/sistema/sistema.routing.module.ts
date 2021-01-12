import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {SetorComponent} from './setor/setor.component';
import {OcorrenciaComponent} from './ocorrencia/ocorrencia.component';
import {UsuarioComponent} from './usuario/usuario.component';
import {OrganogramaComponent} from './setor/organograma/organograma.component';
import {ClienteComponent} from './cliente/cliente.component';
import {VisaoGeralComponent} from './visao-geral/visao-geral.component';
import {VersaoComponent} from './versao/versao.component';
import {TicketComponent} from './ticket/ticket.component';
import {ClientePesquisaComponent} from './cliente/cliente-pesquisa/cliente-pesquisa.component';

const routes: Routes = [
  {path: 'geral', component: VisaoGeralComponent , data: { animation: '' }},
  {path: 'setor', component: SetorComponent , data: { animation: 'setor' }},
  {path: 'setor/organograma', component: OrganogramaComponent , data: { animation: 'organograma' }},
  {path: 'ocorrencias', component: OcorrenciaComponent, data: { animation: 'oco' }},
  {path: 'usuarios', component: UsuarioComponent, data: { animation: 'use' }},
  {path: 'cliente', component: ClienteComponent , data: { animation: 'cliente' }},
  {path: 'cliente/:codCliente', component: ClienteComponent , data: { animation: 'cliente_cod' }},
  {path: 'clientes/versao/:versao', component: ClientePesquisaComponent , data: { animation: 'clientePesquisa' }},
  {path: 'versao', component: VersaoComponent , data: { animation: 'versao' }},
  {path: 'ticket', component: TicketComponent , data: { animation: 'ticket' }}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SistemaRoutingModule {
}

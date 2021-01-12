import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {SetorComponent} from './setor/setor.component';
import {SharedModule} from '../shared/shared.module';
import {SistemaRoutingModule} from './sistema.routing.module';
import {SetorPesquisaComponent} from './setor/setor-pesquisa/setor-pesquisa.component';
import {OcorrenciaComponent} from './ocorrencia/ocorrencia.component';
import {OcorrenciaPesquisaComponent} from './ocorrencia/ocorrencia-pesquisa/ocorrencia-pesquisa.component';
import {UsuarioComponent} from './usuario/usuario.component';
import {OrganogramaComponent} from './setor/organograma/organograma.component';
import {OrganizationChartModule, ScrollPanelModule} from 'primeng/primeng';
import {UsuarioPesquisaComponent} from './usuario/usuario-pesquisa/usuario-pesquisa.component';
import {ClienteComponent} from './cliente/cliente.component';
import {ContatoComponent} from './cliente/contato/contato.component';
import {ClientePesquisaComponent} from './cliente/cliente-pesquisa/cliente-pesquisa.component';
import {VisaoGeralComponent} from './visao-geral/visao-geral.component';
import {VersaoComponent} from './versao/versao.component';
import {TicketComponent} from './ticket/ticket.component';
import {EstatisticaVersaoComponent} from './pesquisas/estatistica-versao/estatistica-versao.component';
import {ChamadosComponent} from './pesquisas/chamados/chamados.component';
import {ChamadoGridViewComponent} from './pesquisas/chamados/chamado-grid-view/chamado-grid-view.component';
import {PesquisaFilterComponent} from './pesquisas/pesquisa-filter/pesquisa-filter.component';
import {VirtualScrollerModule} from 'ngx-virtual-scroller';
import { ChamadoListViewComponent } from './pesquisas/chamados/chamado-list-view/chamado-list-view.component';
import { AnexosComponent } from './ticket/anexos/anexos.component';
import { ComentarioUsuarioComponent } from './ticket/comentario-usuario/comentario-usuario.component';

@NgModule({
  declarations: [SetorComponent, SetorPesquisaComponent, OcorrenciaComponent, OcorrenciaPesquisaComponent,
    UsuarioComponent,
    OrganogramaComponent,
    UsuarioPesquisaComponent,
    ClienteComponent,
    ContatoComponent,
    ClientePesquisaComponent,
    VisaoGeralComponent,
    VersaoComponent,
    TicketComponent,
    EstatisticaVersaoComponent,
    ChamadosComponent,
    ChamadoGridViewComponent,
    PesquisaFilterComponent,
    ChamadoListViewComponent,
    AnexosComponent,
    ComentarioUsuarioComponent],
    imports: [
        CommonModule,
        SharedModule,
        SistemaRoutingModule,
        OrganizationChartModule,
        VirtualScrollerModule,
        ScrollPanelModule
    ]
    , entryComponents: [
    SetorPesquisaComponent,
    PesquisaFilterComponent,
    VersaoComponent,
    TicketComponent,
    ClienteComponent,
    OcorrenciaPesquisaComponent,
    ClientePesquisaComponent,
    ContatoComponent,
    UsuarioPesquisaComponent
  ]
})
export class SistemaModule {
}

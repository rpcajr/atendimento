import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule} from '@angular/router';

import {AppLayoutComponent} from './app.layout.component';
import {AppLayoutFooterComponent} from './app.layout.footer.component';
import {AppLayoutMenuComponent, AppSubMenuComponent} from './app.layout.menu.component';
import {AppLayoutSidebarComponent} from './app.layout.sidebar.component';
import {AppLayoutSidebartabcontentComponent} from './app.layout.sidebartabcontent.component';
import {AppLayoutTopbarComponent} from './top/app.layout.topbar.component';
import {ScrollPanelModule} from 'primeng/primeng';
import {LayoutRoutingModule} from './layout.routing.module';
import {SharedModule} from '../shared/shared.module';
import {PerfilComponent} from './perfil/perfil.component';
import {InjectableRxStompConfig, RxStompService, rxStompServiceFactory} from '@stomp/ng2-stompjs';
import {myRxStompConfig} from './my-rx-stomp.config';
import { AtendimentoComponent } from './atendimento/atendimento.component';
import { AtendimentoViewComponent } from './atendimento/atendimento-view/atendimento-view.component';
import { ListaversaoComponent } from './listaversao/listaversao.component';
import { VersaoViewComponent } from './listaversao/versao-view/versao-view.component';
import { BackupComponent } from './backup/backup.component';

@NgModule({
  declarations: [
    AppLayoutComponent,
    AppLayoutFooterComponent,
    AppLayoutMenuComponent,
    AppLayoutSidebarComponent,
    AppLayoutSidebartabcontentComponent,
    AppLayoutTopbarComponent,
    AppSubMenuComponent,
    PerfilComponent,
    AtendimentoComponent,
    AtendimentoViewComponent,
    ListaversaoComponent,
    VersaoViewComponent,
    BackupComponent
  ],
  imports: [
    CommonModule,
    RouterModule,
    ScrollPanelModule,
    LayoutRoutingModule,
    SharedModule
  ], providers: [
    {
      provide: InjectableRxStompConfig,
      useValue: myRxStompConfig
    },
    {
      provide: RxStompService,
      useFactory: rxStompServiceFactory,
      deps: [InjectableRxStompConfig]
    }
  ]
})
export class LayoutModule {
}

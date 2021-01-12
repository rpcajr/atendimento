import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AppLayoutComponent} from './app.layout.component';
import {PerfilComponent} from './perfil/perfil.component';
import {BackupComponent} from './backup/backup.component';

const routes: Routes = [
  {
    path: '',
    component: AppLayoutComponent,
    children: [
      {path: 'sistema', loadChildren: 'src/app/sistema/sistema.module#SistemaModule'},
      {path: 'perfil', component: PerfilComponent, data: {animation: ''}},
      {path: 'backup', component: BackupComponent, data: {animation: 'bac'}}
    ]
  }

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class LayoutRoutingModule {
}

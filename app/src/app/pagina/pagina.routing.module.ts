import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {EmptyDemoComponent} from './emptydemo.component';

const routes: Routes = [
  {path: 'selecionados', component: EmptyDemoComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PaginaRoutingModule {
}

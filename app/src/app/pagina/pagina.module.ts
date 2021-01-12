import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {PaginaRoutingModule} from './pagina.routing.module';
import {EmptyDemoComponent} from './emptydemo.component';

@NgModule({
  imports: [
    CommonModule,
    PaginaRoutingModule
  ],
  declarations: [EmptyDemoComponent]
})
export class PaginaModule {
}

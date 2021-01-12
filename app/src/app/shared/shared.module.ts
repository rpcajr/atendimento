import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {ButtonModule} from 'primeng/button';
import {
  AutoCompleteModule,
  CalendarModule,
  ChartModule,
  CheckboxModule,
  ChipsModule, DialogModule,
  DropdownModule,
  EditorModule,
  FileUploadModule,
  InputTextModule,
  MessageModule,
  PanelModule,
  ProgressBarModule,
  ScrollPanelModule,
  SelectButtonModule,
  TabViewModule,
  ToolbarModule, TooltipModule,
  TreeModule
} from 'primeng/primeng';
import {JfValidaCampoComponent} from './jf-valida-campo/jf-valida-campo.component';
import {MsgService} from './msg.service';
import {JfBotoesComponent} from './jf-botoes/jf-botoes.component';
import {JfRodapeComponent} from './jf-rodape/jf-rodape.component';
import {UppercaseDirective} from './uppercase.directive';
import {DynamicDialogModule} from 'primeng/dynamicdialog';
import {TableModule} from 'primeng/table';
import {NgxPhoneMaskBrModule} from 'ngx-phone-mask-br';
import {JfPermissoesComponent} from './jf-permissoes/jf-permissoes.component';
import {SweetAlert2Module} from '@sweetalert2/ngx-sweetalert2';
import {AutofocusDirective} from './autofocus.directive';
import {VirtualScrollerModule} from 'primeng/virtualscroller';
import {DataViewModule} from 'primeng/dataview';

@NgModule({
  declarations: [
    JfValidaCampoComponent,
    JfBotoesComponent,
    JfRodapeComponent,
    UppercaseDirective,
    JfPermissoesComponent,
    AutofocusDirective],
  imports: [
    CommonModule,
    FormsModule,
    ButtonModule,
    InputTextModule,
    MessageModule,
    ProgressBarModule,
    FileUploadModule,
    ToolbarModule,
    PanelModule,
    DropdownModule,
    ChartModule,
    DynamicDialogModule,
    TableModule,
    NgxPhoneMaskBrModule,
    CheckboxModule,
    TreeModule,
    ScrollPanelModule,
    TabViewModule,
    ChipsModule,
    SweetAlert2Module.forRoot(),
    EditorModule,
    AutoCompleteModule,
    CalendarModule,
    SelectButtonModule,
    VirtualScrollerModule,
    DataViewModule,
    DialogModule,
    TooltipModule
  ], exports: [
    FormsModule,
    ButtonModule,
    InputTextModule,
    JfValidaCampoComponent,
    ProgressBarModule,
    FileUploadModule,
    PanelModule,
    ToolbarModule,
    DropdownModule,
    JfBotoesComponent,
    JfRodapeComponent,
    UppercaseDirective,
    DynamicDialogModule,
    TableModule,
    NgxPhoneMaskBrModule,
    CheckboxModule,
    JfPermissoesComponent,
    TabViewModule,
    ChipsModule,
    SweetAlert2Module,
    EditorModule,
    AutoCompleteModule,
    CalendarModule,
    SelectButtonModule,
    ChartModule,
    VirtualScrollerModule,
    DataViewModule,
    DialogModule,
    TooltipModule
  ], providers: [
    MsgService
  ]
})
export class SharedModule {
}

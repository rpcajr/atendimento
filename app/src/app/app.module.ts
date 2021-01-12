import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {AppRoutingModule} from './app.routing.module';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {AuthModule} from './auth/auth.module';
import {ToastModule} from 'primeng/toast';
import {DialogService, DynamicDialogConfig, DynamicDialogRef, MessageService} from 'primeng/api';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    AuthModule,
    BrowserAnimationsModule,
    ToastModule
  ],
  providers: [
    MessageService,
    DialogService,
    DynamicDialogRef,
    DynamicDialogConfig
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}

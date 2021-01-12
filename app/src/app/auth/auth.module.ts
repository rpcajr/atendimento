import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {RouterModule} from '@angular/router';

import {TokenInterceptor} from './token.interceptor';
import {AuthService} from './auth.service';
import {AuthGuard} from './auth.guard';
import {LoginComponent} from './login/login.component';
import {SharedModule} from '../shared/shared.module';
import {LoginService} from './login/login.service';


@NgModule({
  declarations: [LoginComponent],
  imports: [
    CommonModule,
    RouterModule,
    HttpClientModule,
    SharedModule
  ],
  providers: [
    AuthGuard,
    AuthService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true
    },
    LoginService
  ],
  exports: [
    LoginComponent
  ]
})
export class AuthModule {
}

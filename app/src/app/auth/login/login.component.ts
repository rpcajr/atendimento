import {Component, ElementRef, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {LoginService} from './login.service';
import {NgForm} from '@angular/forms';
import {JFUtil} from '../../_util/jf.util';
import {throwError} from 'rxjs';
import {HttpErrorResponse} from '@angular/common/http';
import {MsgService} from '../../shared/msg.service';
import {catchError, finalize} from 'rxjs/operators';
import {Tokens} from '../tokens';
import {AuthService} from '../auth.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit, OnDestroy {

  @ViewChild('username') username: ElementRef;
  @ViewChild('password') password: ElementRef;
  usuario = '';
  sen = '';
  conectando = false;

  constructor(private loginService: LoginService,
              private auth: AuthService,
              private router: Router,
              private msg: MsgService) {
  }

  public ngOnInit() {
    JFUtil.focus(this.username);
    if (this.auth.isLoggedIn()) {
      this.conectando = true;
      this.router.navigate(['/principal/sistema/geral']);
    }
  }

  login(form: NgForm) {
    if (this.conectando) {
      return;
    }
    this.conectando = true;
    if (form.invalid) {
      JFUtil.verificaValidacoesForm(form.form);
      if (this.username.nativeElement.value === '') {
        JFUtil.focus(this.username);
        this.conectando = false;
        return;
      }
      if (this.password.nativeElement.value === '') {
        JFUtil.focus(this.password);
        this.conectando = false;
        return;
      }
    }

    this.loginService.entrar(this.usuario, this.sen)
      .pipe(
        finalize(() => this.conectando = false),
        catchError((res: HttpErrorResponse) => {
          this.msg.handle(res);
          return throwError(res);
        })
      ).subscribe(token => {
      const t = new Tokens();
      t.jwt = token.access_token;
      t.refreshToken = token.refresh_token;
      this.auth.storeTokens(t);
      this.router.navigate(['/principal/sistema/geral']);
    });

  }

  ngOnDestroy(): void {
  }

}

import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {NgForm} from '@angular/forms';
import {Versao} from '../../_domain/versao';
import {VersaoService} from './versao.service';
import {JFUtil} from '../../_util/jf.util';
import {catchError} from 'rxjs/operators';
import {HttpErrorResponse} from '@angular/common/http';
import {throwError} from 'rxjs';
import {MsgService} from '../../shared/msg.service';
import {JfBotoesComponent} from '../../shared/jf-botoes/jf-botoes.component';
import {DynamicDialogRef} from 'primeng/api';

@Component({
  selector: 'app-versao',
  templateUrl: './versao.component.html',
  styleUrls: ['./versao.component.css']
})
export class VersaoComponent implements OnInit {
  versao: Versao;
  edicao = false;
  @ViewChild('bts') bts: JfBotoesComponent;
  @ViewChild('nom') nome: ElementRef;
  total = 0;
  versoes: Versao[];

  constructor(private msg: MsgService, public ref: DynamicDialogRef, private service: VersaoService) {
  }

  ngOnInit() {
    this.edicao = false;
    this.versao = new Versao();
    this.service.getTotal().subscribe(t => this.total = t);
    this.service.getList().subscribe(l => this.versoes = l);
  }

  salvar(form: NgForm) {
    if (form.invalid) {
      JFUtil.verificaValidacoesForm(form.form);
      return;
    }
    this.service.set(this.versao)
      .pipe(
        catchError((res: HttpErrorResponse) => {
          this.msg.handle(res);
          return throwError(res);
        })
      )
      .subscribe(s => {
        this.msg.sucesso(`Versão ${s.versao} salvo com Sucesso`);
        this.limpar(form);
        this.ref.close(s);
      });
  }

  pesquisar() {

  }

  editar() {
    this.edicao = true;
    JFUtil.focus(this.nome);
  }

  limpar(form: NgForm) {
    form.resetForm();
    this.ngOnInit();

  }
}

import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {Ocorrencia} from '../../_domain/ocorrencia';
import {Setor} from '../../_domain/setor';
import {SetorService} from '../setor/setor.service';
import {OcorrenciaService} from './ocorrencia.service';
import {JFUtil} from '../../_util/jf.util';
import {catchError} from 'rxjs/operators';
import {HttpErrorResponse} from '@angular/common/http';
import {throwError} from 'rxjs';
import {NgForm} from '@angular/forms';
import {MsgService} from '../../shared/msg.service';
import {JfBotoesComponent} from '../../shared/jf-botoes/jf-botoes.component';
import {DialogService} from 'primeng/api';
import {OcorrenciaPesquisaComponent} from './ocorrencia-pesquisa/ocorrencia-pesquisa.component';

@Component({
  selector: 'app-ocorrencia',
  templateUrl: './ocorrencia.component.html',
  styleUrls: ['./ocorrencia.component.css'],
  providers: [DialogService]
})
export class OcorrenciaComponent implements OnInit {
  @ViewChild('nom') nome: ElementRef;
  @ViewChild('bts') bts: JfBotoesComponent;
  edicao = false;
  ocorrencia = new Ocorrencia();
  setorPai: Setor;
  setores: Setor[];
  total = 0;

  constructor(private setorService: SetorService,
              private msg: MsgService,
              private ocoService: OcorrenciaService,
              private dialogService: DialogService) {
  }

  ngOnInit() {
    this.setores = [];
    this.load();
  }

  load() {
    this.edicao = false;
    this.bts.acao_cancelar();
    this.ocorrencia = new Ocorrencia();
    this.setorService.getList().subscribe(l => {
      this.setores = l;
    });

    this.ocoService.getTotal().subscribe(t => {
      this.total = t;
    });
  }

  escolherSetor(event) {
    if (event !== null) {
      this.ocorrencia.setorID = event.id;
    }
  }

  editar() {
    this.edicao = true;
    JFUtil.focus(this.nome);
  }

  salvar(form: NgForm) {
    if (form.invalid) {
      JFUtil.verificaValidacoesForm(form.form);
      return;
    }
    this.ocoService.set(this.ocorrencia)
      .pipe(
        catchError((res: HttpErrorResponse) => {
          this.msg.handle(res);
          return throwError(res);
        })
      )
      .subscribe(s => {
        this.msg.sucesso(`Ocorrencia ${s.nome} salvo com Sucesso`);
        form.resetForm();
        this.load();
      });
  }

  pesquisar() {
    const ref = this.dialogService.open(OcorrenciaPesquisaComponent, {
      header: 'Pesquisa de Ocorrências',
      width: JFUtil.getWidthDialog(),
      closable: true
    });

    ref.onClose.subscribe((oco: Ocorrencia) => {
      if (oco) {
        this.loadOcorrencia(oco);
      }
    });
  }

  limpar(form: NgForm) {
    form.resetForm();
    this.edicao = false;

  }

  loadOcorrencia(oco: Ocorrencia) {
    this.ocorrencia = oco;
    this.bts.habilitar_edicao();
    for (const pai of this.setores) {
      if (pai.id === this.ocorrencia.setorID) {
        this.setorPai = pai;
      }
    }
  }
}

import {Component, ElementRef, OnInit, ViewChild} from '@angular/core' ;
import {Setor} from '../../_domain/setor';
import {SetorService} from './setor.service';
import {MsgService} from '../../shared/msg.service';
import {catchError} from 'rxjs/operators';
import {HttpErrorResponse} from '@angular/common/http';
import {throwError} from 'rxjs';
import {NgForm} from '@angular/forms';
import {JfBotoesComponent} from '../../shared/jf-botoes/jf-botoes.component';
import {JFUtil} from '../../_util/jf.util';
import {DialogService} from 'primeng/api';
import {SetorPesquisaComponent} from './setor-pesquisa/setor-pesquisa.component';

@Component({
  selector: 'app-setor',
  templateUrl: './setor.component.html',
  styleUrls: ['./setor.component.css'],
  providers: [DialogService]
})
export class SetorComponent implements OnInit {
  setor: Setor;
  setorPai: Setor;
  setores: Setor[];
  @ViewChild('form') form: NgForm;
  @ViewChild('bts') bts: JfBotoesComponent;
  @ViewChild('nom') nome: ElementRef;
  edicao = false;

  constructor(private setorService: SetorService,
              private msg: MsgService,
              private dialogService: DialogService) {
  }

  ngOnInit() {
    this.setores = [];
    this.bts.acao_cancelar();
    this.limpar();
    this.loadSetores();
  }

  pesquisar() {
    const ref = this.dialogService.open(SetorPesquisaComponent, {
      header: 'Pesquisa de Setores',
      width: JFUtil.getWidthDialog(),
      height: '320px',
      closable: true
    });

    ref.onClose.subscribe((setor: Setor) => {
      if (setor) {
        this.loadSetor(setor);
      }
    });

  }

  limpar() {
    this.setor = new Setor();
    this.edicao = false;
    this.form.resetForm();
  }

  escolherPai(event) {
    if (event !== null) {
      this.setor.paiID = event.id;
    }
  }

  salvar() {
    if (this.form.invalid) {
      JFUtil.verificaValidacoesForm(this.form.form);
      return;
    }
    this.setorService.set(this.setor)
      .pipe(
        catchError((res: HttpErrorResponse) => {
          this.msg.handle(res);
          return throwError(res);
        })
      )
      .subscribe(s => {
        this.msg.sucesso(`Setor ${s.nome} salvo com Sucesso`);
        this.limpar();
        this.bts.acao_cancelar();
        this.edicao = false;
        this.loadSetores();
      });
  }

  loadSetores() {
    this.setorService.getList().subscribe(l => {
      this.setores = l;
    });
  }

  loadSetor(setor: Setor) {
    this.setor = setor;
    this.bts.habilitar_edicao();
    for (const pai of this.setores) {
      if (pai.id === this.setor.paiID) {
        this.setorPai = pai;
      }
    }
  }

  editar() {
    this.edicao = true;
    JFUtil.focus(this.nome);
  }
}

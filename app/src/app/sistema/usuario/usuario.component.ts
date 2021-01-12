import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {User} from '../../_domain/user';
import {Setor} from '../../_domain/setor';
import {SetorService} from '../setor/setor.service';
import {JFUtil} from '../../_util/jf.util';
import {NgForm} from '@angular/forms';
import {DialogService, TreeNode} from 'primeng/api';
import {JfPermissoesComponent} from '../../shared/jf-permissoes/jf-permissoes.component';
import {UsuarioService} from './usuario.service';
import {catchError} from 'rxjs/operators';
import {HttpErrorResponse} from '@angular/common/http';
import {throwError} from 'rxjs';
import {MsgService} from '../../shared/msg.service';
import {JfBotoesComponent} from '../../shared/jf-botoes/jf-botoes.component';
import {UsuarioPesquisaComponent} from './usuario-pesquisa/usuario-pesquisa.component';

@Component({
  selector: 'app-usuario',
  templateUrl: './usuario.component.html',
  styleUrls: ['./usuario.component.css'],
  providers: [DialogService]
})
export class UsuarioComponent implements OnInit {
  edicao = false;
  novo = false;
  user: User;
  setores: Setor[];
  setor: Setor;
  total: number;
  @ViewChild('nome') nome: ElementRef;
  @ViewChild('per') per: JfPermissoesComponent;
  @ViewChild('bts') bts: JfBotoesComponent;

  constructor(private setorService: SetorService,
              private msg: MsgService,
              private usuarioService: UsuarioService,
              private dialogService: DialogService) {
  }

  inserir() {
    this.novo = true;
    this.edicao = true;
    JFUtil.focus(this.nome);
    this.per.edicao = true;
    this.per.iniciarArvoreSistema();
  }

  salvar(form: NgForm) {
    if (form.invalid) {
      JFUtil.verificaValidacoesForm(form.form);
      return;
    }

    this.usuarioService.set(this.user)
      .pipe(
        catchError((res: HttpErrorResponse) => {
          this.msg.handle(res);
          return throwError(res);
        })
      )
      .subscribe(u => {
        this.msg.sucesso(`Usuario ${u.nome} salvo com Sucesso`);
        this.bts.acao_cancelar();
        this.totalUsuario();
      });
  }

  ngOnInit() {
    this.user = new User();
    this.loadSetores();
    this.totalUsuario();
  }

  cancelar(form: NgForm) {
    form.resetForm();
    this.edicao = false;
    this.novo = false;
    this.user = new User();
    this.per.limpar();
  }

  loadSetores() {
    this.setorService.getList().subscribe(l => {
      this.setores = l;
    });
  }

  escolherSetor(event) {
    if (event !== null) {
      this.user.setorID = event.id;
    }
  }

  totalUsuario() {
    this.usuarioService.getTotal().subscribe(t => this.total = t);
  }

  pesquisar() {
    const ref = this.dialogService.open(UsuarioPesquisaComponent, {
      header: 'Pesquisa de Usuários',
      width: JFUtil.getWidthDialog(),
      closable: true
    });
    ref.onClose.subscribe((usuario: User) => {
      if (usuario) {
        this.loadUsuario(usuario);
      }
    });
  }

  onNotify(per: string) {
    this.user.permissoes = per;
  }

  private loadUsuario(usuario: User) {
    this.user = usuario;
    this.per.permissoes = usuario.permissoes;
    this.per.iniciarArvoreSistema();
    this.bts.habilitar_edicao();
    for (const pai of this.setores) {
      if (pai.id === this.user.setorID) {
        this.setor = pai;
        break;
      }
    }
  }

  editar() {
    this.novo = false;
    this.edicao = true;
    JFUtil.focus(this.nome);
    this.per.edicao = true;
    this.per.iniciarArvoreSistema();
  }
}

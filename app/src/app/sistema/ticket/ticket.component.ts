import {AfterViewInit, Component, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {Chamado} from '../../_domain/chamado';
import {OcorrenciaService} from '../ocorrencia/ocorrencia.service';
import {UsuarioService} from '../usuario/usuario.service';
import {ClienteService} from '../cliente/cliente.service';
import {Cliente} from '../../_domain/cliente';
import {Contato} from '../../_domain/contato';
import {JFUtil} from '../../_util/jf.util';
import {DialogService, DynamicDialogConfig, DynamicDialogRef, SelectItem} from 'primeng/api';
import {AutoComplete, Dropdown, TabView} from 'primeng/primeng';
import {ContatoComponent} from '../cliente/contato/contato.component';
import {NgForm} from '@angular/forms';
import {Versao} from '../../_domain/versao';
import {VersaoService} from '../versao/versao.service';
import {TicketService} from './ticket.service';
import {MsgService} from '../../shared/msg.service';
import {catchError} from 'rxjs/operators';
import {HttpErrorResponse} from '@angular/common/http';
import {throwError} from 'rxjs';
import {ClienteComponent} from '../cliente/cliente.component';
import {VersaoComponent} from '../versao/versao.component';
import {Comentario} from '../../_domain/comentario';

@Component({
  selector: 'app-ticket',
  templateUrl: './ticket.component.html',
  styleUrls: ['./ticket.component.css'],
  providers: [DialogService]
})
export class TicketComponent implements OnInit, AfterViewInit {
  ocorrencias: SelectItem[];
  contatos: Contato[];
  usuarios: SelectItem[];
  clientes: Cliente[];
  cliente: Cliente;
  @Input() chamado: Chamado;
  @Output() save = new EventEmitter();
  @Output() update = new EventEmitter();
  prioridades: SelectItem[];
  situacoes: SelectItem[];
  atendimento: SelectItem[];
  sistemas: SelectItem[];
  sistema: any;
  contato: Contato;
  versoes: SelectItem[];
  dialogo = false;
  podeExcluir = false;
  comentario = '';
  pt: any;
  salvando = false;
  @ViewChild('auto') auto: AutoComplete;
  index = 0;
  @ViewChild('tabView') tabView: TabView;


  constructor(private ocorrenciasService: OcorrenciaService,
              private clienteService: ClienteService,
              private userService: UsuarioService,
              private versaoService: VersaoService,
              private ticketService: TicketService,
              private msg: MsgService,
              private  config: DynamicDialogConfig,
              public ref: DynamicDialogRef,
              private dialogService: DialogService) {
  }

  ngOnInit() {
    this.carregarChamado();
    this.ocorrenciasService.getListOcorrenciaDoSetor().subscribe(list => {
      this.ocorrencias = [];
      for (const u of list) {
        this.ocorrencias.push({label: u.nome, value: u.id});
      }
    });
    this.userService.getListPerfil().subscribe(list => {
      this.usuarios = [{label: '<< eu >>', value: JFUtil.getUser().id}];
      for (const u of list) {
        this.usuarios.push({label: u.nome, value: u.id});
      }
    });
    this.versaoService.getListString().subscribe(list => {
      this.versoes = [{label: '', value: null}];
      for (const v of list) {
        this.versoes.push({label: v, value: v});
      }
    });
    this.prioridades = JFUtil.getPrioridades();
    this.situacoes = JFUtil.getSituacoes();
    this.sistemas = JFUtil.getSistemas();
    this.atendimento = JFUtil.getAtendimentos();
    this.pt = JFUtil.getFormatacaoData();
  }

  private carregarChamado() {
    if (this.config && this.config.data) {
      this.chamado = new Chamado();
      this.dialogo = true;
      this.ticketService.get(this.config.data.chamadoID).subscribe(chamado => {
        this.chamado = chamado;
        this.chamado.datAbertura = new Date(chamado.datAbertura);
        this.podeExcluir = this.chamado.usuarioID === JFUtil.getUser().id || this.chamado.atribuicaoID === JFUtil.getUser().id;
        if (chamado.clienteID != null) {
          this.clienteService.get(chamado.clienteID).subscribe(cli => {
            this.cliente = cli;
            this.contatos = cli.contatos;

            if (chamado.contatoID != null) {
              const index = this.contatos.findIndex(i => i.id === chamado.contatoID);
              this.contato = this.contatos[index];
            }

            this.carregarSistemaCliente();
          });
        }
      });
      return;
    }

    if (this.chamado == null) {
      this.chamado = new Chamado();
    }
    this.chamado.datAbertura = new Date();
    this.chamado.usuarioID = JFUtil.getUser().id;
    this.chamado.ocorrenciaID = JFUtil.getOcorrencia();
  }

  pesquisarCliente(event: any) {
    this.clienteService.getClientePesquisa(event.query).then(
      res => this.clientes = res
    );
  }

  escolherCliente() {
    if (!this.cliente) {
      this.chamado.clienteID = null;
      this.chamado.versao = null;
      this.chamado.cliente = '';
      this.contatos = [];
      return;
    }


    this.carregarSistemaCliente();

    this.clienteService.getContatos(this.cliente.id).then(res => {
      this.contatos = [null];
      for (const c of res) {
        this.contatos.push(c);
      }
    });
    this.chamado.clienteID = this.cliente.id;
    this.chamado.versao = this.cliente.versao;
    this.chamado.cliente = this.cliente.fantasia;
    this.update.emit();
  }

  carregarSistemaCliente() {
    this.sistemas = [];
    this.sistemas.push({label: this.chamado.sistemas, value: this.chamado.sistemas});
    this.clienteService.getSistemasDTO(this.cliente.codCliente).subscribe(l => {
        for (const c of l) {
          if (c === this.chamado.sistemas) {
            continue;
          }
          this.sistemas.push({label: c, value: c});
        }
      }
    );

  }

  cancelar() {
    this.save.emit();
    if (this.dialogo) {
      this.ref.close();
    }
  }

  adicionarContato() {
    const ref = this.dialogService.open(ContatoComponent, {
      header: 'Adicionar Contato',
      width: JFUtil.getWidthDialog(),
      height: '320px',
      data: {
        clienteID: this.chamado.clienteID,
        inserir: true,
        contato: this.contato
      },
      closable: true
    });

    ref.onClose.subscribe((contato: Contato) => {
      if (contato) {
        const temp = this.contatos;
        this.contatos = [contato];
        for (const c of temp) {
          this.contatos.push(c);
        }
        this.contato = contato;
        this.chamado.contatoID = contato.id;
        this.chamado.contatoNome = contato.nome;
        this.update.emit();
      }
    });
  }

  incluirComentario() {
    if (!this.comentario || this.comentario === '') {
      this.msg.erro('Informe o Comentário');
      return;
    }
    const comentario = new Comentario();
    comentario.comentario = this.comentario;
    comentario.datComentario = new Date();
    comentario.usuario = JFUtil.getUser();
    if (!this.chamado.comentarios) {
      this.chamado.comentarios = [];
    }
    this.chamado.comentarios.push(comentario);
    this.comentario = '';
    this.msg.sucesso('comentario incluido');
  }

  salvar(form: NgForm) {
    this.tabView.activeIndexChange.emit(0);
    this.tabView.activeIndex = 0;

    if (form.invalid) {
      JFUtil.verificaValidacoesForm(form.form);
      return;
    }

    if (this.contato) {
      this.chamado.contatoID = this.contato.id;
    }
    this.salvando = true;
    this.ticketService.set(this.chamado)
      .pipe(
        catchError((res: HttpErrorResponse) => {
          this.msg.handle(res);
          this.salvando = false;
          return throwError(res);
        })
      ).subscribe(c => {
      this.msg.sucesso(`salvo com Sucesso nº ${c.id}`);
      form.resetForm();
      this.save.emit();
      if (this.dialogo) {
        this.ref.close(this.chamado);
      }
    });
  }

  excluir() {
    this.ticketService.delete(this.chamado.id).then(() => {
      this.msg.sucesso('Excluido com Sucesso!!');
      this.ref.close(this.chamado);
    }).catch(erro => this.msg.handle(erro));
  }

  ngAfterViewInit(): void {
    setTimeout(() => this.auto.focusInput(), 400);
  }

  escolherOcorrencia() {
    JFUtil.setOcorrencia(this.chamado.ocorrenciaID);
  }

  escolherContato(event: any) {
    if (event.value) {
      this.chamado.contatoNome = event.value.nome;
      this.update.emit();
    }
  }

  acessarCliente() {
    const ref = this.dialogService.open(ClienteComponent, {
      header: 'Cliente',
      width: '80%',
      data: {
        dialogo: true,
        clienteID: this.chamado.clienteID
      },
      closable: true
    });

    ref.onClose.subscribe((cliente: Cliente) => {
      if (cliente) {
        this.cliente = cliente;
        this.escolherCliente();
      }
    });
  }

  adicionarVersao() {
    const ref = this.dialogService.open(VersaoComponent, {
      header: 'Adicionar Versao',
      width: JFUtil.getWidthDialog(),
      closable: true
    });

    ref.onClose.subscribe((versao: Versao) => {
      if (versao) {
        this.versoes.push({label: versao.versao, value: versao.versao});
        this.chamado.versao = versao.versao;
      }
    });
  }

}

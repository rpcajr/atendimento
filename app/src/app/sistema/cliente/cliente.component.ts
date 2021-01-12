import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {Cliente} from '../../_domain/cliente';
import {DialogService, DynamicDialogConfig, DynamicDialogRef, SelectItem} from 'primeng/api';
import {NgForm} from '@angular/forms';
import {JFUtil} from '../../_util/jf.util';
import {ClienteService} from './cliente.service';
import {MsgService} from '../../shared/msg.service';
import {catchError} from 'rxjs/operators';
import {HttpErrorResponse} from '@angular/common/http';
import {throwError} from 'rxjs';
import {JfBotoesComponent} from '../../shared/jf-botoes/jf-botoes.component';
import {Contato} from '../../_domain/contato';
import {ContatoComponent} from './contato/contato.component';
import {ClientePesquisaComponent} from './cliente-pesquisa/cliente-pesquisa.component';
import {VersaoService} from '../versao/versao.service';
import {ActivatedRoute} from '@angular/router';
import {ChamadosComponent} from '../pesquisas/chamados/chamados.component';
import {JFFilter} from '../../_domain/jffilter';

@Component({
  selector: 'app-cliente',
  templateUrl: './cliente.component.html',
  styleUrls: ['./cliente.component.css'],
  providers: [DialogService]
})
export class ClienteComponent implements OnInit {
  cliente: Cliente;
  edicao = false;
  @ViewChild('reg') registro: ElementRef;
  @ViewChild('fone1') fone: ElementRef;
  @ViewChild('fone2') fone2: ElementRef;
  @ViewChild('bts') bts: JfBotoesComponent;
  @ViewChild('chamado') chamados: ChamadosComponent;
  filter: JFFilter = new JFFilter();
  sistemas: [];
  ufs: SelectItem[];
  total = 0;
  index = 0;
  versoes: SelectItem[];

  constructor(private msg: MsgService, private service: ClienteService,
              public ref: DynamicDialogRef, public config: DynamicDialogConfig,
              private dialogService: DialogService, private route: ActivatedRoute, private versaoService: VersaoService) {
  }

  ngOnInit() {
    this.cliente = new Cliente();
    this.ufs = JFUtil.getUF();
    this.totalClientes();
    this.versaoService.getListString().subscribe(list => {
      this.versoes = [{label: '', value: null}];
      for (const s of list) {
        this.versoes.push({label: s, value: s});
      }
    });

    if (this.config.data && this.config.data.clienteID) {
      this.service.get(this.config.data.clienteID)
        .subscribe(c => this.carregarCliente(c));
    }

    if (this.route.snapshot.paramMap.get('codCliente')) {
      this.cliente.codCliente = this.route.snapshot.paramMap.get('codCliente');
      this.service.getPorCodCliente(this.cliente.codCliente)
        .subscribe(c => this.carregarCliente(c));
    }

  }

  totalClientes() {
    this.service.getTotal().subscribe(t => this.total = t);
  }

  editar() {
    this.edicao = true;
    JFUtil.focus(this.registro);
  }

  salvar(form: NgForm) {
    if (form.invalid) {
      JFUtil.verificaValidacoesForm(form.form);
      return;
    }

    this.service.set(this.cliente)
      .pipe(
        catchError((res: HttpErrorResponse) => {
          this.msg.handle(res);
          return throwError(res);
        })
      )
      .subscribe(s => {
        this.msg.sucesso(`Cliente ${s.nome} salvo com Sucesso`);
        this.bts.acao_cancelar();
        this.ref.close(s);
      });
  }

  pesquisar() {
    const ref = this.dialogService.open(ClientePesquisaComponent, {
      header: 'Pesquisa de Clientes',
      width: '80%',
      closable: true
    });

    ref.onClose.subscribe((cliente: Cliente) => {
      if (cliente) {
        this.service.getPorCodCliente(cliente.codCliente)
          .subscribe(c => this.carregarCliente(c));
      }
    });

  }

  limpar(form: NgForm) {
    form.resetForm();
    this.edicao = false;
    this.cliente = new Cliente();
    this.sistemas = [];
    this.fone.nativeElement.value = '';
    this.fone2.nativeElement.value = '';
    JFUtil.focus(this.registro);
    this.index = 0;
  }

  pesquisarCliente(form: NgForm) {
    if (this.edicao) {
      return;
    }

    this.service.getPorCodCliente(this.cliente.codCliente)
      .pipe(
        catchError((res: HttpErrorResponse) => {
          this.msg.handle(res);
          this.limpar(form);
          return throwError(res);
        })
      )
      .subscribe(c => this.carregarCliente(c));

  }

  carregarCliente(cliente: Cliente) {
    this.cliente = cliente;
    this.sistemas = [];
    this.bts.habilitar_edicao();
    this.filter.cliente = cliente;
    this.filter.situacao = null;
    this.chamados.ignoreInit = false;
    this.chamados.loadChamado();
    this.service.getSistemas(this.cliente.codCliente).subscribe(list => this.sistemas = list);
  }

  editarContato(rowData: Contato) {
    const ref = this.dialogService.open(ContatoComponent, {
      header: 'Contato',
      data: {
        contato: rowData
      },
      width: JFUtil.getWidthDialog(),
      closable: true
    });

    ref.onClose.subscribe((contato: Contato) => {
      rowData.nome = contato.nome;
      rowData.fone = contato.fone;
      rowData.fone2 = contato.fone2;
      rowData.email = contato.email;
      rowData.cargo = contato.cargo;
    });
  }

  adicionarContato() {
    const ref = this.dialogService.open(ContatoComponent, {
      header: 'Adicionar Contato',
      width: JFUtil.getWidthDialog(),
      closable: true
    });

    ref.onClose.subscribe((contato: Contato) => {
      if (contato) {
        this.cliente.contatos.push(contato);
      }
    });
  }
}

import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {JFFilter} from '../../../_domain/jffilter';
import {Cliente} from '../../../_domain/cliente';
import {ClienteService} from '../../cliente/cliente.service';
import {DynamicDialogConfig, DynamicDialogRef, SelectItem} from 'primeng/api';
import {JFUtil} from '../../../_util/jf.util';
import {AutoComplete} from 'primeng/primeng';
import {OcorrenciaService} from '../../ocorrencia/ocorrencia.service';
import {UsuarioService} from '../../usuario/usuario.service';
import {Ocorrencia} from '../../../_domain/ocorrencia';
import {UserPerfil} from '../../../_domain/user-perfil';
import {VersaoService} from '../../versao/versao.service';

@Component({
  selector: 'app-pesquisa-filter',
  templateUrl: './pesquisa-filter.component.html',
  styleUrls: ['./pesquisa-filter.component.css']
})
export class PesquisaFilterComponent implements OnInit, AfterViewInit {
  filter: JFFilter;
  ocorrencias: Ocorrencia[];
  usuarios: UserPerfil[];
  clientes: Cliente[];
  situacoes: SelectItem[];
  sistemas: SelectItem[];
  versoes: SelectItem[];
  @ViewChild('auto') auto: AutoComplete;
  pt: any;

  constructor(private ocorrenciasService: OcorrenciaService,
              private clienteService: ClienteService,
              private userService: UsuarioService,
              private versaoService: VersaoService,
              public ref: DynamicDialogRef, public config: DynamicDialogConfig) {
    this.filter = new JFFilter();
  }

  ngOnInit() {
    if (this.config.data) {
      this.filter = {...this.config.data.filter};
    }
    this.situacoes = JFUtil.getSituacoes();
    this.pt = JFUtil.getFormatacaoData();
    this.sistemas = JFUtil.getSistemas();
    this.ocorrenciasService.getListOcorrenciaDoSetor().subscribe(list => {
      this.ocorrencias = [null, ...list];
    });
    this.userService.getListPerfil().subscribe(list => {
      this.usuarios = [null, JFUtil.getUser(), ...list];
      this.usuarios[1].nome = '<< eu >>';
    });

    this.versaoService.getListString().subscribe(list => {
      this.versoes = [{label: '', value: null}];
      for (const v of list) {
        this.versoes.push({label: v, value: v});
      }
    });

  }

  salvar() {
    this.filter.page = 0;
    JFUtil.setFilter(this.filter);
    this.ref.close(this.filter);
  }

  cancelar() {
    this.ref.close();
  }

  pesquisarCliente(event: any) {
    this.clienteService.getClientePesquisa(event.query).then(
      res => this.clientes = res
    );
  }

  ngAfterViewInit(): void {
    setTimeout(() => this.auto.focusInput(), 200);
  }

  limpar() {
    this.filter = new JFFilter();
  }
}

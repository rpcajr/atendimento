import {Component, OnInit, ViewChild} from '@angular/core';
import {DynamicDialogConfig, DynamicDialogRef} from 'primeng/api';
import {Contato} from '../../../_domain/contato';
import {JfBotoesComponent} from '../../../shared/jf-botoes/jf-botoes.component';
import {JFUtil} from '../../../_util/jf.util';
import {ClienteService} from '../cliente.service';

@Component({
  selector: 'app-contato',
  templateUrl: './contato.component.html',
  styleUrls: ['./contato.component.css']
})
export class ContatoComponent implements OnInit {
  contato: Contato;
  contatoOriginal: any;
  clienteID: number;
  inserir: false;
  @ViewChild('nom') nome: JfBotoesComponent;

  constructor(public ref: DynamicDialogRef, public config: DynamicDialogConfig, private clienteService: ClienteService) {
  }

  ngOnInit() {
    if (this.config.data) {
      this.contato = this.config.data.contato;
      this.clienteID = this.config.data.clienteID;
      this.inserir = this.config.data.inserir;
    }

    if (!this.contato) {
      this.contato = new Contato();
    } else {
      this.contatoOriginal = {...this.contato};
    }
    JFUtil.focus(this.nome);
  }

  salvar() {
    if (!this.inserir) {
      this.ref.close(this.contato);
      return;
    }
    this.clienteService.setContato(this.clienteID, this.contato).then(c => this.ref.close(c));
  }

  cancelar() {
    this.ref.close(this.contatoOriginal);
  }

}

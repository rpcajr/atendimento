import {Component, EventEmitter, Input, Output} from '@angular/core';

@Component({
  selector: 'app-jf-botoes',
  templateUrl: './jf-botoes.component.html',
  styleUrls: ['./jf-botoes.component.css']
})
export class JfBotoesComponent {
  @Output() novo = new EventEmitter();
  @Output() editar = new EventEmitter();
  @Output() salvar = new EventEmitter();
  @Output() cancelar = new EventEmitter();
  @Output() pesquisar = new EventEmitter();
  @Output() sair = new EventEmitter();
  @Input() ocultarPesquisar = false;
  btInserir = false;
  btEditar = true;
  btSalvar = true;
  btCancelar = true;
  btPesquisar = false;
  btSair = false;

  acao_novo() {
    this.habilitar_salvar(true);
    this.novo.emit();
  }

  acao_editar() {
    this.habilitar_salvar(true);
    this.editar.emit();
  }

  acao_salvar() {
    this.salvar.emit();
  }

  acao_sair() {
    this.sair.emit();
  }

  acao_cancelar() {
    this.habilitar_salvar(false);
    this.cancelar.emit();
  }

  habilitar_edicao() {
    this.btInserir = true;
    this.btPesquisar = true;
    this.btSair = true;
    this.btSalvar = true;
    this.btEditar = false;
    this.btCancelar = false;
  }

  habilitar_salvar(val: boolean) {
    this.btInserir = val;
    this.btPesquisar = val;
    this.btSair = val;
    this.btEditar = true;
    this.btCancelar = !val;
    this.btSalvar = !val;
  }

  acao_pesquisar() {
    this.pesquisar.emit();
  }
}

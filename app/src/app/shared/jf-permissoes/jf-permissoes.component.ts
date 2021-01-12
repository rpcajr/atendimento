import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {TreeNode} from 'primeng/api';

@Component({
  selector: 'app-jf-permissoes',
  templateUrl: './jf-permissoes.component.html',
  styleUrls: ['./jf-permissoes.component.css']
})
export class JfPermissoesComponent implements OnInit {
  @Input() edicao: boolean;
  @Input() permissoes: string;
  @Output() alteracao = new EventEmitter<string>();
  tree: TreeNode[];
  selecionados: TreeNode[];

  constructor() {
  }

  ngOnInit() {
    this.iniciarArvoreSistema();
  }

  iniciarArvoreSistema() {
    this.tree = [
      {
        label: 'Cadastro', expanded: true, selectable: this.edicao, key: '0',
        children: [
          {label: 'Setores', expanded: true, selectable: this.edicao, key: '1'},
          {label: 'Ocorrências', expanded: true, selectable: this.edicao, key: '2'},
          {label: 'Operadores', expanded: true, selectable: this.edicao, key: '3'}
        ]
      }
    ];
    this.lerPermissoes();
  }

  lerPermissoes() {
    if (this.permissoes == null) {
      return;
    }
    this.selecionados = [];
    const temp = this.permissoes.split(',');
    for (const p of temp) {
      this.selecionados.push({key: p});
    }
  }

  carregarPermissoes() {
    const temp = [];
    for (const p of this.selecionados) {
      temp.push(p.key);
    }
    this.permissoes = temp.join(',');
    this.alteracao.emit(this.permissoes);
  }

  nodeSelect(event) {
    this.carregarPermissoes();
  }

  limpar() {
    this.edicao = false;
    this.permissoes = null;
    this.selecionados = null;
    this.iniciarArvoreSistema();
  }

}

import {Component, OnInit} from '@angular/core';
import {SetorService} from '../setor.service';
import {Setor} from '../../../_domain/setor';
import {TreeNode} from 'primeng/api';

@Component({
  selector: 'app-organograma',
  templateUrl: './organograma.component.html',
  styleUrls: ['./organograma.component.css']
})
export class OrganogramaComponent implements OnInit {
  data: TreeNode[];

  constructor(private setorService: SetorService) {
  }

  ngOnInit() {
    this.data = [{label: 'Empresa ', expanded: true, children: [], data: 0}];
    this.setorService.getOrganograma().subscribe(l => this.carregarOrganograma(l));
  }

  carregarOrganograma(setores: Setor[]) {
    for (const s of setores) {
      this.setOrg(s, this.data);
    }
  }

  setOrg(setor: Setor, data: TreeNode[]) {
    for (const t of data) {
      if (t.data === setor.paiID) {
        t.children.push(this.getTree(setor));
      } else {
        this.setOrg(setor, t.children);
      }
    }
  }

  getTree(setor: Setor): TreeNode {
    return {label: setor.nome, expanded: true, data: setor.id, children: []};
  }

}

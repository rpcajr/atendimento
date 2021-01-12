import {Component, OnInit} from '@angular/core';
import {DynamicDialogRef} from 'primeng/api';
import {Setor} from '../../../_domain/setor';
import {SetorService} from '../setor.service';

@Component({
  selector: 'app-setor-pesquisa',
  templateUrl: './setor-pesquisa.component.html',
  styleUrls: ['./setor-pesquisa.component.css']
})
export class SetorPesquisaComponent implements OnInit {
  setores: Setor[];
  loading = true;

  constructor(public ref: DynamicDialogRef,
              private setorService: SetorService) {
  }

  ngOnInit() {
    this.setores = [];
    this.setorService.getList().subscribe(l => {
      this.setores = l;
      this.loading = false;
    });
  }

  selecionar(event) {
    this.ref.close(event.data);
  }
}

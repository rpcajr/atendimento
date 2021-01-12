import {Component, OnInit} from '@angular/core';
import {DynamicDialogRef} from 'primeng/api';
import {OcorrenciaService} from '../ocorrencia.service';
import {Ocorrencia} from '../../../_domain/ocorrencia';

@Component({
  selector: 'app-ocorrencia-pesquisa',
  templateUrl: './ocorrencia-pesquisa.component.html',
  styleUrls: ['./ocorrencia-pesquisa.component.css']
})
export class OcorrenciaPesquisaComponent implements OnInit {

  oco: Ocorrencia;
  ocorrencias: Ocorrencia[] = [];
  loading = true;

  constructor(public ref: DynamicDialogRef,
              private ocorrenciaService: OcorrenciaService) {
  }

  ngOnInit() {
    this.ocorrenciaService.getList().subscribe(list => {
      this.ocorrencias = list;
      this.loading = false;
    });
  }

  selecionar(event) {
    this.ref.close(event.data);
  }

}

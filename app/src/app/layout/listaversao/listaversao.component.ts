import {Component, OnInit} from '@angular/core';
import {Versao} from '../../_domain/versao';
import {PesquisaService} from '../../sistema/pesquisas/pesquisa.service';

@Component({
  selector: 'app-listaversao',
  templateUrl: './listaversao.component.html',
  styleUrls: ['./listaversao.component.css']
})
export class ListaversaoComponent implements OnInit {

  versoes: Versao[];

  constructor(private service: PesquisaService) {
  }

  ngOnInit() {
    this.service.getVeroes().subscribe(list => this.versoes = list);
  }

}

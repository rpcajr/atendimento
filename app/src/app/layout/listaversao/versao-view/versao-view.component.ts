import {Component, Input, OnInit} from '@angular/core';
import {Versao} from '../../../_domain/versao';
import {NavigationEnd, Router} from '@angular/router';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-versao-view',
  templateUrl: './versao-view.component.html',
  styleUrls: ['./versao-view.component.css']
})
export class VersaoViewComponent implements OnInit {

  @Input() versao: Versao;

  constructor(private router: Router) {

  }

  ngOnInit() {
  }

  getPercentual() {
    const width = Math.floor(this.versao.total * 100 / this.versao.totalGeral);
    return {width: `${width}%`, height: '8px'};
  }

  verClientes() {
    this.router.navigate(['/principal/sistema/clientes/versao', this.versao.versao]);
  }

}

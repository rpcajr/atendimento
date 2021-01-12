import {Component, OnInit} from '@angular/core';
import {Cliente} from '../../../_domain/cliente';
import {ClienteService} from '../cliente.service';
import {DynamicDialogRef} from 'primeng/api';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';
import {switchMap} from 'rxjs/operators';

@Component({
  selector: 'app-cliente-pesquisa',
  templateUrl: './cliente-pesquisa.component.html',
  styleUrls: ['./cliente-pesquisa.component.css']
})
export class ClientePesquisaComponent implements OnInit {

  clientes: Cliente[];

  versao = '';

  constructor(public ref: DynamicDialogRef,
              private service: ClienteService,
              private route: ActivatedRoute,
              private router: Router) {

  }

  ngOnInit() {
    this.clientes = [];
    this.route.params.subscribe(params => {
      this.versao = params.versao;
      this.loadClientes();
    });


  }

  loadClientes() {
    if (this.versao) {
      this.service.getClientePesquisaVersao(this.versao).then(list => this.clientes = list);
    } else {
      this.service.getList().subscribe(l => this.clientes = l);
    }

  }

  selecionar(event) {
    if (this.versao) {
      this.router.navigate(['/principal/sistema/cliente/', event.data.codCliente]);
    } else {
      this.ref.close(event.data);
    }
  }

}

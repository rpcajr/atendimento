import {Component, OnInit} from '@angular/core';
import {User} from '../../../_domain/user';
import {UsuarioService} from '../usuario.service';
import {DynamicDialogRef} from 'primeng/api';

@Component({
  selector: 'app-usuario-pesquisa',
  templateUrl: './usuario-pesquisa.component.html',
  styleUrls: ['./usuario-pesquisa.component.css']
})
export class UsuarioPesquisaComponent implements OnInit {
  usuarios: User[];

  constructor(private uService: UsuarioService,
              public ref: DynamicDialogRef) {
  }

  ngOnInit() {
    this.uService.getList().subscribe(list => this.usuarios = list);
  }

  selecionar(event: any) {
    this.ref.close(event.data);
  }
}

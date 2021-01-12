import {Component, OnInit} from '@angular/core';
import {TopbarService} from '../top/topbar.service';
import {UserPerfil} from '../../_domain/user-perfil';
import {JFUtil} from '../../_util/jf.util';

@Component({
  selector: 'app-perfil',
  templateUrl: './perfil.component.html',
  styleUrls: ['./perfil.component.css']
})
export class PerfilComponent implements OnInit {
  imagem: string;
  user: UserPerfil;

  constructor(private topService: TopbarService) {
  }

  ngOnInit() {
    this.imagem = '';
    this.user = new UserPerfil();
    this.topService.loadImage.subscribe(url => {
      this.imagem = url;
    });
    this.topService.getUsuarioPerfil().subscribe(
      u => {
        this.loadUser(u);
      }
    );
  }

  private loadUser(u) {
    this.user = u;
    this.topService.loadImagePerfil(u);
    JFUtil.setUser(u);
  }

  salvar() {
    this.topService.set(this.user).subscribe(u => this.loadUser(u));
  }

  cancelar() {
    this.topService.getUsuarioPerfil().subscribe(u => this.loadUser(u));
  }

  setDefaultPic() {
    this.imagem = 'assets/images/profile.jpg';
  }

  onFileChanged(event: any) {
    const formData = new FormData();
    const file = event.target.files[0];
    formData.append('file', file, file.name);
    this.topService.uploadImage(formData).subscribe(f => {
      this.imagem = f.url;
      this.user.url = f.url;
      this.user.foto = f.nome;
    });
  }
}

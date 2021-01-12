import {Component, OnInit} from '@angular/core';
import {AppLayoutComponent} from '../app.layout.component';
import {TopbarService} from './topbar.service';
import {UserPerfil} from '../../_domain/user-perfil';
import {AuthService} from '../../auth/auth.service';
import {JFUtil} from '../../_util/jf.util';

@Component({
  selector: 'app-layout-topbar',
  templateUrl: './app.layout.topbar.html'
})
export class AppLayoutTopbarComponent implements OnInit {
  user: UserPerfil;
  imagem: string;


  constructor(public app: AppLayoutComponent,
              private auth: AuthService,
              private topService: TopbarService) {
  }


  ngOnInit(): void {
    this.imagem = '';
    this.topService.getUsuarioPerfil().subscribe(
      u => {
        this.user = u;
        this.topService.loadImagePerfil(u);
        JFUtil.setUser(u);
        this.topService.emiterPerfil.emit(u);
      }
    );

    this.topService.loadImage.subscribe(url => {
      this.imagem = url;
    });
  }

  setDefaultPic() {
    this.imagem = 'assets/images/profile.jpg';
  }

  sair() {
    this.auth.logout();
  }
}

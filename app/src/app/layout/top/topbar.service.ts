import {EventEmitter, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../environments/environment';
import {UserPerfil} from '../../_domain/user-perfil';
import {Observable} from 'rxjs';
import {Tokens} from '../../auth/tokens';
import {User} from '../../_domain/user';

@Injectable({
  providedIn: 'root'
})
export class TopbarService {
  path: string;
  pathFile: string;
  loadImage = new EventEmitter<string>();
  emiterPerfil = new EventEmitter<UserPerfil>();

  constructor(private http: HttpClient) {
    this.path = `${environment.API_BASE_URL}api/usuarios`;
    this.pathFile = `${environment.API_BASE_URL}file`;
  }

  loadImagePerfil(user: UserPerfil) {
    this.loadImage.emit(user.url);
  }

  getUsuarioPerfil(): Observable<UserPerfil> {
    return this.http.get<UserPerfil>(`${this.path}/perfil`);
  }

  uploadImage(form: FormData): Observable<any> {
    return this.http.post<any>(`${this.pathFile}/upload`, form);
  }

  set(user: UserPerfil): Observable<UserPerfil> {
    return this.http.post<UserPerfil>(`${this.path}/perfil`, user);
  }


}

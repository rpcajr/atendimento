import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../environments/environment';
import {User} from '../../_domain/user';
import {CrudService} from '../../shared/crud-service';
import {Observable} from 'rxjs';
import {UserPerfil} from '../../_domain/user-perfil';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService extends CrudService<User> {

  constructor(protected http: HttpClient) {
    super(http, `${environment.API_BASE_URL}api/usuarios`);
  }

  getListPerfil(): Observable<UserPerfil[]> {
    return this.http.get<UserPerfil[]>(`${this.url}/perfil/list`);
  }

}

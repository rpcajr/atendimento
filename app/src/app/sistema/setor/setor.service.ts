import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../environments/environment';
import {Observable} from 'rxjs';
import {Setor} from '../../_domain/setor';
import {CrudService} from '../../shared/crud-service';

@Injectable({
  providedIn: 'root'
})
export class SetorService extends CrudService<Setor> {

  constructor(protected http: HttpClient) {
    super(http, `${environment.API_BASE_URL}api/setores`);
  }

  getOrganograma(): Observable<Setor[]> {
    return this.http.get<Setor[]>(`${this.url}/organograma`);
  }

  getSetoresDependentes(): Observable<Setor[]> {
    return this.http.get<Setor[]>(`${this.url}/dependentes`);
  }

}

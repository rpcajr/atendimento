import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../environments/environment';
import {Ocorrencia} from '../../_domain/ocorrencia';
import {CrudService} from '../../shared/crud-service';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class OcorrenciaService extends CrudService<Ocorrencia> {

  constructor(protected http: HttpClient) {
    super(http, `${environment.API_BASE_URL}api/ocorrencias`);
  }

  getListOcorrenciaDoSetor(): Observable<Ocorrencia[]> {
    return this.http.get<Ocorrencia[]>(`${this.url}/setor`);
  }

}

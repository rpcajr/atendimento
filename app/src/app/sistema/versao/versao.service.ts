import {Injectable} from '@angular/core';
import {CrudService} from '../../shared/crud-service';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../environments/environment';
import {Versao} from '../../_domain/versao';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class VersaoService extends CrudService<Versao> {

  constructor(protected http: HttpClient) {
    super(http, `${environment.API_BASE_URL}api/versoes`);
  }

  getListString(): Observable<string[]> {
    return this.http.get<string[]>(this.url);
  }
}

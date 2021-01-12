import {Injectable} from '@angular/core';
import {CrudService} from '../shared/crud-service';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {Sistema} from '../_domain/sistema';

@Injectable({
  providedIn: 'root'
})
export class SistemaService extends CrudService<Sistema> {

  constructor(protected http: HttpClient) {
    super(http, `${environment.API_BASE_URL}api/sistemas`);
  }
}

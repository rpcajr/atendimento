import {Injectable} from '@angular/core';
import {CrudService} from '../../shared/crud-service';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../environments/environment';
import {Chamado} from '../../_domain/chamado';
import {Observable} from 'rxjs';
import {JfFiles} from '../../_domain/jfFiles';

@Injectable({
  providedIn: 'root'
})
export class TicketService extends CrudService<Chamado> {
  pathFile: string;

  constructor(protected http: HttpClient) {
    super(http, `${environment.API_BASE_URL}api/chamados`);
    this.pathFile = `${environment.API_BASE_URL}file`;
  }

  uploadFiles(form: FormData): Observable<JfFiles[]> {
    return this.http.post<JfFiles[]>(`${this.pathFile}/uploadFiles`, form);
  }

}

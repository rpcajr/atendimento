import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../environments/environment';
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class BackupService {
  path: string;

  constructor(private http: HttpClient) {
    this.path = `${environment.API_BASE_URL}clientes`;
  }

  getList(): Observable<any> {
    return this.http.get(this.path);
  }

  getTotal(): Observable<any> {
    return this.http.get(this.path+"/total");
  }


}

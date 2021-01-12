import {Injectable} from '@angular/core';
import {environment} from '../../../environments/environment';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  path: string;

  constructor(private http: HttpClient) {
    this.path = `${environment.API_BASE_URL}oauth/token`;
  }

  entrar(login: string, senha: string): Observable<any> {
    const body = `username=${login}&password=${senha}&grant_type=password`;
    return this.http.post<any>(this.path, body, this.getHeader());
  }

  getHeader(): any {
    return {
      headers: new HttpHeaders({
        'Content-Type': 'application/x-www-form-urlencoded',
        Authorization: 'Basic YW5ndWxhcjpAbmd1bEByMA=='
      })
    };
  }

}

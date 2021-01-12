import {Injectable} from '@angular/core';
import {Tokens} from './tokens';
import {Router} from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly JWT_TOKEN = 'access_token';
  private readonly REFRESH_TOKEN = 'refresh_token';

  constructor(private router: Router) {
  }

  getJwtToken() {
    return localStorage.getItem(this.JWT_TOKEN);
  }

  storeTokens(tokens: Tokens) {
    localStorage.setItem(this.JWT_TOKEN, tokens.jwt);
    localStorage.setItem(this.REFRESH_TOKEN, tokens.refreshToken);
  }

  isLoggedIn() {
/*    const helper = new JwtHelperService();
    const decodedToken = helper.decodeToken(this.getJwtToken());
    const expirationDate = helper.getTokenExpirationDate(this.getJwtToken());
    const isExpired = helper.isTokenExpired(this.getJwtToken());
    console.log('decoded', decodedToken);
    console.log('expirationDate', expirationDate);
    console.log('isExpired', isExpired);*/
    return !!this.getJwtToken();
  }

  logout() {
    this.removeTokens();
    this.router.navigate(['/login']);
  }

  private removeTokens() {
    localStorage.removeItem(this.JWT_TOKEN);
    localStorage.removeItem(this.REFRESH_TOKEN);
    localStorage.removeItem('user');
  }

}

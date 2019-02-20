import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { User } from '../user';

@Injectable()
export class AuthenticationService {

  private authenticationUrl = 'http://localhost:8089/api/v1/auth/login';
  private isAuthenticatedUserUrl = 'http://localhost:8089/api/v1/auth/isAuthenticated';

  constructor(private http: HttpClient) {
  }

  setBearerToken(token) {
    localStorage.setItem('bearerToken', token);
  }

  getBearerToken() {
    return localStorage.getItem('bearerToken');
  }

  isUserAuthenticated(token): Promise<boolean> {
    const httpOptions = {
      headers: new HttpHeaders({
        'Authorization': `${token}`
      })
    };
    return this.http.post(this.isAuthenticatedUserUrl, '', httpOptions).toPromise().then(
      (onfulfilled) => {
        return onfulfilled['isAuthenticated'];
    });
  }

  authenticateUser(data) {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
    return this.http.post(this.authenticationUrl, data, httpOptions);
  }
}

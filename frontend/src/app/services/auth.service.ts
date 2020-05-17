import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {BehaviorSubject, Observable} from "rxjs";
import {User} from "../model/user";
import {map} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private currentUserSubject: BehaviorSubject<User>;
  public currentUser: Observable<User>;

  constructor(private http: HttpClient) {
    this.currentUserSubject = new BehaviorSubject<User>(JSON.parse(localStorage.getItem('currentUser')));
    this.currentUser = this.currentUserSubject.asObservable();
  }

  public get currentUserValue(): User {
    return this.currentUserSubject.value;
  }

  login(username: string, password: string) {
    return this.http.post<any>(`/api/users/authenticate`, {username, password}).pipe(map(user => {
          // store user details in local storage to keep logged in with refreshes
          localStorage.setItem('currentUser', JSON.stringify(user));
          // notify other components of the change
          this.currentUserSubject.next(user);
          return user;
    }));
  }

  // Fake login - temporary
  fakeLogin(){
    let user = new User();
    user.loginName = "Inkaru";
    localStorage.setItem('currentUser', JSON.stringify(user));
    this.currentUserSubject.next(user);
    return user;
  }

  logout() {
    // remove user from local storage to log user out
    localStorage.removeItem('currentUser');
    this.currentUserSubject.next(null);
  }

  // TODO : test if it works
  public registerUser(loginName: string, email: string, password: string, passwordRepeat: string){
    let params = new HttpParams().set('loginName',loginName);
    params.set('email',email);
    params.set('password',password);
    params.set('passwordRepeat',passwordRepeat);
    return this.http.post('/api/users/registerUser',{params: params});
  }
}

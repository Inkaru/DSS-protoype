import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {BehaviorSubject, Observable} from 'rxjs';
import {User} from '../model/user';

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

  login(username, password, callback) {

    const token = this.createBasicAuthToken(username, password);
    console.log(token);

    this.http.post<Observable<boolean>>('/api/login/authenticate', {},
      { headers: { authorization: token
        }}).subscribe(isValid => {
      if (isValid){
        console.log('valid');
        sessionStorage.setItem('token', token);
        this.getCurrentUser();
        return callback && callback();
      } else {
        console.log('invalid');
      }
    });
  }

  private createBasicAuthToken(username, password) {
    return 'Basic ' + window.btoa(username + ':' + password);
  }

  // Fake login - temporary
  fakeLogin(){
    const user = new User();
    user.loginName = 'Inkaru';
    user.firstName = 'Pierre-Antoine';
    user.lastName = 'Cabaret';
    user.description = 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce vestibulum vulputate ornare. Ut mollis dolor ut sem fringilla semper. Maecenas mattis hendrerit magna ac iaculis.';
    this.currentUserSubject.next(user);
    return user;
  }

  getCurrentUser(){
    this.http.get<User>('/api/login/user').subscribe(user => {
      console.log(user);
      this.currentUserSubject.next(user);
    });
  }

  logout() {
    this.http.post('/logout', {});
    this.currentUserSubject.next(null);
  }

  public registerUser(loginName: string, email: string, password: string, passwordRepeat: string){
    const header = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');
    const data = JSON.stringify({loginName, email, password, passwordRepeat});
    console.log(data);
    return this.http.post('/api/users/registerUser', data, {headers: header});
  }
}

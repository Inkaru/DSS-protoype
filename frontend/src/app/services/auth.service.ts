import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {BehaviorSubject, Observable} from 'rxjs';
import {User} from '../model/user';
import {map} from 'rxjs/operators';

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
    const user = new User();
    user.loginName = 'Inkaru';
    user.firstName = 'Pierre-Antoine';
    user.lastName = 'Cabaret';
    user.description = 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce vestibulum vulputate ornare. Ut mollis dolor ut sem fringilla semper. Maecenas mattis hendrerit magna ac iaculis.';
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
    const header = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');
    const data = JSON.stringify({loginName, email, password, passwordRepeat});
    console.log(data);
    return this.http.post('/api/users/registerUser', data, {headers: header});
  }
}

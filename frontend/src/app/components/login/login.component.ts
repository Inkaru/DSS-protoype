import {Component, OnInit} from '@angular/core';
import {AuthService} from '../../services/auth.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {first} from 'rxjs/operators';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  returnUrl: string;

  message: string;

  loginForm: FormGroup;
  loginError = '';
  logloading = false;
  logsubmitted = false;

  registerForm: FormGroup;
  registerError;
  regloading = false;
  regsubmitted = false;

  constructor(
    private authService: AuthService,
    private formBuilder: FormBuilder,
    private router: Router,
    private route: ActivatedRoute,
  ) {
    if (this.authService.currentUserValue) {
      this.router.navigate(['/']);
    }
  }

  ngOnInit(): void {
    this.loginForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });

    this.registerForm = this.formBuilder.group({
      loginName: ['', Validators.required],
      email: ['', Validators.required],
      password: ['', Validators.required],
      passwordConfirm: ['', Validators.required]
    });

    sessionStorage.setItem('token', '');

    // get return url from route parameters or default to '/'
    this.returnUrl = this.route.snapshot.queryParams.returnUrl || '/';
  }

  // convenience getter for easy access to form fields
  get logform() {
    return this.loginForm.controls;
  }

  get regform() {
    return this.registerForm.controls;
  }

  onSubmitLoginForm() {
    this.logsubmitted = true;

    // stop here if form is invalid
    if (this.loginForm.invalid) {
      return;
    }

    this.logloading = true;

    this.authService.login(
      this.logform.username.value,
      this.logform.password.value,
      () => this.goBack()
    );

    this.logloading = false;
  }

  onSubmitRegisterForm() {
    this.regsubmitted = true;

    if (this.registerForm.invalid) {
      return;
    }

    this.regloading = true;
    this.authService.registerUser(
      this.regform.loginName.value,
      this.regform.email.value,
      this.regform.password.value,
      this.regform.passwordConfirm.value
    ).pipe(first())
      .subscribe(
        data => {
          this.message = 'Registration sucessful, please login';
          // @ts-ignore
          $('#registerModal').modal('hide');
          this.resetRegisterForm();
        },
        error => {
          console.log(error);
          this.registerError = error.error.errors.map(err => err.defaultMessage);
          this.regloading = false;
        }
      );
  }

  resetRegisterForm() {
    this.registerForm.reset();
    this.regsubmitted = false;
  }

  tempLogin() {
    this.authService.fakeLogin();
    this.router.navigate([this.returnUrl]);
  }

  private goBack() {
    this.router.navigate([this.returnUrl]);
  }
}

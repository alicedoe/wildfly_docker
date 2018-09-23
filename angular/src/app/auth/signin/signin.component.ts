import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';

import * as fromApp from '../../shared/app.reducers';
import * as fromAuth from '../store';
import * as AuthActions from '../store/actions/auth.actions';

@Component({
  selector: 'app-signin',
  templateUrl: './signin.component.html',
  styleUrls: ['./signin.component.css']
})
export class SigninComponent implements OnInit {

  authState: Observable<fromAuth.AuthState>;

  constructor(private store: Store<fromApp.AppState>) { }

  ngOnInit() {
    this.authState = this.store<any>(fromAuth.reducer;
  }

  onSignin(form: NgForm) {
    const email = form.value.email;
    const password = form.value.password;
    this.store.dispatch(new AuthActions.TrySignin({username: email, password: password}));
  }

}

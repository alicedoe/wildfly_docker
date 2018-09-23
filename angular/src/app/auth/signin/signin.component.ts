import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';

import * as fromStore from '../store';
import * as AuthActions from '../store/actions/auth.actions';

@Component({
  selector: 'app-signin',
  templateUrl: './signin.component.html',
  styleUrls: ['./signin.component.css']
})
export class SigninComponent implements OnInit {
  
  error$: Observable<string>;

  constructor(private store: Store<fromStore.AuthState>) { }

  ngOnInit() {
    this.error$ = this.store.select(fromStore.getError);
  }

  onSignin(form: NgForm) {
    const email = form.value.email;
    const password = form.value.password;
    this.store.dispatch(new AuthActions.TrySignin({username: email, password: password}));
  }

}

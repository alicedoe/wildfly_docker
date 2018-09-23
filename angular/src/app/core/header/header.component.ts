import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Store } from '@ngrx/store';

import * as fromApp from '../../shared/app.reducers';
import * as fromAuth from '../../auth/store/reducers/auth.reducers';
import * as AuthActions from '../../auth/store/actions/auth.actions';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  authState: Observable<fromAuth.State>;
  constructor(private store: Store<fromApp.AppState>) {
  }

  ngOnInit() {
    this.authState = this.store.select('auth');
    this.authState.subscribe(res=> {
      if (res.authenticated != true) {
        this.store.dispatch(new AuthActions.TryTokenSignin());
      }
    });    
  }

  onLogout() {
    this.store.dispatch(new AuthActions.Logout());
  }
}

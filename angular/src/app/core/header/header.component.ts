import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Store } from '@ngrx/store';

import * as fromStore from '../../auth/store';
import * as AuthActions from '../../auth/store/actions/auth.actions';
import { UserDto } from '../../shared/models/userDto.model';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  authenticated$ : Observable<boolean>;
  user$ : Observable<UserDto>;

  constructor(private store: Store<fromStore.AuthState>) {
  }

  ngOnInit() {
    this.authenticated$ = this.store.select(fromStore.getAuthenticated);   
    this.user$ = this.store.select(fromStore.getUser);    
    
  }

  onLogout() {
    this.store.dispatch(new AuthActions.Logout());
  }
}

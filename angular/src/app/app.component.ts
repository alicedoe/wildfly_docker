import { Component } from '@angular/core';
import { Store } from '@ngrx/store';

import * as fromStore from './auth/store';
import * as AuthActions from './auth/store/actions/auth.actions';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'app';

  constructor(private store: Store<fromStore.AuthState>) {
    this.store.select(fromStore.getAuthenticated).subscribe(res=>{
      if (!res) {
        this.store.dispatch(new AuthActions.TryTokenSignin());
      }
    })
  }
}

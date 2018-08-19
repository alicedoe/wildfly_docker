import { Component, OnInit } from '@angular/core';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';

import * as fromApp from '../../shared/app.reducers';
import * as fromUserAdmin from './store/userAdmin.reducers';
import * as UserAdminActions from './store/userAdmin.actions';

@Component({
  selector: 'app-user-admin',
  templateUrl: './user-admin.component.html',
  styleUrls: ['./user-admin.component.css']
})
export class UserAdminComponent implements OnInit {

  userAdminState: Observable<fromUserAdmin.State>;
  constructor(private store: Store<fromApp.AppState>) { }

  ngOnInit() {
    this.userAdminState = this.store.select('userAdmin');
    this.store.dispatch(new UserAdminActions.GetAllUsers());
  }

}

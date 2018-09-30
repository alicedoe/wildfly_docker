import { Component, OnInit } from '@angular/core';
import { Store } from '@ngrx/store';

import * as fromStore from '../store';
import * as UserAdminActions from '../store/actions/userAdmin.actions';

@Component({
  selector: 'app-user-admin',
  templateUrl: './user-admin.component.html',
  styleUrls: ['./user-admin.component.css']
})
export class UserAdminComponent implements OnInit {

  constructor(private store: Store<fromStore.UserAdminState>) { }

  ngOnInit() {
    this.store.dispatch(new UserAdminActions.GetRoles());
    this.store.dispatch(new UserAdminActions.GetUsers());
  }

}

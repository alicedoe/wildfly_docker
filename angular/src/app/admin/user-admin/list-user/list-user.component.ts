import { Component, OnInit } from '@angular/core';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';

import * as fromApp from '../../../shared/app.reducers';
import * as fromUserAdmin from '../../store/reducers/userAdmin.reducers';
import * as UserAdminActions from '../../store/actions/userAdmin.actions';
import { UserDto } from '../../../shared/models/userDto.model';

@Component({
  selector: 'app-list-user',
  templateUrl: './list-user.component.html',
  styleUrls: ['./list-user.component.css']
})
export class ListUserComponent implements OnInit {

  userAdminState: Observable<fromUserAdmin.UserAdminState>;
  users: Array<UserDto>;

  constructor(private store: Store<fromApp.AppState>) { }

  ngOnInit() {
    this.userAdminState = this.store.select('userAdmin');
    this.store.dispatch(new UserAdminActions.GetUsers());
    this.userAdminState.subscribe(res => {
      this.users = res.users;
    })
  }

  onDeleteUser(id:number) {
    this.store.dispatch(new UserAdminActions.DeleteUser(id));
  }

  onEditUser(id:number) {
    this.store.dispatch(new UserAdminActions.EditUser(id));
  }
}

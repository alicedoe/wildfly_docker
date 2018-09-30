import { Component, OnInit } from '@angular/core';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';

import * as fromStore from '../../store';
import * as UserAdminActions from '../../store/actions/userAdmin.actions';
import { UserDto } from '../../../shared/models/userDto.model';

@Component({
  selector: 'app-list-user',
  templateUrl: './list-user.component.html',
  styleUrls: ['./list-user.component.css']
})
export class ListUserComponent implements OnInit {

  users$: Observable<Array<UserDto>>;

  constructor(private store: Store<fromStore.UserAdminState>) { }

  ngOnInit() {
    this.users$ = this.store.select(fromStore.getUsers);
  }

  onDeleteUser(id:number) {
    this.store.dispatch(new UserAdminActions.DeleteUser(id));
  }

  onEditUser(id:number) {
    this.store.dispatch(new UserAdminActions.EditUser(id));
  }
}

import { Action } from '@ngrx/store';
import { RoleDto } from '../../models/roleDto.model';
import { UserDto } from '../../../shared/models/userDto.model';

export const AU_GET_USER = 'AU_GET_USER';
export const AU_EDIT_USER = 'AU_EDIT_USER';
export const AU_SAVE_USER = 'AU_SAVE_USER';
export const AU_GET_USERS = 'AU_GET_USERS';
export const AU_SET_USERS = 'AU_SET_USERS';
export const AU_SET_USER = 'AU_SET_USER';
export const AU_GET_ROLES = 'AU_GET_ROLES';
export const AU_SET_ROLES = 'AU_SET_ROLES';
export const AU_CREATE_USER = 'AU_CREATE_USER';
export const AU_DELETE_USER = 'AU_DELETE_USER';
export const AU_ADMIN_ERROR = 'AU_ADMIN_ERROR';

export class SaveUser implements Action {
  readonly type = AU_SAVE_USER;
  constructor(public newUser: UserDto) {}
}

export class DeleteUser implements Action {
  readonly type = AU_DELETE_USER;
  constructor(public id: number) {}
}

export class CreateUser implements Action {
  readonly type = AU_CREATE_USER;
  constructor(public newUser: UserDto) {}
}

export class GetRoles implements Action {
  readonly type = AU_GET_ROLES;
  constructor() {}
}

export class SetRoles implements Action {
  readonly type = AU_SET_ROLES;
  constructor(public payload: Array<RoleDto>) {}
}

export class EditUser implements Action {
  readonly type = AU_EDIT_USER;
  constructor(public id: number) {}
}

export class GetUser implements Action {
  readonly type = AU_GET_USER;
  constructor(public id: number) {}
}

export class GetUsers implements Action {
    readonly type = AU_GET_USERS;
    constructor() {}
  }

export class SetUsers implements Action {
    readonly type = AU_SET_USERS;
    constructor(public payload: Array<UserDto>) {}
  }

export class SetUser implements Action {
    readonly type = AU_SET_USER;
    constructor(public payload: UserDto) {}
  }

export class AdminError implements Action {
  readonly type = AU_ADMIN_ERROR;
  constructor(public payload: string) {}
}

export type UserAdminActions = 
    SaveUser |  EditUser | GetUsers | GetUser | AdminError | SetUsers | SetUser | GetRoles | SetRoles | CreateUser | DeleteUser;
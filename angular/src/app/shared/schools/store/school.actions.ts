import { Action } from '@ngrx/store';

import { SchoolDto } from '../schoolDto.model';

export const ADD_SCHOOL = 'ADD_SCHOOL';
export const UPDATE_SCHOOL = 'UPDATE_SCHOOL';
export const DELETE_SCHOOL = 'DELETE_SCHOOL';
export const GET_SCHOOL = 'GET_SCHOOL';
export const GET_SCHOOLS = 'GET_SCHOOLS';
export const SET_SCHOOLS = 'SET_SCHOOLS';

export class AddSChool implements Action {
  readonly type = ADD_SCHOOL;

  constructor(public payload: SchoolDto) {}
}

export class UpdateSchool implements Action {
  readonly type = UPDATE_SCHOOL;

  constructor(public payload: {index: number, updatedRecipe: SchoolDto}) {}
}

export class DeleteSchool implements Action {
  readonly type = DELETE_SCHOOL;

  constructor(public payload: number) {}
}

export class GetSchools implements Action {
  readonly type = GET_SCHOOLS;
}

export class GetSchool implements Action {
  readonly type = GET_SCHOOL;

  constructor(public id: number) {}
}

export class SetSchools implements Action {
  readonly type = SET_SCHOOLS;

  constructor(public payload: SchoolDto[]) {}
}

export type SchoolActions = AddSChool |
  UpdateSchool |
  DeleteSchool |
  GetSchools |
  SetSchools |
  GetSchool;

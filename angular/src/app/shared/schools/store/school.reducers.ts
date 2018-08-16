import { SchoolDto } from '../schoolDto.model';
import * as SchoolActions from './school.actions';
import * as fromApp from '../../app.reducers';

export interface FeatureState extends fromApp.AppState {
  schools: State
}

export interface State {
  schools: SchoolDto[];
}

const initialState: State = {
  schools: []
};

export function schoolReducer(state = initialState, action: SchoolActions.SchoolActions) {
  switch (action.type) {
    case (SchoolActions.GET_SCHOOLS):
      console.log('SchoolActions.GET_SCHOOLS');
      return {
        ...state
      };
    default:
      return state;
  }
}

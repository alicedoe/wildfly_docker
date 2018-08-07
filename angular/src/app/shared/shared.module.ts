import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EffectsModule } from '@ngrx/effects';
import { StoreModule } from '@ngrx/store';

import { SchoolEffects } from './schools/store/school.effects';
import { schoolReducer } from './schools/store/school.reducers';
import { UserComponent } from './user/user.component';

@NgModule({
  imports: [
    CommonModule,
    StoreModule.forFeature('schools', schoolReducer),
    EffectsModule.forFeature([SchoolEffects])
  ],
  declarations: [UserComponent]
})
export class SharedModule { }

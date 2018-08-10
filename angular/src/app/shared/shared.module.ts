import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EffectsModule } from '@ngrx/effects';
import { StoreModule } from '@ngrx/store';

import { SchoolEffects } from './schools/store/school.effects';
import { schoolReducer } from './schools/store/school.reducers';

@NgModule({
  imports: [
    CommonModule,
    StoreModule.forFeature('schools', schoolReducer),
    EffectsModule.forFeature([SchoolEffects])
  ]
})
export class SharedModule { }

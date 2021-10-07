import { NgModule } from '@angular/core';

import { T3ExtendedService, T3DetailsExtendedComponent, T3ListExtendedComponent, T3NewExtendedComponent } from './';
import { T3Service } from 'src/app/entities/t-3';
import { T3Module } from 'src/app/entities/t-3/t-3.module';
import { t3Route } from './t-3.route';

import { SharedModule  } from 'src/app/common/shared';
import { GeneralComponentsExtendedModule } from 'src/app/extended/common/general-components/general-extended.module';

const entities = [
    T3DetailsExtendedComponent, T3ListExtendedComponent, T3NewExtendedComponent 
  ]
@NgModule({
	declarations: entities,
	exports: entities,
  imports: [
    t3Route,
    T3Module,
    SharedModule,
    GeneralComponentsExtendedModule,
  ],
  providers: [{ provide: T3Service, useClass: T3ExtendedService }],
})
export class T3ExtendedModule {
}

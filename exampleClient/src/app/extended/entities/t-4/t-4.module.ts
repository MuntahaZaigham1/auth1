import { NgModule } from '@angular/core';

import { T4ExtendedService, T4DetailsExtendedComponent, T4ListExtendedComponent, T4NewExtendedComponent } from './';
import { T4Service } from 'src/app/entities/t-4';
import { T4Module } from 'src/app/entities/t-4/t-4.module';
import { t4Route } from './t-4.route';

import { SharedModule  } from 'src/app/common/shared';
import { GeneralComponentsExtendedModule } from 'src/app/extended/common/general-components/general-extended.module';

const entities = [
    T4DetailsExtendedComponent, T4ListExtendedComponent, T4NewExtendedComponent 
  ]
@NgModule({
	declarations: entities,
	exports: entities,
  imports: [
    t4Route,
    T4Module,
    SharedModule,
    GeneralComponentsExtendedModule,
  ],
  providers: [{ provide: T4Service, useClass: T4ExtendedService }],
})
export class T4ExtendedModule {
}

import { NgModule } from '@angular/core';

import { T4DetailsComponent, T4ListComponent, T4NewComponent} from './';
import { t4Route } from './t-4.route';

import { SharedModule } from 'src/app/common/shared';
import { GeneralComponentsModule } from 'src/app/common/general-components/general.module';

const entities = [
    T4DetailsComponent, T4ListComponent,T4NewComponent
  ]
@NgModule({
	declarations: entities,
	exports: entities,
  imports: [
    t4Route,
    SharedModule,
    GeneralComponentsModule,
  ]
})
export class T4Module {
}

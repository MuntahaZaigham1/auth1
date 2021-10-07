import { NgModule } from '@angular/core';

import { T3DetailsComponent, T3ListComponent, T3NewComponent} from './';
import { t3Route } from './t-3.route';

import { SharedModule } from 'src/app/common/shared';
import { GeneralComponentsModule } from 'src/app/common/general-components/general.module';

const entities = [
    T3DetailsComponent, T3ListComponent,T3NewComponent
  ]
@NgModule({
	declarations: entities,
	exports: entities,
  imports: [
    t3Route,
    SharedModule,
    GeneralComponentsModule,
  ]
})
export class T3Module {
}

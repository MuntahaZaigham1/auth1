
import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from "@angular/core";
// import { CanDeactivateGuard } from 'src/app/core/guards/can-deactivate.guard';
// import { AuthGuard } from 'src/app/core/guards/auth.guard';

// import { T3DetailsComponent, T3ListComponent, T3NewComponent } from './';

const routes: Routes = [
	// { path: '', component: T3ListComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ] },
	// { path: ':id', component: T3DetailsComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ] },
	// { path: 'new', component: T3NewComponent, canActivate: [ AuthGuard ] },
];

export const t3Route: ModuleWithProviders<any> = RouterModule.forChild(routes);

import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from "@angular/core";
import { CanDeactivateGuard } from 'src/app/core/guards/can-deactivate.guard';
import { AuthGuard } from 'src/app/core/guards/auth.guard';
import { T3DetailsExtendedComponent, T3ListExtendedComponent , T3NewExtendedComponent } from './';

const routes: Routes = [
	{ path: '', component: T3ListExtendedComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ] },
	{ path: ':id', component: T3DetailsExtendedComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ] },
	{ path: 'new', component: T3NewExtendedComponent, canActivate: [ AuthGuard ] },
];

export const t3Route: ModuleWithProviders<any> = RouterModule.forChild(routes);
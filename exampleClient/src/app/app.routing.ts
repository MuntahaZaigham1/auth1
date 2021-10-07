
import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from "@angular/core";
import { SwaggerComponent } from './core/swagger/swagger.component';
import { ErrorPageComponent  } from './core/error-page/error-page.component';
import { AuthGuard } from './core/guards/auth.guard';

const routes: Routes = [
	{
		path: '',
		loadChildren: () => import('./extended/core/core.module').then(m => m.CoreExtendedModule),
	},
  	{ path: "swagger-ui", component: SwaggerComponent , canActivate: [ AuthGuard ] },
	{
		path: '',
		loadChildren: () => import('./extended/admin/admin.module').then(m => m.AdminExtendedModule),
	},
	{
		path: '',
		loadChildren: () => import('./extended/account/account.module').then(m => m.AccountExtendedModule),
	},
	{
		path: 't1',
		loadChildren: () => import('./extended/entities/t-1/t-1.module').then(m => m.T1ExtendedModule),
		canActivate: [AuthGuard]
	},
	{
		path: 't4',
		loadChildren: () => import('./extended/entities/t-4/t-4.module').then(m => m.T4ExtendedModule),
		canActivate: [AuthGuard]
	},
	{
		path: 't3',
		loadChildren: () => import('./extended/entities/t-3/t-3.module').then(m => m.T3ExtendedModule),
		canActivate: [AuthGuard]
	},
	{ path: '**', component:ErrorPageComponent},
	
];

export const routingModule: ModuleWithProviders<any> = RouterModule.forRoot(routes);
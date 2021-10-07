import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from "@angular/router";
import { FormBuilder } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';

import { T4ExtendedService } from '../t-4.service';

import { PickerDialogService } from 'src/app/common/shared';
import { ErrorService } from 'src/app/core/services/error.service';


import { GlobalPermissionService } from 'src/app/core/services/global-permission.service';
import { T4DetailsComponent } from 'src/app/entities/t-4/index';

@Component({
  selector: 'app-t-4-details',
  templateUrl: './t-4-details.component.html',
  styleUrls: ['./t-4-details.component.scss']
})
export class T4DetailsExtendedComponent extends T4DetailsComponent implements OnInit {
	title:string='T4';
	parentUrl:string='t4';
	//roles: IRole[];  
	constructor(
		public formBuilder: FormBuilder,
		public router: Router,
		public route: ActivatedRoute,
		public dialog: MatDialog,
		public t4ExtendedService: T4ExtendedService,
		public pickerDialogService: PickerDialogService,
		public errorService: ErrorService,
		public globalPermissionService: GlobalPermissionService,
	) {
		super(formBuilder, router, route, dialog, t4ExtendedService, pickerDialogService, errorService,
		globalPermissionService,
		);
  }

	ngOnInit() {
		super.ngOnInit();
  }
  
}

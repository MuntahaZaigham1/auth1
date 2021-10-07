import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from "@angular/router";
import { FormBuilder } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';

import { T3ExtendedService } from '../t-3.service';

import { PickerDialogService } from 'src/app/common/shared';
import { ErrorService } from 'src/app/core/services/error.service';


import { GlobalPermissionService } from 'src/app/core/services/global-permission.service';
import { T3DetailsComponent } from 'src/app/entities/t-3/index';

@Component({
  selector: 'app-t-3-details',
  templateUrl: './t-3-details.component.html',
  styleUrls: ['./t-3-details.component.scss']
})
export class T3DetailsExtendedComponent extends T3DetailsComponent implements OnInit {
	title:string='T3';
	parentUrl:string='t3';
	//roles: IRole[];  
	constructor(
		public formBuilder: FormBuilder,
		public router: Router,
		public route: ActivatedRoute,
		public dialog: MatDialog,
		public t3ExtendedService: T3ExtendedService,
		public pickerDialogService: PickerDialogService,
		public errorService: ErrorService,
		public globalPermissionService: GlobalPermissionService,
	) {
		super(formBuilder, router, route, dialog, t3ExtendedService, pickerDialogService, errorService,
		globalPermissionService,
		);
  }

	ngOnInit() {
		super.ngOnInit();
  }
  
}

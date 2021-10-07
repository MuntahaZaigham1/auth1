import { Component, OnInit, Inject } from '@angular/core';
import { T4ExtendedService } from '../t-4.service';

import { ActivatedRoute,Router } from "@angular/router";
import { FormBuilder } from '@angular/forms';
import { PickerDialogService } from 'src/app/common/shared';
import { ErrorService } from 'src/app/core/services/error.service';
import { MatDialogRef, MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';

import { GlobalPermissionService } from 'src/app/core/services/global-permission.service';

import { T4NewComponent } from 'src/app/entities/t-4/index';

@Component({
  selector: 'app-t-4-new',
  templateUrl: './t-4-new.component.html',
  styleUrls: ['./t-4-new.component.scss']
})
export class T4NewExtendedComponent extends T4NewComponent implements OnInit {
  
    title:string = "New T4";
	constructor(
		public formBuilder: FormBuilder,
		public router: Router,
		public route: ActivatedRoute,
		public dialog: MatDialog,
		public dialogRef: MatDialogRef<T4NewComponent>,
		@Inject(MAT_DIALOG_DATA) public data: any,
		public pickerDialogService: PickerDialogService,
		public t4ExtendedService: T4ExtendedService,
		public errorService: ErrorService,
		public globalPermissionService: GlobalPermissionService,
	) {
		super(formBuilder, router, route, dialog, dialogRef, data, pickerDialogService, t4ExtendedService, errorService,
		globalPermissionService,
		);
	}
 
	ngOnInit() {
		super.ngOnInit();
  }
     
}

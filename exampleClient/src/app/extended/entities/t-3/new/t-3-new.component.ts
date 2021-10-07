import { Component, OnInit, Inject } from '@angular/core';
import { T3ExtendedService } from '../t-3.service';

import { ActivatedRoute,Router } from "@angular/router";
import { FormBuilder } from '@angular/forms';
import { PickerDialogService } from 'src/app/common/shared';
import { ErrorService } from 'src/app/core/services/error.service';
import { MatDialogRef, MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';

import { GlobalPermissionService } from 'src/app/core/services/global-permission.service';

import { T3NewComponent } from 'src/app/entities/t-3/index';

@Component({
  selector: 'app-t-3-new',
  templateUrl: './t-3-new.component.html',
  styleUrls: ['./t-3-new.component.scss']
})
export class T3NewExtendedComponent extends T3NewComponent implements OnInit {
  
    title:string = "New T3";
	constructor(
		public formBuilder: FormBuilder,
		public router: Router,
		public route: ActivatedRoute,
		public dialog: MatDialog,
		public dialogRef: MatDialogRef<T3NewComponent>,
		@Inject(MAT_DIALOG_DATA) public data: any,
		public pickerDialogService: PickerDialogService,
		public t3ExtendedService: T3ExtendedService,
		public errorService: ErrorService,
		public globalPermissionService: GlobalPermissionService,
	) {
		super(formBuilder, router, route, dialog, dialogRef, data, pickerDialogService, t3ExtendedService, errorService,
		globalPermissionService,
		);
	}
 
	ngOnInit() {
		super.ngOnInit();
  }
     
}

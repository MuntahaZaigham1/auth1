import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';

import { IT3 } from '../it-3';
import { T3Service } from '../t-3.service';
import { Router, ActivatedRoute } from '@angular/router';
import { T3NewComponent } from '../new/t-3-new.component';
import { BaseListComponent, ListColumnType, PickerDialogService } from 'src/app/common/shared';
import { ErrorService } from 'src/app/core/services/error.service';
import { Globals } from 'src/app/core/services/globals';
import { GlobalPermissionService } from 'src/app/core/services/global-permission.service';


@Component({
  selector: 'app-t-3-list',
  templateUrl: './t-3-list.component.html',
  styleUrls: ['./t-3-list.component.scss']
})
export class T3ListComponent extends BaseListComponent<IT3> implements OnInit {

	title = 'T3';
	constructor(
		public router: Router,
		public route: ActivatedRoute,
		public global: Globals,
		public dialog: MatDialog,
		public changeDetectorRefs: ChangeDetectorRef,
		public pickerDialogService: PickerDialogService,
		public t3Service: T3Service,
		public errorService: ErrorService,
		public globalPermissionService: GlobalPermissionService,
	) { 
		super(router, route, dialog, global, changeDetectorRefs, pickerDialogService, t3Service, errorService)
  }

	ngOnInit() {
		this.entityName = 'T3';
		this.setAssociation();
		this.setColumns();
		this.primaryKeys = ['id', ]
		super.ngOnInit();
	}
  
  
	setAssociation(){
  	
		this.associations = [
		];
	}
  
  	setColumns(){
  		this.columns = [
    		{
				column: 'score',
				searchColumn: 'score',
				label: 'score',
				sort: true,
				filter: true,
                       type : ListColumnType.Array,
                       subtype : ListColumnType.Number,
			},
    		{
				column: 'id',
				searchColumn: 'id',
				label: 'id',
				sort: true,
				filter: true,
				type: ListColumnType.Number
			},
		  	{
				column: 'actions',
				label: 'Actions',
				sort: false,
				filter: false,
				type: ListColumnType.String
			}
		];
		this.selectedColumns = this.columns;
		this.displayedColumns = this.columns.map((obj) => { return obj.column });
  	}
  addNew(comp) {
	if(!comp){
		comp = T3NewComponent;
	}
	super.addNew(comp);
  }
  
}

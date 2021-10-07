import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { RouterTestingModule } from '@angular/router/testing';
import { DetailsComponent, ListComponent, FieldsComp } from 'src/app/common/general-components';

import { TestingModule, EntryComponents } from 'src/testing/utils';
import { T4ExtendedService, T4DetailsExtendedComponent, T4ListExtendedComponent } from '../';
import { IT4 } from 'src/app/entities/t-4';
describe('T4DetailsExtendedComponent', () => {
  let component: T4DetailsExtendedComponent;
  let fixture: ComponentFixture<T4DetailsExtendedComponent>;
  let el: HTMLElement;
  
  describe('Unit Tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [
          T4DetailsExtendedComponent,
          DetailsComponent
        ],
        imports: [TestingModule],
        providers: [
          T4ExtendedService,
        ],
        schemas: [NO_ERRORS_SCHEMA]  
      }).compileComponents();
    }));
  
    beforeEach(() => {
      fixture = TestBed.createComponent(T4DetailsExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });

  });
  
  describe('Integration Tests', () => {
    beforeEach(async(() => {

      TestBed.configureTestingModule({
        declarations: [
          T4DetailsExtendedComponent,
          T4ListExtendedComponent,
          DetailsComponent,
          ListComponent,
          FieldsComp
        ].concat(EntryComponents),
        imports: [
          TestingModule,
          RouterTestingModule.withRoutes([
            { path: 't4', component: T4DetailsExtendedComponent },
            { path: 't4/:id', component: T4ListExtendedComponent }
          ])
        ],
        providers: [
          T4ExtendedService
        ]

      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(T4DetailsExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });

  });
  
});

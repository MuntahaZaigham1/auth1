
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { T4Service } from 'src/app/entities/t-4/t-4.service';
@Injectable({
  providedIn: 'root'
})
export class T4ExtendedService extends T4Service {
	constructor(protected httpclient: HttpClient) { 
    super(httpclient);
    this.url += '/extended';
  }
}

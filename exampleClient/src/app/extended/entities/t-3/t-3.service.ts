
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { T3Service } from 'src/app/entities/t-3/t-3.service';
@Injectable({
  providedIn: 'root'
})
export class T3ExtendedService extends T3Service {
	constructor(protected httpclient: HttpClient) { 
    super(httpclient);
    this.url += '/extended';
  }
}

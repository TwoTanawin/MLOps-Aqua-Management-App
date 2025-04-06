import { Component } from '@angular/core';
import {Observable, Observer} from 'rxjs';
import {MatTabsModule} from '@angular/material/tabs';
import {AsyncPipe} from '@angular/common';
import { PondInfoCardComponent } from '../pond-info-card/pond-info-card.component';
import { PondInfoTableComponent } from "../pond-info-table/pond-info-table.component";
import { SimpleChartComponent } from "../simple-chart/simple-chart.component";

export interface TabNav {
  label: string;
  content: string;
}

@Component({
  selector: 'app-tab-nav',
  imports: [MatTabsModule, AsyncPipe, PondInfoCardComponent, PondInfoTableComponent, SimpleChartComponent],
  templateUrl: './tab-nav.component.html',
  styleUrl: './tab-nav.component.scss'
})

export class TabNavComponent {
  asyncTabs: Observable<TabNav[]>;

  constructor() {
    this.asyncTabs = new Observable((observer: Observer<TabNav[]>) => {
      setTimeout(() => {
        observer.next([
          {label: 'First', content: 'Content 1'},
          {label: 'Second', content: 'Content 2'},
          {label: 'Third', content: 'Content 3'},
        ]);
      }, 1000);
    });
  }
}

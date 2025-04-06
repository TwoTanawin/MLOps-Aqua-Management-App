import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PondInfoTableComponent } from './pond-info-table.component';

describe('PondInfoTableComponent', () => {
  let component: PondInfoTableComponent;
  let fixture: ComponentFixture<PondInfoTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PondInfoTableComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PondInfoTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

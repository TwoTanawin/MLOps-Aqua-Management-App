import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PondInfoCardComponent } from './pond-info-card.component';

describe('PondInfoCardComponent', () => {
  let component: PondInfoCardComponent;
  let fixture: ComponentFixture<PondInfoCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PondInfoCardComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PondInfoCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

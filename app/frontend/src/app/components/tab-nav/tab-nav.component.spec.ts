import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TabNavComponent } from './tab-nav.component';

describe('TabNavComponent', () => {
  let component: TabNavComponent;
  let fixture: ComponentFixture<TabNavComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TabNavComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TabNavComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

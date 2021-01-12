import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OrganogramaComponent } from './organograma.component';

describe('OrganogramaComponent', () => {
  let component: OrganogramaComponent;
  let fixture: ComponentFixture<OrganogramaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OrganogramaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OrganogramaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

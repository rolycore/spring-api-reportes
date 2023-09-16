import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReportetecnicoComponent } from './reportetecnico.component';

describe('ReportetecnicoComponent', () => {
  let component: ReportetecnicoComponent;
  let fixture: ComponentFixture<ReportetecnicoComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ReportetecnicoComponent]
    });
    fixture = TestBed.createComponent(ReportetecnicoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

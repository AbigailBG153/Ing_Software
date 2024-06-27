import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BodyAuthComponent } from './body-auth.component';

describe('BodyAuthComponent', () => {
  let component: BodyAuthComponent;
  let fixture: ComponentFixture<BodyAuthComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [BodyAuthComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BodyAuthComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

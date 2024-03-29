import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddEditConfigModelComponent } from './add-edit-config-model.component';

describe('AddEditConfigComponent', () => {
  let component: AddEditConfigModelComponent;
  let fixture: ComponentFixture<AddEditConfigModelComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddEditConfigModelComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AddEditConfigModelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

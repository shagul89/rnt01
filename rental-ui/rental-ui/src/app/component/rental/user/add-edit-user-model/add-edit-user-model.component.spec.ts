import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddEditUserModelComponent } from './add-edit-user-model.component';

describe('AddEditUserModelComponent', () => {
  let component: AddEditUserModelComponent;
  let fixture: ComponentFixture<AddEditUserModelComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddEditUserModelComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AddEditUserModelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddEditCustomerModelComponent } from './add-edit-customer-model.component';

describe('AddEditCustomerModelComponent', () => {
  let component: AddEditCustomerModelComponent;
  let fixture: ComponentFixture<AddEditCustomerModelComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddEditCustomerModelComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AddEditCustomerModelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

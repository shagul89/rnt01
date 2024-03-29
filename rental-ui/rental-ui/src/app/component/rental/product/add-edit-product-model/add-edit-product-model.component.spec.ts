import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddEditProductModelComponent } from './add-edit-product-model.component';

describe('AddEditProductModelComponent', () => {
  let component: AddEditProductModelComponent;
  let fixture: ComponentFixture<AddEditProductModelComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddEditProductModelComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AddEditProductModelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

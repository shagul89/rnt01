import { Component, Inject } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Configuration } from '../../../model/config';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { RentalService } from '../../rental-service';

@Component({
  selector: 'app-add-edit-config-model',
  templateUrl: './add-edit-config-model.component.html',
  styleUrl: './add-edit-config-model.component.css'
})
export class AddEditConfigModelComponent {

  isEdit = false;
  form: any;
  title!: string;
  config: Configuration = new Configuration;

  constructor(public dialog: MatDialogRef<any>, @Inject(MAT_DIALOG_DATA) data: any, private formBuilder: FormBuilder, private rental: RentalService, private toastr: ToastrService) {
    this.title = data.title;
    if (data && data.config && data.config.configId) {
      this.isEdit = true;
      this.config = data.config;
    }
  }

  ngOnInit() {
    this.form = this.formBuilder.group({
      name: ['', Validators.required],
      value: ['', Validators.required],
      description: ['', [Validators.required]]
    });
    if (this.isEdit) {
      this.form.controls.name.patchValue(this.config.name);
      this.form.controls.value.patchValue(this.config.value);
      this.form.controls.description.patchValue(this.config.description);
      this.getConfigurationById(this.config.configId);
    }
  }

  convertFormToConfig() {
    this.config.name = this.form.get('name').value;
    this.config.value = this.form.get('value').value;
    this.config.description = this.form.get('description').value;
  }

  getConfigurationById(configId: number) {
    this.rental.getConfigurationById(configId).subscribe({
      next: (data) => {
        this.config = data.outputData.responseData;
      },
      error: (error) => {
        this.toastr.error("Failed to get configuration data");
      }
    });
  }

  save() {
    this.rental.saveConfiguration(this.config).subscribe({
      next: () => {
        this.toastr.success("Configuration data created successfully");
        this.dialog.close();
      },
      error: () => {
        this.toastr.error("Configuration data failed to create");
      }
    });
  }

  update() {
    this.rental.updateConfiguration(this.config).subscribe({
      next: () => {
        this.toastr.success("Configuration data created successfully");
        this.dialog.close();
      },
      error: () => {
        this.toastr.error("Configuration data failed to create");
      }
    });
  }

  submit() {
    if (this.form.invalid) {
      return;
    }
    this.convertFormToConfig();
    if (this.isEdit) {
      this.update();
    } else {
      this.save();
    }
  }

}

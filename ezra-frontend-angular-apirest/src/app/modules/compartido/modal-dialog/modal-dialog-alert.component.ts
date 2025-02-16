import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'modal-dialog-alert',
  templateUrl: './modal-dialog-alert.component.html',
  styleUrl: './modal-dialog-alert.component.css'
})

export class ModalDialogAlertComponent {
  title: string;
  icon: string;
  message: string;
  typeAlert: string;
  showYesButton:boolean;

  constructor(
    private modal: MatDialogRef<ModalDialogAlertComponent>,
    @Inject(MAT_DIALOG_DATA) private data: DataRecived
  ) {
    this.title = this.data?.title;
    this.icon = this.data?.icon;
    this.message = this.data?.message;
    this.typeAlert = this.data?.typeAlert;
    this.showYesButton = this.data?.showYesButton;
  }
  closeModal(): void{
    this.modal.close();
  }
}

interface DataRecived  {
  title: string,
  icon: string,
  message: string,
  typeAlert: string,
  showYesButton:boolean
}

import { CommonModule } from '@angular/common';
import { Component, Inject } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatCardModule } from '@angular/material/card';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatRadioModule } from '@angular/material/radio';
import { MatSelectModule } from '@angular/material/select';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-detalle-item-pedido',
  templateUrl: './detalle-item-pedido.component.html',
  styleUrl: './detalle-item-pedido.component.css',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule, ReactiveFormsModule, MatCardModule, MatAutocompleteModule, MatSelectModule, MatRadioModule, MatIconModule, MatDialogModule]
})
export class DetalleItemPedidoComponent {

  detalle!: string;

  constructor(
    public dialogRef: MatDialogRef<DetalleItemPedidoComponent>,
    @Inject(MAT_DIALOG_DATA) public data: string){
      this.detalle=data;
  }

  cancelar(): void {
    this.dialogRef.close();
  }

}

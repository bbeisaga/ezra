import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-detalle-item-pedido',
  templateUrl: './detalle-item-pedido.component.html',
  styleUrl: './detalle-item-pedido.component.css'
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

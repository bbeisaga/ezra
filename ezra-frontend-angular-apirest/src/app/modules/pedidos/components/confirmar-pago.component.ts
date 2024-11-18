import { CommonModule } from '@angular/common';
import { ChangeDetectionStrategy, Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Pedido } from '../../../models/pedido';
import { PedidoService } from '../../../services/pedido.service';
import { Router } from '@angular/router';
import swal from 'sweetalert2';
import {MatCheckboxModule} from '@angular/material/checkbox'


@Component({
  selector: 'app-confirmar-pago',
  templateUrl: './confirmar-pago.component.html',
  styleUrl: './confirmar-pago.component.css',
})
export class ConfirmarPagoComponent {
  pedido: Pedido ;


  constructor(private pedidoService: PedidoService,
    private ro : Router,

    /************matDialog********/
    public dialogRef: MatDialogRef<ConfirmarPagoComponent>,
    @Inject(MAT_DIALOG_DATA)
    public data: Pedido,
    /****************************/
  ) {
    this.pedido = data;
    console.log(this.pedido);
    //dialogRef.disableClose = true;
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  onSubmitForm(){
   // console.log("aaaaaaa",this.pedido);
   // this.pedido.pago = this.pedido.apagar;
    console.log("bbbbbbbbb", this.pedido);
   // debugger;
    /*console.log(this.pago);
    console.log( this.pedido.pago);*/
    this.pedidoService.update(this.pedido)
      .subscribe(
        json => {
          //this.ro.navigate(['/pr/clientes']);
          this.dialogRef.close(json.pedido)
          swal.fire('Pedido Actualizado', 'success');
        },
        err => {
          console.error('Código del error desde el backend: ' + err.status);
          console.error(err.error.errors);
        }
      )


  }

}

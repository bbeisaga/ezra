import { CommonModule } from '@angular/common';
import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { Pedido } from '../../../models/pedido';
import { flatMap, map, Observable } from 'rxjs';
import { Producto } from '../../../models/producto';
import { ClienteService } from '../../../services/cliente.service';
import { PedidoService } from '../../../services/pedido.service';
import { ActivatedRoute, Router } from '@angular/router';
import { MatAutocompleteSelectedEvent } from '@angular/material/autocomplete';
import { ItemPedido } from '../../../models/item-pedido';
import { FormControl } from '@angular/forms';
import swal from 'sweetalert2';
import { Cliente } from '../../../models/cliente';
import { ProductoService } from '../../../services/producto.service';
import { GenericosDeProductoService } from '../../../services/genericos-de-producto.service';
import { Empaque } from '../../../models/empaque';
import { TipoPedido } from '../../../models/tipo-pedido';
import { AlertService } from '../../../services/alert.service';

@Component({
  selector: 'app-form-pedido',
  templateUrl: './form-pedido.component.html',
  styleUrl: './form-pedido.component.css'
})
export class FormPedidoComponent implements OnInit {

  //titulo: string = 'Nuevo Pedido';
  //  displayedColumns: string[] = ['producto', 'precio', 'cantidad', 'importe', 'descripcion', 'eliminar'];
    pedido: Pedido = new Pedido();
    empaques: Empaque[]=[];
    cliente!: Cliente;
    autocompleteControl = new FormControl();
    tipoPedidoVentaClientes!:TipoPedido ;
    //tipoPedidoVentaClientes: boolean=false;
/*     tipoPedidos: TipoPedido[] = [
      {"id": 1, "codigo":true, "nombre": "VENTA A CLIENTES"},
      {"id": 2, "codigo":false, "nombre": "COMPRA O ADQUISICION"},
    ]; */
    tipoPedidos: TipoPedido[] =[];
    productosFiltrados!: Observable<Producto[]>;


    constructor(private clienteService: ClienteService,
      private pedidoService: PedidoService,
      private productoService: ProductoService,
      private alertService: AlertService,
      private genericosDeProductoService: GenericosDeProductoService,
      private router: Router,
      private activatedRoute: ActivatedRoute) { }

    ngOnInit() {
      this.activatedRoute.paramMap.subscribe(params => {
        let clienteId = +params.get('clienteId')! ;
        this.clienteService.getCliente(clienteId).subscribe(cliente => this.cliente = cliente);
      });

      this.productosFiltrados = this.autocompleteControl.valueChanges
        .pipe(
          map(value => typeof value === 'string' ? value : value.nombre),
          flatMap(value => value ? this._filter(value) : [])
        );
      this.pedidoService.getAllTipoPedido().subscribe(result => {
        this.tipoPedidos = result
         this.tipoPedidos.forEach(r =>
          r.activo = r.id ==1?true:false
        )
        this.tipoPedidoVentaClientes = this.tipoPedidos[0];
      });
       // this.genericosDeProductoService.getGenericos().subscribe(result =>{
          //this.genericosDeProducto = result
          //this.colores =  result.filter(p => p.id>=10 && p.id<30);
          //this.materiales = result.filter(p => p.id>=30 && p.id<50);
          //this.origenes = result.filter(p => p.id>=50 && p.id<70);
          //this.empaques = result.filter(p => p.id>=70 && p.id<90);
          //this.categorias = result.filter(p => p.id>=90 && p.id<100);
          //this.usos = result.filter(p => p.id>=100 && p.id<120);
        //});
    }

    private _filter(value: string): Observable<Producto[]> {
      const filterValue = value.toLowerCase();

      return this.productoService.filtrarProductos(filterValue);
    }

    mostrarNombre(producto: Producto): string  {
/*       return producto ? (producto.nombre + producto.color?.nombre + producto.material?.nombre) : '';
 */
      return producto ? producto.nombre  : '';

    }

    seleccionarProducto(event: MatAutocompleteSelectedEvent): void {
      let producto = event.option.value as Producto;

      if (this.existeItem(producto.id)) {
        this.incrementaCantidad(producto.id);
      } else {
        let nuevoItem = new ItemPedido();
        nuevoItem.producto = producto;
        this.pedido.items.push(nuevoItem);
      }

      this.autocompleteControl.setValue('');
      //this.calcularGranTotal();
      event.option.focus();
      event.option.deselect();

    }

    actualizarCantidad(id: number, event: any): void {
      let cantidad: number = event.target.value as number;
      //debugger;
      if (cantidad == 0) {
        return this.eliminarItemPedido(id);
      }

      this.pedido.items = this.pedido.items.map((item: ItemPedido) => {
        if (id === item.producto.id) {
          item.cantidad = cantidad;
        }
        return item;
      });

      //this.calcularGranTotal();
    }

    calcularGranTotal(id: number, event: any): void {
      //debugger;
      let importe: number = event.target.value as number;

      this.pedido.items = this.pedido.items.map((item: ItemPedido) => {
        if (id === item.producto.id) {
          item.importe = importe;
        }
        return item;
      });

     // let grandTotal = 0;
      this.pedido.precioNetoTotal = 0;
      this.pedido.costoTotal = 0;
      this.pedido.items.forEach((item: ItemPedido) => {
        if(this.tipoPedidoVentaClientes.activo){
              //this.pedido.precioNetoTotal += item.calcularImporteVentaCliente();
              this.pedido.precioNetoTotal +=parseFloat(item.importe.toString());
            } else {
              this.pedido.costoTotal += parseFloat(item.importe.toString());
            }
        });
     // return grandTotal;
    }

    existeItem(id: number): boolean {
      let existe = false;
      this.pedido.items.forEach((item: ItemPedido) => {
        if (id === item.producto.id) {
          existe = true;
        }
      });
      return existe;
    }

    incrementaCantidad(id: number): void {
      this.pedido.items = this.pedido.items.map((item: ItemPedido) => {
        if (id === item.producto.id) {
          ++item.cantidad;
        }
        return item;
      });
    }

    eliminarItemPedido(id: number): void {
      console.log("Eliminar producot ...." + id);
      this.pedido.items = this.pedido.items.filter((item: ItemPedido) => id !== item.producto.id);
    }

    create(pedidoForm: any): void {
      if (this.pedido.items.length == 0) {
        this.autocompleteControl.setErrors({ 'invalid': true });
      }

      if (pedidoForm.form.valid && this.pedido.items.length > 0) {
        this.cliente.pedidos=[];
        this.pedido.cliente = {...this.cliente}
        this.pedido.tipoPedido = this.tipoPedidoVentaClientes
        console.log(this.pedido);

        this.pedidoService.create(this.pedido).subscribe(json => {
          //swal.fire(this.titulo, `Pedido para ${pedido.cliente?.apellidos}, ${pedido.cliente?.nombres} creado con éxito!`, 'success');
          this.alertService.success(`Pedido para ${json.pedido.cliente?.apellidos}, ${json.pedido.cliente?.nombres} creado con éxito!`,'success')
          this.router.navigate(['/pr/clientes']);
        });
      }
    }

}

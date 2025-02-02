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

@Component({
  selector: 'app-form-pedido',
  templateUrl: './form-pedido.component.html',
  styleUrl: './form-pedido.component.css'
})
export class FormPedidoComponent implements OnInit {

  titulo: string = 'Nuevo Pedido';
  //  displayedColumns: string[] = ['producto', 'precio', 'cantidad', 'importe', 'descripcion', 'eliminar'];
    pedido: Pedido = new Pedido();
    cliente!: Cliente;
    autocompleteControl = new FormControl();

    productosFiltrados!: Observable<Producto[]>;

    constructor(private clienteService: ClienteService,
      private pedidoService: PedidoService,
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
    }

    private _filter(value: string): Observable<Producto[]> {
      const filterValue = value.toLowerCase();

      return this.pedidoService.filtrarProductos(filterValue);
    }

    mostrarNombre(producto: Producto): string  {
      return producto ? producto.nombre : '';
    }

    seleccionarProducto(event: MatAutocompleteSelectedEvent): void {
      let producto = event.option.value as Producto;
      console.log(producto);

      if (this.existeItem(producto.id)) {
        this.incrementaCantidad(producto.id);
      } else {
        let nuevoItem = new ItemPedido();
        nuevoItem.producto = producto;
        this.pedido.items.push(nuevoItem);
      }

      this.autocompleteControl.setValue('');
      this.calcularGranTotal();
      event.option.focus();
      event.option.deselect();

    }

    actualizarCantidad(id: number, event: any): void {
      let cantidad: number = event.target.value as number;

      if (cantidad == 0) {
        return this.eliminarItemPedido(id);
      }

      this.pedido.items = this.pedido.items.map((item: ItemPedido) => {
        if (id === item.producto.id) {
          item.cantidad = cantidad;
        }
        return item;
      });

      this.calcularGranTotal();
    }

    calcularGranTotal(): number {
      this.pedido.precioNetoTotal = 0;
      this.pedido.items.forEach((item: ItemPedido) => {
        this.pedido.precioNetoTotal += item.calcularImporte();
      });
      return this.pedido.precioNetoTotal;
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
        console.log(this.pedido);

        this.pedidoService.create(this.pedido).subscribe(pedido => {
          swal.fire(this.titulo, `Pedido para ${pedido.cliente?.apellidos}, ${pedido.cliente?.nombres} creado con éxito!`, 'success');
          this.router.navigate(['/pr/clientes']);
        });
      }
    }

}

import { Component, OnInit } from '@angular/core';
import { Cliente } from '../../../models/cliente';
import { Observable, of } from 'rxjs';
import { ClienteService } from '../../../services/cliente.service';
import { FormControl } from '@angular/forms';
import { flatMap, map, mergeMap, switchMap } from 'rxjs/operators';
import { MatAutocompleteSelectedEvent } from '@angular/material/autocomplete';

@Component({
  selector: 'autocomplete-cliente',
  templateUrl: './autocomplete-cliente.component.html',
  styleUrl: './autocomplete-cliente.component.css'
})
export class AutocompleteClienteComponent implements OnInit {

  autocompleteControl = new FormControl();
  filteredOptions!: Observable<Cliente[]>;


  constructor(private clienteService: ClienteService) {

  }

  ngOnInit() {
    this.filteredOptions = this.autocompleteControl.valueChanges
      .pipe(
        map(value => typeof value === 'string' ? value : value.apellNomRz),
        switchMap(value => value? this._filter(value):[])
      );
  }

  private _filter(value: string): Observable<Cliente[]> {
    const filterValue = value.toLowerCase();
    return this.clienteService.filtrarClientes(filterValue);
  }

  mostrarApellidoCliente(cliente: Cliente): string {
    return cliente && cliente.nomApellRz ? cliente.nomApellRz : '';
  }

   seleccionarProducto(event: MatAutocompleteSelectedEvent): void {
    let cliente = event.option.value as Cliente;
    console.log(cliente);
/*      if (this.existeItem(producto.id)) {
      this.incrementaCantidad(producto.id);
    } else {
      let nuevoItem = new ItemPedido();
      nuevoItem.producto = producto;
      this.pedido.items.push(nuevoItem);
    }

    this.autocompleteControl.setValue('');
    event.option.focus();
    event.option.deselect(); */

  }

}

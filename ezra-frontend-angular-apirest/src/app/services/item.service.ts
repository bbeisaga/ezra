import { Injectable } from '@angular/core';
import { ItemPedido } from '../models/item-pedido';
import { BehaviorSubject } from 'rxjs';
import { findIndex } from 'lodash';

@Injectable({
  providedIn: 'root'
})
export class ItemService {

  itemsBehaviorSubject = new BehaviorSubject<ItemPedido[]>([]);

  constructor() { }

  setItems(items: ItemPedido[]) {
    this.itemsBehaviorSubject.next(items);
  }

  getItems() {
    return this.itemsBehaviorSubject.asObservable();
  }

  existItemInItems(items: Array<ItemPedido>, productoId: number): boolean {
    let existe = false;
    items.forEach((item: ItemPedido) => {
      if (productoId === item.producto.id) {
        existe = true;
      }
    });
    return existe;
  }

  //adiciona cantidad a un item especifico de un array de items
  addItemtoItems(items: Array<ItemPedido>, productoId: number, cantidad: number): Array<ItemPedido> {
    return items = items.map((item: ItemPedido) => {
      //if (productoId === item.producto.id) {
      // ++item.cantidad;
      item.cantidad = cantidad;
      item.importe = cantidad * item.producto.precioNeto;
      //}
      return item;
    });

    //     this.items = [...this.items, { ...item }];

  }

  UpdateAmountItemFromItems(items: Array<ItemPedido>, productoId: number, cantidad: number): Array<ItemPedido> {
    if (cantidad == 0) {
      return this.deleteItemFromItems(items, productoId);
    }
    items = items.map((item: ItemPedido) => {
      if (productoId === item.producto.id) {
        item.cantidad = cantidad;
        // parseFloat(item.cantidad.toString()) < 0 ? item.cantidad = 0 : item.cantidad;
        item.importe = item.cantidad * item.producto.precioNeto;
      }
      return item;
    });

    return items
  }

  UpdateAmountItemFromExterno(items: Array<ItemPedido>, productoId: number, cantidad: number): Array<ItemPedido> {
    items = items.map((item: ItemPedido) => {
      if (productoId === item.producto.id) {
        item.cantidad += cantidad;
        item.importe = item.cantidad * item.producto.precioNeto;
      }
      return item;
    });

    return items
  }

  /*   UpdateAmountItem(item: ItemPedido, productoId: number, cantidad: number): Array<ItemPedido> {
      if (cantidad == 0) {
        return this.delteItemFromItems(items, productoId);
      }
      items = items.map((item: ItemPedido) => {
        if (productoId === item.producto.id) {
          item.cantidad = cantidad;
          item.importe = cantidad * item.producto.precioNeto;
        }
        return item;
      });
  
      return items
    } */

  deleteItemFromItems(items: Array<ItemPedido>, productoId: number): Array<ItemPedido> {
    const indice = findIndex(items, (item: ItemPedido) => item.producto.id === productoId);
    //return items = items.filter((item: ItemPedido) => productoId !== item.producto.id);
    return items.splice(indice, 1) ? items : [];
  }

  //cacula el total del un array de tipo items
  calculateTotalFromItems(items: Array<ItemPedido>): number {
    let total = 0
    return total = items.reduce((n, item) => n + item.importe, 0);
  }

  saveSessionStorageItems(items: Array<ItemPedido>) {
    sessionStorage.setItem('items', JSON.stringify(items));
  }

  getSessionStorageItems(): Array<ItemPedido> {
    const items = sessionStorage.getItem('items');
    return items ? JSON.parse(items) : [];
  }

  saveLocalStorageItems(items: Array<ItemPedido>) {
    localStorage.setItem('items', JSON.stringify(items));
  }

  getLocalStorageItems(): Array<ItemPedido> {
    const items = localStorage.getItem('items');
    return items ? JSON.parse(items) : [];
  }

}

import { NgModule } from '@angular/core';
// aplicaicon modulos
import { CommonModule, NgOptimizedImage } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

//angular material modulos
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatCardModule } from '@angular/material/card';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatTableModule } from '@angular/material/table';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatInputModule } from '@angular/material/input';
import { MatSortModule } from '@angular/material/sort';
import { MatNativeDateModule } from '@angular/material/core';
import { MatMenuModule } from '@angular/material/menu';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatDialogModule } from '@angular/material/dialog';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatRadioModule } from '@angular/material/radio';
import { MatExpansionModule } from '@angular/material/expansion';


import { SearchBoxTableComponent } from './search-box-table/search-box-table.component';
import { ModalDialogAlertComponent } from './modal-dialog/modal-dialog-alert.component';
import { AutocompleteClienteComponent } from './autocomplete-box/autocomplete-cliente.component';
import { MenuTiendaComponent } from './menus-nav/menu-tienda.component';
import { CabeceraComponent } from './cabecera/cabecera.component';
import { MenuAppComponent } from './menus-nav/menu-app.component';
import { PrincipalComponent } from './principal.component';
import { MenuCabeceraComponent } from './menus-nav/menu-cabecera.component';
import { PieComponent } from './pie/pie.component';
import { ContactanosComponent } from './contactanos/contactanos.component';



@NgModule({
  declarations: [
    SearchBoxTableComponent,
    ModalDialogAlertComponent,
    MenuAppComponent,
    MenuTiendaComponent,
    PrincipalComponent,
    AutocompleteClienteComponent,
    CabeceraComponent,
    MenuCabeceraComponent,
    PieComponent,
    ContactanosComponent

  ],
  imports: [
    CommonModule,
    FormsModule,
    RouterModule,
    ReactiveFormsModule,
    MatSidenavModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatButtonModule,
    MatToolbarModule,
    MatIconModule,
    MatListModule,
    MatPaginatorModule,
    MatCardModule,
    MatDatepickerModule,
    MatTableModule,
    MatTooltipModule,
    MatSortModule,
    MatNativeDateModule,
    MatMenuModule,
    MatAutocompleteModule,
    MatDialogModule,
    MatCheckboxModule,
    MatRadioModule,
    MatExpansionModule,
    NgOptimizedImage
  ],
  exports: [
    FormsModule,
    ReactiveFormsModule,
    MatSidenavModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatButtonModule,
    MatToolbarModule,
    MatIconModule,
    MatListModule,
    MatPaginatorModule,
    MatCardModule,
    MatDatepickerModule,
    MatTableModule,
    MatTooltipModule,
    MatSortModule,
    MatNativeDateModule,
    MatMenuModule,
    MatAutocompleteModule,
    MatDialogModule,
    MatCheckboxModule,
    MatRadioModule,
    MatExpansionModule,
    NgOptimizedImage,
    SearchBoxTableComponent,
    AutocompleteClienteComponent,
    MenuTiendaComponent,
    CabeceraComponent,
    PrincipalComponent,
    PieComponent,
    ContactanosComponent
  ]
})
export class CompartidoModule { }

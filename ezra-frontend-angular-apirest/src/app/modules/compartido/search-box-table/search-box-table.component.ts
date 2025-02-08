import { Component, EventEmitter, OnInit, Output } from '@angular/core';

@Component({
  selector: 'search-box-table',
  templateUrl: './search-box-table.component.html',
  styleUrl: './search-box-table.component.css'
})
export class SearchBoxTableComponent {
  query!: string;
  @Output() searchEvent = new EventEmitter();

  //theForm: FormGroup;

  constructor(
  ) { }

  search(): void {
    this.searchEvent.emit(this.query);
  }
}

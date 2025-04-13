import { AfterViewInit, Component, OnInit } from '@angular/core';

import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { DatePipe } from '@angular/common';
import swal from 'sweetalert2';
import { Router } from '@angular/router';
import { CajaUsuario } from '../../../../models/caja-usuario';
import { Usuario } from '../../../../models/usuario';
import { Caja } from '../../../../models/caja';
import { AuthService } from '../../../../services/auth.service';
import { CajaService } from '../../../../services/caja.service';
import moment from 'moment';
import { UsuarioService } from '../../../../services/usuario.service';
import { AlertService } from '../../../../services/alert.service';


@Component({
  selector: 'app-apertura-cierre-caja',
  templateUrl: './apertura-cierre-caja.component.html',
  styleUrls: ['./apertura-cierre-caja.component.css'],

})
export class AperturaCierreCajaComponent implements OnInit ,AfterViewInit {

  formCaja! : FormGroup;
  cajaUsuario = new CajaUsuario();
  username!:string;
  //user!: Usuario;
  //caja: Caja = new Caja();
  cajas: Caja[]=[];
  caja!:Caja;
  //usuario!: Usuario;
  isAutenticado!: boolean;
  cajaActiva:boolean=false;
  fechaActual!: string;

  constructor(private fb:FormBuilder,
              private authService: AuthService,
              private cajaService: CajaService,
              private usuarioService: UsuarioService,
              private alertService: AlertService,
              private router: Router){

  }

  ngOnInit(): void {
    this.createForm();
    if(this.authService.isAuthenticated()){
      this.username = this.authService.usuario.username;
      //debugger;
      //console.log(this.authService.usuario.username);
      this.usuarioService.getUsuarioByUsername(this.username).subscribe(resp => this.cajaUsuario.usuario = resp)
    }
  }

  ngAfterViewInit(): void {
    this.fechaActual = moment().format('DD-MM-YYYY hh:mm:ss');
    //this.datePipe.transform(new Date(), 'dd-MM-yyyy')!;
    this.obtenerTodoCajas();
        //this.setValuesControls(null);
    //    debugger;
    console.log(this.username);
    this.cajaService.getCajaUsuarioByUserName(this.username).subscribe(
          result => {
              this.setValuesControls(result);
          }
     )
    console.log("AperturaCajaComponent.ngOnInit...", this.cajaUsuario);

  }

  setValuesControls(cajaUsuario: CajaUsuario | null){
    //this.formCaja.get('usuarioId')?.setValue('');
    //this.formCaja.get('usuario')?.setValue('');
    this.formCaja.get('fchApertura')?.setValue('');
    this.formCaja.get('fchCierre')?.setValue('');
    this.formCaja.get('cajaAsignada')?.setValue('');
    this.formCaja.get('saldoCaja')?.setValue(0);

    if(cajaUsuario!=null){
      this.cajaUsuario = cajaUsuario
      this.cajaUsuario.fechaApertura = moment(this.cajaUsuario.fechaApertura).format('DD/MM/YYYY HH:mm:ss');
      //this.datePipe.transform(this.cajaUsuario.fechaApertura, 'dd-MM-yyyy')!;
      if(this.cajaUsuario.fechaCierre!=null){
      this.cajaUsuario.fechaCierre = moment(this.cajaUsuario.fechaCierre).format('DD/MM/YYYY HH:mm:ss')};
      //this.datePipe.transform(this.cajaUsuario.fechaCierre, 'dd-MM-yyyy')!;
      this.cajaActiva = this.cajaUsuario.activa;
      this.formCaja.get('fchApertura')?.setValue(this.cajaUsuario.fechaApertura);
      this.formCaja.get('fchCierre')?.setValue(this.cajaUsuario.fechaCierre);
      this.formCaja.get('cajaAsignada')?.setValue(this.cajaUsuario.caja.nombre );
      this.formCaja.get('saldoCaja')?.setValue(this.cajaUsuario.saldoCaja);
      //this.formCaja.get('usuarioId')?.setValue(this.cajaUsuario.usuario.username);
      //this.formCaja.get('usuario')?.setValue(this.cajaUsuario.usuario.apellido +' ' + this.cajaUsuario.usuario.nombre);
    }
  }

  asignarCajaUsuario(caja :Caja){
    //this.cajaUsuario = new CajaUsuario();
    //console.log(this.username);
    this.cajaUsuario.caja = caja
    //this.cajaUsuario.usuario.username = this.username
    this.formCaja.get("ubicacionCaja")?.setValue(caja.ubicacion);
    this.cajaService.getCajaUsuarioByCajaIdAndUsername(caja.id,this.username ).subscribe(
      result => {
          this.setValuesControls(result);
      }
    )
    console.log("AperturaCajaComponent.asignarCajaUsuario...", this.cajaUsuario);
  }

  obtenerTodoCajas(){
    this.cajaService.getAllCaja().subscribe(
      result => this.cajas = result
    )
  }

  abrirCaja(){
    console.log("AperturaCajaComponent.abrirCaja...", this.cajaUsuario);
    this.cajaUsuario.fechaApertura = '';
    this.cajaUsuario.fechaCierre = '';
    this.cajaUsuario.usuario.roles = [];
     this.cajaService.create(this.cajaUsuario).subscribe(
      response => {
        this.alertService.success(`Se habrió ${this.cajaUsuario.caja.nombre}, para ${this.cajaUsuario.usuario.username} con éxito!`,"Caja", )
       // swal.fire("Caja", `Se habrió ${this.cajaUsuario.caja.nombre}, para ${this.cajaUsuario.usuario.username} con éxito!`, 'success');
        this.router.navigate(['/']);
      } )
  }

  cerrarCaja(){
    const saldoPorConteo = this.formCaja.get('saldoPorConteo')!.value;
    const saldoCaja = this.formCaja.get('saldoCaja')!.value;
    this.cajaUsuario.fechaApertura = '';
    this.cajaUsuario.fechaCierre = '';
    if (saldoPorConteo == saldoCaja){
      this.cajaUsuario.saldoPorConteo = saldoPorConteo;
      this.cajaUsuario.activa=false;
      this.cajaService.update(this.cajaUsuario).subscribe(
        response => {
          this.alertService.success( `Se cerró ${this.cajaUsuario.caja.nombre}, para ${this.cajaUsuario.usuario.username} con éxito!`, "Caja" )
       //   swal.fire("Caja", `Se cerró ${this.cajaUsuario.caja.nombre}, para ${this.cajaUsuario.usuario.username} con éxito!`, 'success');
          this.router.navigate(['/']);
        }
      )
    } else {
      this.alertService.warning(`No puede cerrar ${this.cajaUsuario.caja.nombre}, existe diferencias`,"Caja" )
      //swal.fire("Caja", `No puede cerrar ${this.cajaUsuario.caja.nombre}, existe diferencias`, 'warning');
    }
  }

  createForm():void {
   // console.log("cajaUsuariocreateForm", this.cajaUsuario);
    this.formCaja = this.fb.group({
      //usuario: [this.cajaUsuario?.usuario?.username],
      //usuario:   [this.cajaUsuario?.usuario?.apellido +'-'+ this.cajaUsuario?.usuario?.nombre],
      cajaAsignada:[this.cajaUsuario?.caja?.nombre],
      ubicacionCaja:[this.cajaUsuario?.caja?.ubicacion,{
        validators:[Validators.required]
        }],

      saldoCaja: [this.cajaUsuario?.saldoCaja,{
        validators:[Validators.required]
        }],
      saldoPorConteo: [this.cajaUsuario?.saldoPorConteo,{
          validators:[Validators.required]
          }],

      fchApertura: [this.cajaUsuario?.fechaApertura] ,
      fchCierre: [this.cajaUsuario?.fechaCierre] ,
      cajaPorAsignar:[this.cajaUsuario?.caja?.id,{
          validators:[Validators.required]
          }],
    })
  }

}

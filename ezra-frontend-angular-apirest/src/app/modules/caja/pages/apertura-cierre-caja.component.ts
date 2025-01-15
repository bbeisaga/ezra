import { Component, OnInit } from '@angular/core';
import { Caja } from '../../../models/caja';
import { CajaService } from '../../../services/caja.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Usuario } from '../../../models/usuario';
import { AuthService } from '../../../services/auth.service';
import { CajaUsuario } from '../../../models/caja-usuario';
import { DatePipe } from '@angular/common';
import swal from 'sweetalert2';
import { Router } from '@angular/router';

@Component({
  selector: 'app-apertura-cierre-caja',
  templateUrl: './apertura-cierre-caja.component.html',
  styleUrls: ['./apertura-cierre-caja.component.css'],

})
export class AperturaCierreCajaComponent implements OnInit {

  formCaja! : FormGroup;
  cajaUsuario = new CajaUsuario();
  user!: Usuario;
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
              private datePipe: DatePipe,
              private router: Router){
  }

  ngOnInit(): void {
    this.createForm();
    this.fechaActual = this.datePipe.transform(new Date(), 'dd-MM-yyyy')!;
    this.obtenerTodoCajas();
    this.isAutenticado = this.authService.isAuthenticated();
    if(this.isAutenticado){
      this.user = this.authService.usuario;
    }
    this.cajaService.getCajaUsuarioByUserName(this.user).subscribe(
      result => {

        //this.cajaUsuario.usuario = this.user
        //if(result!=null){
          this.setValuesControls(result);
        //}

        //this.formCaja.get('usuarioId')?.setValue(this.cajaUsuario.usuario.username);
        //this.formCaja.get('usuario')?.setValue(this.cajaUsuario.usuario.apellido +' ' + this.cajaUsuario.usuario.nombre);
      }
    )
    console.log("AperturaCajaComponent.ngOnInit...", this.cajaUsuario);
  }

  setValuesControls(cajaUsuario: CajaUsuario){
    this.formCaja.get('usuarioId')?.setValue('');
    this.formCaja.get('usuario')?.setValue('');
    this.formCaja.get('fchApertura')?.setValue('');
    this.formCaja.get('fchCierre')?.setValue('');
    this.formCaja.get('cajaAsignada')?.setValue('');
    this.formCaja.get('saldoCaja')?.setValue(0);
    this.cajaUsuario.usuario = this.user
    if(cajaUsuario!=null){
      this.cajaUsuario = cajaUsuario
      this.cajaUsuario.fechaApertura = this.datePipe.transform(this.cajaUsuario.fechaApertura, 'dd-MM-yyyy')!;
      this.cajaUsuario.fechaCierre = this.datePipe.transform(this.cajaUsuario.fechaCierre, 'dd-MM-yyyy')!;
      this.cajaActiva = this.cajaUsuario.activa;
      this.formCaja.get('fchApertura')?.setValue(this.cajaUsuario.fechaApertura);
      this.formCaja.get('fchCierre')?.setValue(this.cajaUsuario.fechaCierre);
      this.formCaja.get('cajaAsignada')?.setValue(this.cajaUsuario.caja.nombre );
      this.formCaja.get('saldoCaja')?.setValue(this.cajaUsuario.saldoCaja);
    }
    this.formCaja.get('usuarioId')?.setValue(this.cajaUsuario.usuario.username);
    this.formCaja.get('usuario')?.setValue(this.cajaUsuario.usuario.apellido +' ' + this.cajaUsuario.usuario.nombre);
  }

  asignarCajaUsuario(caja :Caja){
    this.cajaUsuario = new CajaUsuario();
    this.cajaUsuario.caja = caja
    this.formCaja.get("ubicacionCaja")?.setValue(this.cajaUsuario.caja.ubicacion);
    this.cajaService.getCajaUsuarioByCajaIdAndUserId(caja.id,this.user.id ).subscribe(
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
    //this.cajaUsuario.caja = this.formCaja.get('cajaPorAsignar')?.value
    console.log("AperturaCajaComponent.abrirCaja...", this.cajaUsuario);
    this.cajaUsuario.usuario.roles = [];
    this.cajaUsuario.fechaApertura = '';
    this.cajaUsuario.fechaCierre = '';
     this.cajaService.create(this.cajaUsuario).subscribe(
      response => {
        swal.fire("Caja", `Se habrió ${this.cajaUsuario.caja.nombre}, para ${this.cajaUsuario.usuario.username} con éxito!`, 'success');
        this.router.navigate(['/pr']);
      } )
  }

  cerrarCaja(){
    const saldoPorConteo = this.formCaja.get('saldoPorConteo')!.value;
    const saldoCaja = this.formCaja.get('saldoCaja')!.value;
    this.cajaUsuario.fechaApertura = '';
    this.cajaUsuario.fechaCierre = '';
    if (saldoPorConteo == saldoCaja){
      this.cajaUsuario.saldoPorConteo = saldoPorConteo;
      this.cajaService.update(this.cajaUsuario).subscribe(
        response => {
          swal.fire("Caja", `Se cerró ${this.cajaUsuario.caja.nombre}, para ${this.cajaUsuario.usuario.username} con éxito!`, 'success');
          this.router.navigate(['/pr']);
        }
      )
    } else {
      swal.fire("Caja", `No puede cerrar ${this.cajaUsuario.caja.nombre}, existe diferencias`, 'warning');
    }
  }

  createForm():void {
   // console.log("cajaUsuariocreateForm", this.cajaUsuario);
    this.formCaja = this.fb.group({
      usuarioId: [this.cajaUsuario?.usuario?.username,{
        validators:[Validators.required]
        }],
      usuario: [this.cajaUsuario?.usuario?.apellido +'-'+ this.cajaUsuario?.usuario?.nombre ,{
        validators:[Validators.required]
        }],

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

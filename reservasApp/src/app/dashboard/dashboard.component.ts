import { Component, OnInit } from '@angular/core';
import { UsuarioService } from '../usuarios/usuario.service';
import { HabitacionService } from '../habitaciones/habitacion.service';
import { HuespedService } from '../huespedes/huesped.service';
import { AuthService } from '../core/services/auth.service';
import { ROLES } from '../core/models/usuario.model';

@Component({
  selector: 'app-dashboard',
  standalone: false,
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent implements OnInit {

  totalUsuarios = 0;
  totalHabitaciones = 0;
  totalHuespedes = 0;
  cargando = true;
  isAdmin = false;

  constructor(
    private usuarioService: UsuarioService,
    private habitacionService: HabitacionService,
    private huespedService: HuespedService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.isAdmin = this.authService.hasRole(ROLES[0]);
    this.listarHabitaciones();
    this.listarHuespedes();
    if(this.isAdmin) {
      this.listarUsuarios();
    }
  }

  listarUsuarios(): void {
    this.usuarioService.listar().subscribe({
      next: (data) => {
        this.totalUsuarios = data.length;
        this.cargando = false;
      },
      error: () => {
        this.cargando = false;
      }
    });
  }

  listarHabitaciones(): void {
    this.habitacionService.listar().subscribe({
      next: (data) => {
        this.totalHabitaciones = data.length;
      },
      error: () => {
        // Manejo de error
      }
    });
  }

  listarHuespedes(): void {
    this.huespedService.listar().subscribe({
      next: (data) => {
        this.totalHuespedes = data.length;
      },
      error: () => {
        // Manejo de error
      }
    });
  }
}
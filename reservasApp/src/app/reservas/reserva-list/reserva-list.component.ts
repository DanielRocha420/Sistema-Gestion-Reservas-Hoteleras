import { Component, OnInit } from '@angular/core';
import { ReservaService } from '../reserva.service';
import { ReservaResponse, EstadoReserva } from '../../core/models/reserva.model';
import { ESTADO_RESERVA_LABELS } from '../../core/models/enums/estado-reserva.enum';
import { MatDialog } from '@angular/material/dialog';
import { ReservaFormComponent } from '../reserva-form/reserva-form.component';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-reserva-list',
  templateUrl: './reserva-list.component.html',
  styleUrls: ['./reserva-list.component.scss']
})
export class ReservaListComponent implements OnInit {
  reservas: ReservaResponse[] = [];
  displayedColumns: string[] = ['id', 'huesped', 'habitacion', 'estado', 'fechaEntrada', 'fechaSalida', 'acciones'];
  estadoLabels = ESTADO_RESERVA_LABELS;

  constructor(
    private reservaService: ReservaService,
    private dialog: MatDialog,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.cargarReservas();
  }

  cargarReservas(): void {
    this.reservaService.listar().subscribe({
      next: (data) => {
        this.reservas = data.map(r => ({
          ...r,
          estadoReserva: this.reservaService.obtenerEstadoPorCodigo(r.estadoReserva as any)
        }));
      },
      error: (error) => {
        this.snackBar.open('Error al cargar reservas', 'Cerrar', { duration: 3000 });
        console.error(error);
      }
    });
  }

  crearReserva(): void {
    const dialogRef = this.dialog.open(ReservaFormComponent, {
      width: '500px',
      data: { mode: 'create' }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.cargarReservas();
        this.snackBar.open('Reserva creada exitosamente', 'Cerrar', { duration: 3000 });
      }
    });
  }

  editarReserva(reserva: ReservaResponse): void {
    const dialogRef = this.dialog.open(ReservaFormComponent, {
      width: '500px',
      data: { mode: 'edit', reserva }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.cargarReservas();
        this.snackBar.open('Reserva actualizada exitosamente', 'Cerrar', { duration: 3000 });
      }
    });
  }

  eliminarReserva(id: number): void {
    if (confirm('¿Está seguro de eliminar esta reserva?')) {
      this.reservaService.eliminar(id).subscribe({
        next: () => {
          this.cargarReservas();
          this.snackBar.open('Reserva eliminada exitosamente', 'Cerrar', { duration: 3000 });
        },
        error: (error) => {
          this.snackBar.open('Error al eliminar reserva', 'Cerrar', { duration: 3000 });
          console.error(error);
        }
      });
    }
  }

  checkIn(reserva: ReservaResponse): void {
    if (!this.reservaService.puedeRealizarCheckIn(reserva.estadoReserva)) {
      this.snackBar.open('Solo se puede hacer check-in en reservas confirmadas', 'Cerrar', { duration: 3000 });
      return;
    }

    this.reservaService.checkIn(reserva.id, reserva.numHabitacion).subscribe({
      next: () => {
        this.cargarReservas();
        this.snackBar.open('Check-in realizado exitosamente', 'Cerrar', { duration: 3000 });
      },
      error: (error) => {
        this.snackBar.open('Error al realizar check-in', 'Cerrar', { duration: 3000 });
        console.error(error);
      }
    });
  }

  checkOut(reserva: ReservaResponse): void {
    if (!this.reservaService.puedeRealizarCheckOut(reserva.estadoReserva)) {
      this.snackBar.open('Solo se puede hacer check-out en reservas en curso', 'Cerrar', { duration: 3000 });
      return;
    }

    this.reservaService.checkOut(reserva.id, reserva.numHabitacion).subscribe({
      next: () => {
        this.cargarReservas();
        this.snackBar.open('Check-out realizado exitosamente', 'Cerrar', { duration: 3000 });
      },
      error: (error) => {
        this.snackBar.open('Error al realizar check-out', 'Cerrar', { duration: 3000 });
        console.error(error);
      }
    });
  }

  cancelarReserva(reserva: ReservaResponse): void {
    if (!this.reservaService.puedeCancelar(reserva.estadoReserva)) {
      this.snackBar.open('Solo se puede cancelar reservas confirmadas', 'Cerrar', { duration: 3000 });
      return;
    }

    if (confirm('¿Está seguro de cancelar esta reserva?')) {
      this.reservaService.cancelar(reserva.id, reserva.numHabitacion).subscribe({
        next: () => {
          this.cargarReservas();
          this.snackBar.open('Reserva cancelada exitosamente', 'Cerrar', { duration: 3000 });
        },
        error: (error) => {
          this.snackBar.open('Error al cancelar reserva', 'Cerrar', { duration: 3000 });
          console.error(error);
        }
      });
    }
  }

  obtenerLabelEstado(estado: EstadoReserva): string {
    return this.estadoLabels[estado] || estado;
  }
}

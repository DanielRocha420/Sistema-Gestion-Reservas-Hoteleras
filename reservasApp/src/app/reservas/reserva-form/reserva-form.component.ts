import { Component, Inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ReservaService } from '../reserva.service';
import { ReservaRequest, ReservaResponse } from '../../core/models/reserva.model';
import { EstadoReserva } from '../../core/models/enums/estado-reserva.enum';
import { HuespedService } from '../../huespedes/huesped.service';
import { HabitacionService } from '../../habitaciones/habitacion.service';
import { HuespedResponse } from '../../core/models/huesped.model';
import { HabitacionResponse } from '../../core/models/habitacion.model';

interface DialogData {
  mode: 'create' | 'edit';
  reserva?: ReservaResponse;
}

@Component({
  selector: 'app-reserva-form',
  templateUrl: './reserva-form.component.html',
  styleUrls: ['./reserva-form.component.scss']
})
export class ReservaFormComponent {
  form: FormGroup;
  cargando = false;
  huespedes: HuespedResponse[] = [];
  habitaciones: HabitacionResponse[] = [];
  esEdicion = false;

  constructor(
    private fb: FormBuilder,
    private dialogRef: MatDialogRef<ReservaFormComponent>,
    @Inject(MAT_DIALOG_DATA) private data: DialogData,
    private reservaService: ReservaService,
    private huespedService: HuespedService,
    private habitacionService: HabitacionService,
    private snackBar: MatSnackBar
  ) {
    this.esEdicion = data.mode === 'edit';
    this.form = this.fb.group({
      idHuesped: ['', [Validators.required]],
      numHabitacion: ['', [Validators.required]],
      estadoReserva: [EstadoReserva.CONFIRMADA, [Validators.required]],
      fechaEntrada: ['', [Validators.required]],
      fechaSalida: ['', [Validators.required]]
    });

    if (this.esEdicion && data.reserva) {
      this.form.patchValue({
        idHuesped: data.reserva.idHuesped,
        numHabitacion: data.reserva.numHabitacion,
        estadoReserva: data.reserva.estadoReserva,
        fechaEntrada: data.reserva.fecha_Entrada,
        fechaSalida: data.reserva.fecha_Salida
      });
    }

    this.cargarHuespedes();
    this.cargarHabitaciones();
  }

  cargarHuespedes(): void {
    this.huespedService.listar().subscribe({
      next: (data) => {
        this.huespedes = data;
      },
      error: (error) => {
        this.snackBar.open('Error al cargar huéspedes', 'Cerrar', { duration: 3000 });
        console.error(error);
      }
    });
  }

  cargarHabitaciones(): void {
    this.habitacionService.listar().subscribe({
      next: (data) => {
        this.habitaciones = data;
      },
      error: (error) => {
        this.snackBar.open('Error al cargar habitaciones', 'Cerrar', { duration: 3000 });
        console.error(error);
      }
    });
  }

  guardar(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.cargando = true;
    const request: ReservaRequest = this.form.value;

    if (this.esEdicion && this.data.reserva) {
      this.reservaService.actualizar(request, this.data.reserva.id).subscribe({
        next: () => {
          this.cargando = false;
          this.snackBar.open('Reserva actualizada exitosamente', 'Cerrar', { duration: 3000 });
          this.dialogRef.close(true);
        },
        error: (error) => {
          this.cargando = false;
          this.snackBar.open('Error al actualizar reserva', 'Cerrar', { duration: 3000 });
          console.error(error);
        }
      });
    } else {
      this.reservaService.registrar(request).subscribe({
        next: () => {
          this.cargando = false;
          this.snackBar.open('Reserva creada exitosamente', 'Cerrar', { duration: 3000 });
          this.dialogRef.close(true);
        },
        error: (error) => {
          this.cargando = false;
          this.snackBar.open('Error al crear reserva', 'Cerrar', { duration: 3000 });
          console.error(error);
        }
      });
    }
  }

  cancelar(): void {
    this.dialogRef.close();
  }

  obtenerLabelEstado(estado: EstadoReserva): string {
    const labels: Record<EstadoReserva, string> = {
      [EstadoReserva.CONFIRMADA]: 'Confirmada',
      [EstadoReserva.EN_CURSO]: 'En Curso',
      [EstadoReserva.FINALIZADA]: 'Finalizada',
      [EstadoReserva.CANCELADA]: 'Cancelada'
    };
    return labels[estado] || estado;
  }
}

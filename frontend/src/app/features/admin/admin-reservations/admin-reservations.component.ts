import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatTableModule } from '@angular/material/table';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { ReservationService } from '../../../core/services/reservation.service';
import { ReservationResponse, ReservationStatus } from '../../../core/models/reservation.models';
import { LoadingSpinnerComponent } from '../../../shared/components/loading-spinner/loading-spinner.component';
import { EmptyStateComponent } from '../../../shared/components/empty-state/empty-state.component';

@Component({
  selector: 'app-admin-reservations',
  standalone: true,
  imports: [
    CommonModule, FormsModule, MatTableModule, MatCardModule, MatButtonModule,
    MatIconModule, MatFormFieldModule, MatSelectModule, MatSnackBarModule,
    LoadingSpinnerComponent, EmptyStateComponent,
  ],
  templateUrl: './admin-reservations.component.html',
  styleUrls: ['./admin-reservations.component.scss'],
})
export class AdminReservationsComponent implements OnInit {
  reservations: ReservationResponse[] = [];
  loading = true;
  selectedStatus: ReservationStatus = 'PENDING';
  statusOptions: ReservationStatus[] = ['PENDING', 'CONFIRMED', 'EXPIRED', 'CANCELLED'];
  displayedColumns = ['id', 'userName', 'eventName', 'status', 'totalAmount', 'reservationDate', 'expiresAt'];

  constructor(
    private reservationService: ReservationService,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void { this.load(); }

  load(): void {
    this.loading = true;
    this.reservationService.findReservationsByStatus(this.selectedStatus).subscribe({
      next: (r) => { this.reservations = r; this.loading = false; },
      error: () => { this.loading = false; },
    });
  }

  getStatusLabel(s: string): string {
    const m: Record<string, string> = {
      PENDING: 'Bekliyor', CONFIRMED: 'Onaylandı', EXPIRED: 'Süresi Doldu', CANCELLED: 'İptal',
    };
    return m[s] ?? s;
  }
}

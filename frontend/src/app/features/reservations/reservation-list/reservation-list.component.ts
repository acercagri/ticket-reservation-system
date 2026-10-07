import { Component, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { interval, Subscription } from 'rxjs';
import { ReservationService } from '../../../core/services/reservation.service';
import { AuthService } from '../../../core/services/auth.service';
import { ReservationResponse } from '../../../core/models/reservation.models';
import { LoadingSpinnerComponent } from '../../../shared/components/loading-spinner/loading-spinner.component';
import { EmptyStateComponent } from '../../../shared/components/empty-state/empty-state.component';
import { extractErrorMessage } from '../../../shared/utils/error-handler';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-reservation-list',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink,
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    MatSnackBarModule,
    LoadingSpinnerComponent,
    EmptyStateComponent,
  ],
  templateUrl: './reservation-list.component.html',
  styleUrls: ['./reservation-list.component.scss'],
})
export class ReservationListComponent implements OnInit, OnDestroy {
  reservations: ReservationResponse[] = [];
  loading = true;
  countdowns: Map<number, string> = new Map();
  private timerSub?: Subscription;

  constructor(
    private reservationService: ReservationService,
    private authService: AuthService,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.load();
    this.timerSub = interval(1000).subscribe(() => this.updateCountdowns());
  }

  ngOnDestroy(): void {
    this.timerSub?.unsubscribe();
  }

  load(): void {
    const userId = this.authService.getUserId();
    if (!userId) { this.loading = false; return; }

    this.reservationService.findReservationsByUser(userId).subscribe({
      next: (res) => {
        this.reservations = res.sort(
          (a, b) => new Date(b.createdAt).getTime() - new Date(a.createdAt).getTime()
        );
        this.loading = false;
        this.updateCountdowns();
      },
      error: () => { this.loading = false; },
    });
  }

  updateCountdowns(): void {
    this.reservations.forEach((r) => {
      if (r.status === 'PENDING') {
        const remaining = new Date(r.expiresAt).getTime() - Date.now();
        if (remaining <= 0) {
          this.countdowns.set(r.id, '00:00');
        } else {
          const minutes = Math.floor(remaining / 60000);
          const seconds = Math.floor((remaining % 60000) / 1000);
          this.countdowns.set(r.id, `${String(minutes).padStart(2, '0')}:${String(seconds).padStart(2, '0')}`);
        }
      }
    });
  }

  cancelReservation(id: number): void {
    this.reservationService.cancelReservation(id).subscribe({
      next: () => {
        this.snackBar.open('Rezervasyon iptal edildi.', 'Kapat', { duration: 3000 });
        this.load();
      },
      error: (err: HttpErrorResponse) => {
        this.snackBar.open(extractErrorMessage(err), 'Kapat', { duration: 4000, panelClass: 'snack-error' });
      },
    });
  }

  getStatusLabel(status: string): string {
    const map: Record<string, string> = {
      PENDING: 'Bekliyor', CONFIRMED: 'Onaylandı', EXPIRED: 'Süresi Doldu', CANCELLED: 'İptal',
    };
    return map[status] ?? status;
  }
}

import { Component, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatDividerModule } from '@angular/material/divider';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { interval, Subscription } from 'rxjs';
import { ReservationService } from '../../../core/services/reservation.service';
import { PaymentService } from '../../../core/services/payment.service';
import { ReservationResponse } from '../../../core/models/reservation.models';
import { PaymentResponse } from '../../../core/models/payment.models';
import { LoadingSpinnerComponent } from '../../../shared/components/loading-spinner/loading-spinner.component';
import { extractErrorMessage } from '../../../shared/utils/error-handler';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-reservation-detail',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink,
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    MatDividerModule,
    MatSnackBarModule,
    MatProgressSpinnerModule,
    LoadingSpinnerComponent,
  ],
  templateUrl: './reservation-detail.component.html',
  styleUrls: ['./reservation-detail.component.scss'],
})
export class ReservationDetailComponent implements OnInit, OnDestroy {
  reservation: ReservationResponse | null = null;
  loading = true;
  paymentLoading = false;
  countdown = '';
  private timerSub?: Subscription;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private reservationService: ReservationService,
    private paymentService: PaymentService,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.reservationService.findReservationById(id).subscribe({
      next: (res) => {
        this.reservation = res;
        this.loading = false;
        if (res.status === 'PENDING') {
          this.timerSub = interval(1000).subscribe(() => this.updateCountdown());
          this.updateCountdown();
        }
      },
      error: () => { this.loading = false; },
    });
  }

  ngOnDestroy(): void {
    this.timerSub?.unsubscribe();
  }

  updateCountdown(): void {
    if (!this.reservation) return;
    const remaining = new Date(this.reservation.expiresAt).getTime() - Date.now();
    if (remaining <= 0) {
      this.countdown = '00:00';
      this.timerSub?.unsubscribe();
      return;
    }
    const minutes = Math.floor(remaining / 60000);
    const seconds = Math.floor((remaining % 60000) / 1000);
    this.countdown = `${String(minutes).padStart(2, '0')}:${String(seconds).padStart(2, '0')}`;
  }

  pay(successful: boolean): void {
    if (!this.reservation) return;
    this.paymentLoading = true;
    this.paymentService.processPayment({ reservationId: this.reservation.id, successful }).subscribe({
      next: (payment: PaymentResponse) => {
        this.paymentLoading = false;
        if (payment.status === 'PAID') {
          this.snackBar.open('Ödeme başarılı! Biletleriniz hazır.', 'Kapat', { duration: 4000 });
          this.router.navigate(['/tickets']);
        } else {
          this.snackBar.open('Ödeme başarısız. Tekrar deneyebilirsiniz.', 'Kapat', {
            duration: 4000,
            panelClass: 'snack-error',
          });
          this.refreshReservation();
        }
      },
      error: (err: HttpErrorResponse) => {
        this.paymentLoading = false;
        this.snackBar.open(extractErrorMessage(err), 'Kapat', { duration: 5000, panelClass: 'snack-error' });
      },
    });
  }

  cancel(): void {
    if (!this.reservation) return;
    this.reservationService.cancelReservation(this.reservation.id).subscribe({
      next: () => {
        this.snackBar.open('Rezervasyon iptal edildi.', 'Kapat', { duration: 3000 });
        this.router.navigate(['/reservations']);
      },
      error: (err: HttpErrorResponse) => {
        this.snackBar.open(extractErrorMessage(err), 'Kapat', { duration: 4000, panelClass: 'snack-error' });
      },
    });
  }

  refreshReservation(): void {
    if (!this.reservation) return;
    this.reservationService.findReservationById(this.reservation.id).subscribe({
      next: (res) => { this.reservation = res; },
    });
  }

  getStatusLabel(status: string): string {
    const map: Record<string, string> = {
      PENDING: 'Ödeme Bekleniyor',
      CONFIRMED: 'Onaylandı',
      EXPIRED: 'Süresi Doldu',
      CANCELLED: 'İptal Edildi',
    };
    return map[status] ?? status;
  }
}

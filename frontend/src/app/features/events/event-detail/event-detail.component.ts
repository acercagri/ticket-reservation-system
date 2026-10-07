import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatDividerModule } from '@angular/material/divider';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { EventService } from '../../../core/services/event.service';
import { EventSeatService } from '../../../core/services/event-seat.service';
import { ReservationService } from '../../../core/services/reservation.service';
import { EventResponse } from '../../../core/models/event.models';
import { EventSeatResponse } from '../../../core/models/event-seat.models';
import { LoadingSpinnerComponent } from '../../../shared/components/loading-spinner/loading-spinner.component';
import { extractErrorMessage } from '../../../shared/utils/error-handler';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-event-detail',
  standalone: true,
  imports: [
    CommonModule,
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    MatDividerModule,
    MatSnackBarModule,
    MatDialogModule,
    LoadingSpinnerComponent,
  ],
  templateUrl: './event-detail.component.html',
  styleUrls: ['./event-detail.component.scss'],
})
export class EventDetailComponent implements OnInit {
  event: EventResponse | null = null;
  seats: EventSeatResponse[] = [];
  selectedSeatIds: Set<number> = new Set();
  loading = true;
  reserving = false;
  errorMessage = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private eventService: EventService,
    private eventSeatService: EventSeatService,
    private reservationService: ReservationService,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.eventService.findEventById(id).subscribe({
      next: (event) => {
        this.event = event;
        this.loadSeats(id);
      },
      error: () => { this.loading = false; },
    });
  }

  loadSeats(eventId: number): void {
    this.eventSeatService.findEventSeatsByEvent(eventId).subscribe({
      next: (seats) => {
        this.seats = seats;
        this.loading = false;
      },
      error: () => { this.loading = false; },
    });
  }

  get rows(): string[] {
    return [...new Set(this.seats.map((s) => s.rowName))].sort();
  }

  getSeatsForRow(row: string): EventSeatResponse[] {
    return this.seats
      .filter((s) => s.rowName === row)
      .sort((a, b) => a.seatNumber - b.seatNumber);
  }

  toggleSeat(seat: EventSeatResponse): void {
    if (seat.status !== 'AVAILABLE') return;
    if (this.selectedSeatIds.has(seat.id)) {
      this.selectedSeatIds.delete(seat.id);
    } else {
      this.selectedSeatIds.add(seat.id);
    }
  }

  get selectedSeats(): EventSeatResponse[] {
    return this.seats.filter((s) => this.selectedSeatIds.has(s.id));
  }

  get totalPrice(): number {
    return this.selectedSeats.reduce((sum, s) => sum + Number(s.price), 0);
  }

  createReservation(): void {
    if (this.selectedSeatIds.size === 0 || !this.event) return;
    this.reserving = true;
    this.errorMessage = '';

    this.reservationService
      .createReservation({
        eventId: this.event.id,
        eventSeatIds: [...this.selectedSeatIds],
      })
      .subscribe({
        next: (res) => {
          this.reserving = false;
          this.snackBar.open('Rezervasyon oluşturuldu!', 'Kapat', { duration: 3000 });
          this.router.navigate(['/reservations', res.id]);
        },
        error: (err: HttpErrorResponse) => {
          this.reserving = false;
          this.errorMessage = extractErrorMessage(err);
          if (err.status === 409) {
            this.selectedSeatIds.clear();
            this.loadSeats(this.event!.id);
          }
          this.snackBar.open(this.errorMessage, 'Kapat', { duration: 5000, panelClass: 'snack-error' });
        },
      });
  }

  getStatusLabel(status: string): string {
    const map: Record<string, string> = {
      ACTIVE: 'Aktif', DRAFT: 'Taslak', CANCELLED: 'İptal', COMPLETED: 'Tamamlandı',
    };
    return map[status] ?? status;
  }
}

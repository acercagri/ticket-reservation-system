import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { ReservationService } from '../../../core/services/reservation.service';
import { TicketService } from '../../../core/services/ticket.service';
import { AuthService } from '../../../core/services/auth.service';
import { TicketResponse } from '../../../core/models/ticket.models';
import { LoadingSpinnerComponent } from '../../../shared/components/loading-spinner/loading-spinner.component';
import { EmptyStateComponent } from '../../../shared/components/empty-state/empty-state.component';
import { forkJoin, of } from 'rxjs';
import { catchError, switchMap } from 'rxjs/operators';

@Component({
  selector: 'app-ticket-list',
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
  templateUrl: './ticket-list.component.html',
  styleUrls: ['./ticket-list.component.scss'],
})
export class TicketListComponent implements OnInit {
  tickets: TicketResponse[] = [];
  loading = true;

  constructor(
    private reservationService: ReservationService,
    private ticketService: TicketService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    const userId = this.authService.getUserId();
    if (!userId) { this.loading = false; return; }

    // Fetch all user reservations, then for each CONFIRMED reservation
    // fetch the ticket by reservationItemId (reservationId maps to reservationItem in backend)
    this.reservationService.findReservationsByUser(userId).pipe(
      switchMap((reservations) => {
        const confirmed = reservations.filter((r) => r.status === 'CONFIRMED');
        if (confirmed.length === 0) return of([]);
        const requests = confirmed.map((r) =>
          this.ticketService.findTicketByReservationItem(r.id).pipe(catchError(() => of(null)))
        );
        return forkJoin(requests);
      }),
      catchError(() => of([]))
    ).subscribe({
      next: (results) => {
        this.tickets = (results as (TicketResponse | null)[]).filter(
          (t): t is TicketResponse => t !== null
        );
        this.loading = false;
      },
      error: () => { this.loading = false; },
    });
  }

  getStatusLabel(status: string): string {
    return status === 'VALID' ? 'Geçerli' : 'İptal';
  }
}

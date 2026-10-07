import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatTableModule } from '@angular/material/table';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { EventSeatService } from '../../../core/services/event-seat.service';
import { EventService } from '../../../core/services/event.service';
import { SeatService } from '../../../core/services/seat.service';
import { EventSeatResponse } from '../../../core/models/event-seat.models';
import { EventResponse } from '../../../core/models/event.models';
import { SeatResponse } from '../../../core/models/seat.models';
import { LoadingSpinnerComponent } from '../../../shared/components/loading-spinner/loading-spinner.component';
import { EmptyStateComponent } from '../../../shared/components/empty-state/empty-state.component';
import { AdminConfirmDialogComponent } from '../shared/admin-confirm-dialog.component';
import { extractErrorMessage } from '../../../shared/utils/error-handler';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-admin-event-seats',
  standalone: true,
  imports: [
    CommonModule, ReactiveFormsModule, MatTableModule, MatCardModule, MatButtonModule,
    MatIconModule, MatFormFieldModule, MatInputModule, MatSelectModule,
    MatSnackBarModule, MatDialogModule, LoadingSpinnerComponent, EmptyStateComponent,
  ],
  templateUrl: './admin-event-seats.component.html',
  styleUrls: ['./admin-event-seats.component.scss'],
})
export class AdminEventSeatsComponent implements OnInit {
  eventSeats: EventSeatResponse[] = [];
  events: EventResponse[] = [];
  seats: SeatResponse[] = [];
  filteredSeats: SeatResponse[] = [];
  loading = true;
  showForm = false;
  formLoading = false;
  form: FormGroup;
  displayedColumns = ['id', 'eventName', 'rowName', 'seatNumber', 'price', 'status', 'actions'];
  statusOptions = ['AVAILABLE', 'RESERVED', 'SOLD'];

  constructor(
    private eventSeatService: EventSeatService,
    private eventService: EventService,
    private seatService: SeatService,
    private fb: FormBuilder,
    private snackBar: MatSnackBar,
    private dialog: MatDialog
  ) {
    this.form = this.fb.group({
      eventId: [null, Validators.required],
      seatId: [null, Validators.required],
      price: [null, [Validators.required, Validators.min(0.01)]],
      status: ['AVAILABLE', Validators.required],
    });
  }

  ngOnInit(): void {
    this.eventService.findAllEvents().subscribe((e) => { this.events = e; });
    this.seatService.findAllSeats().subscribe((s) => { this.seats = s; });
    this.load();
  }

  onEventChange(eventId: number): void {
    const event = this.events.find((e) => e.id === eventId);
    if (event) {
      this.seatService.findSeatsByVenue(event.venueId).subscribe((s) => { this.filteredSeats = s; });
    }
  }

  load(): void {
    this.loading = true;
    // Load all event seats by fetching from each event
    this.eventService.findAllEvents().subscribe({
      next: (events) => {
        if (events.length === 0) { this.eventSeats = []; this.loading = false; return; }
        import('rxjs').then(({ forkJoin }) => {
          const requests = events.map((e) =>
            this.eventSeatService.findEventSeatsByEvent(e.id)
          );
          forkJoin(requests).subscribe({
            next: (results) => {
              this.eventSeats = results.flat();
              this.loading = false;
            },
            error: () => { this.loading = false; },
          });
        });
      },
      error: () => { this.loading = false; },
    });
  }

  save(): void {
    if (this.form.invalid) return;
    this.formLoading = true;
    this.eventSeatService.createEventSeat(this.form.value).subscribe({
      next: () => {
        this.snackBar.open('Etkinlik koltuğu oluşturuldu.', 'Kapat', { duration: 3000 });
        this.form.reset({ status: 'AVAILABLE' });
        this.showForm = false;
        this.formLoading = false;
        this.load();
      },
      error: (err: HttpErrorResponse) => {
        this.formLoading = false;
        this.snackBar.open(extractErrorMessage(err), 'Kapat', { duration: 4000, panelClass: 'snack-error' });
      },
    });
  }

  delete(es: EventSeatResponse): void {
    const ref = this.dialog.open(AdminConfirmDialogComponent, {
      data: { message: `Etkinlik koltuğu #${es.id} silinsin mi?` },
    });
    ref.afterClosed().subscribe((confirmed) => {
      if (!confirmed) return;
      this.eventSeatService.deleteEventSeat(es.id).subscribe({
        next: () => { this.snackBar.open('Silindi.', 'Kapat', { duration: 3000 }); this.load(); },
        error: (err: HttpErrorResponse) =>
          this.snackBar.open(extractErrorMessage(err), 'Kapat', { duration: 4000, panelClass: 'snack-error' }),
      });
    });
  }

  getStatusLabel(s: string): string {
    const m: Record<string, string> = { AVAILABLE: 'Müsait', RESERVED: 'Rezerve', SOLD: 'Satıldı' };
    return m[s] ?? s;
  }
}

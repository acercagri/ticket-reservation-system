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
import { EventService } from '../../../core/services/event.service';
import { VenueService } from '../../../core/services/venue.service';
import { EventResponse } from '../../../core/models/event.models';
import { VenueResponse } from '../../../core/models/venue.models';
import { LoadingSpinnerComponent } from '../../../shared/components/loading-spinner/loading-spinner.component';
import { EmptyStateComponent } from '../../../shared/components/empty-state/empty-state.component';
import { AdminConfirmDialogComponent } from '../shared/admin-confirm-dialog.component';
import { extractErrorMessage } from '../../../shared/utils/error-handler';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-admin-events',
  standalone: true,
  imports: [
    CommonModule, ReactiveFormsModule, MatTableModule, MatCardModule, MatButtonModule,
    MatIconModule, MatFormFieldModule, MatInputModule, MatSelectModule,
    MatSnackBarModule, MatDialogModule, LoadingSpinnerComponent, EmptyStateComponent,
  ],
  templateUrl: './admin-events.component.html',
  styleUrls: ['./admin-events.component.scss'],
})
export class AdminEventsComponent implements OnInit {
  events: EventResponse[] = [];
  venues: VenueResponse[] = [];
  loading = true;
  showForm = false;
  formLoading = false;
  form: FormGroup;
  displayedColumns = ['id', 'name', 'venueName', 'startDateTime', 'status', 'actions'];

  statusOptions = ['DRAFT', 'ACTIVE', 'CANCELLED', 'COMPLETED'];

  constructor(
    private eventService: EventService,
    private venueService: VenueService,
    private fb: FormBuilder,
    private snackBar: MatSnackBar,
    private dialog: MatDialog
  ) {
    this.form = this.fb.group({
      name: ['', Validators.required],
      description: ['', Validators.required],
      startDateTime: ['', Validators.required],
      venueId: [null, Validators.required],
      status: ['DRAFT', Validators.required],
    });
  }

  ngOnInit(): void {
    this.venueService.findAllVenues().subscribe((v) => { this.venues = v; });
    this.load();
  }

  load(): void {
    this.loading = true;
    this.eventService.findAllEvents().subscribe({
      next: (e) => { this.events = e; this.loading = false; },
      error: () => { this.loading = false; },
    });
  }

  save(): void {
    if (this.form.invalid) return;
    this.formLoading = true;
    const val = this.form.value;
    // Spring Boot LocalDateTime format: "2024-12-25T20:00:00" (no Z suffix)
    const dt = new Date(val.startDateTime);
    const pad = (n: number) => String(n).padStart(2, '0');
    const localDt = `${dt.getFullYear()}-${pad(dt.getMonth()+1)}-${pad(dt.getDate())}T${pad(dt.getHours())}:${pad(dt.getMinutes())}:00`;
    const request = { ...val, startDateTime: localDt };
    this.eventService.createEvent(request).subscribe({
      next: () => {
        this.snackBar.open('Etkinlik oluşturuldu.', 'Kapat', { duration: 3000 });
        this.form.reset({ status: 'DRAFT' });
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

  delete(event: EventResponse): void {
    const ref = this.dialog.open(AdminConfirmDialogComponent, {
      data: { message: `"${event.name}" etkinliğini silmek istediğinize emin misiniz?` },
    });
    ref.afterClosed().subscribe((confirmed) => {
      if (!confirmed) return;
      this.eventService.deleteEvent(event.id).subscribe({
        next: () => { this.snackBar.open('Etkinlik silindi.', 'Kapat', { duration: 3000 }); this.load(); },
        error: (err: HttpErrorResponse) =>
          this.snackBar.open(extractErrorMessage(err), 'Kapat', { duration: 4000, panelClass: 'snack-error' }),
      });
    });
  }

  getStatusLabel(status: string): string {
    const map: Record<string, string> = { ACTIVE: 'Aktif', DRAFT: 'Taslak', CANCELLED: 'İptal', COMPLETED: 'Tamamlandı' };
    return map[status] ?? status;
  }
}

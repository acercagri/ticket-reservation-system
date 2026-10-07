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
import { SeatService } from '../../../core/services/seat.service';
import { VenueService } from '../../../core/services/venue.service';
import { SeatResponse } from '../../../core/models/seat.models';
import { VenueResponse } from '../../../core/models/venue.models';
import { LoadingSpinnerComponent } from '../../../shared/components/loading-spinner/loading-spinner.component';
import { EmptyStateComponent } from '../../../shared/components/empty-state/empty-state.component';
import { AdminConfirmDialogComponent } from '../shared/admin-confirm-dialog.component';
import { extractErrorMessage } from '../../../shared/utils/error-handler';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-admin-seats',
  standalone: true,
  imports: [
    CommonModule, ReactiveFormsModule, MatTableModule, MatCardModule, MatButtonModule,
    MatIconModule, MatFormFieldModule, MatInputModule, MatSelectModule,
    MatSnackBarModule, MatDialogModule, LoadingSpinnerComponent, EmptyStateComponent,
  ],
  templateUrl: './admin-seats.component.html',
  styleUrls: ['./admin-seats.component.scss'],
})
export class AdminSeatsComponent implements OnInit {
  seats: SeatResponse[] = [];
  venues: VenueResponse[] = [];
  loading = true;
  showForm = false;
  formLoading = false;
  form: FormGroup;
  displayedColumns = ['id', 'rowName', 'seatNumber', 'venueName', 'createdAt', 'actions'];

  constructor(
    private seatService: SeatService,
    private venueService: VenueService,
    private fb: FormBuilder,
    private snackBar: MatSnackBar,
    private dialog: MatDialog
  ) {
    this.form = this.fb.group({
      rowName: ['', Validators.required],
      seatNumber: [null, [Validators.required, Validators.min(1)]],
      venueId: [null, Validators.required],
    });
  }

  ngOnInit(): void {
    this.venueService.findAllVenues().subscribe((v) => { this.venues = v; });
    this.load();
  }

  load(): void {
    this.loading = true;
    this.seatService.findAllSeats().subscribe({
      next: (s) => { this.seats = s; this.loading = false; },
      error: () => { this.loading = false; },
    });
  }

  save(): void {
    if (this.form.invalid) return;
    this.formLoading = true;
    this.seatService.createSeat(this.form.value).subscribe({
      next: () => {
        this.snackBar.open('Koltuk oluşturuldu.', 'Kapat', { duration: 3000 });
        this.form.reset();
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

  delete(seat: SeatResponse): void {
    const ref = this.dialog.open(AdminConfirmDialogComponent, {
      data: { message: `Koltuk ${seat.rowName}-${seat.seatNumber} silinsin mi?` },
    });
    ref.afterClosed().subscribe((confirmed) => {
      if (!confirmed) return;
      this.seatService.deleteSeat(seat.id).subscribe({
        next: () => { this.snackBar.open('Koltuk silindi.', 'Kapat', { duration: 3000 }); this.load(); },
        error: (err: HttpErrorResponse) =>
          this.snackBar.open(extractErrorMessage(err), 'Kapat', { duration: 4000, panelClass: 'snack-error' }),
      });
    });
  }
}

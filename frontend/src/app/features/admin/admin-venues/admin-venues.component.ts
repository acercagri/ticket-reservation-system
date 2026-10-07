import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatTableModule } from '@angular/material/table';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { VenueService } from '../../../core/services/venue.service';
import { VenueResponse } from '../../../core/models/venue.models';
import { LoadingSpinnerComponent } from '../../../shared/components/loading-spinner/loading-spinner.component';
import { EmptyStateComponent } from '../../../shared/components/empty-state/empty-state.component';
import { AdminConfirmDialogComponent } from '../shared/admin-confirm-dialog.component';
import { extractErrorMessage } from '../../../shared/utils/error-handler';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-admin-venues',
  standalone: true,
  imports: [
    CommonModule, ReactiveFormsModule, MatTableModule, MatCardModule,
    MatButtonModule, MatIconModule, MatFormFieldModule, MatInputModule,
    MatSnackBarModule, MatDialogModule, LoadingSpinnerComponent, EmptyStateComponent,
  ],
  templateUrl: './admin-venues.component.html',
  styleUrls: ['./admin-venues.component.scss'],
})
export class AdminVenuesComponent implements OnInit {
  venues: VenueResponse[] = [];
  loading = true;
  showForm = false;
  formLoading = false;
  form: FormGroup;
  displayedColumns = ['id', 'name', 'city', 'address', 'createdAt', 'actions'];

  constructor(
    private venueService: VenueService,
    private fb: FormBuilder,
    private snackBar: MatSnackBar,
    private dialog: MatDialog
  ) {
    this.form = this.fb.group({
      name: ['', Validators.required],
      city: ['', Validators.required],
      address: ['', Validators.required],
    });
  }

  ngOnInit(): void { this.load(); }

  load(): void {
    this.loading = true;
    this.venueService.findAllVenues().subscribe({
      next: (v) => { this.venues = v; this.loading = false; },
      error: () => { this.loading = false; },
    });
  }

  save(): void {
    if (this.form.invalid) return;
    this.formLoading = true;
    this.venueService.createVenue(this.form.value).subscribe({
      next: () => {
        this.snackBar.open('Mekan oluşturuldu.', 'Kapat', { duration: 3000 });
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

  delete(venue: VenueResponse): void {
    const ref = this.dialog.open(AdminConfirmDialogComponent, {
      data: { message: `"${venue.name}" mekanını silmek istediğinize emin misiniz?` },
    });
    ref.afterClosed().subscribe((confirmed) => {
      if (!confirmed) return;
      this.venueService.deleteVenue(venue.id).subscribe({
        next: () => { this.snackBar.open('Mekan silindi.', 'Kapat', { duration: 3000 }); this.load(); },
        error: (err: HttpErrorResponse) =>
          this.snackBar.open(extractErrorMessage(err), 'Kapat', { duration: 4000, panelClass: 'snack-error' }),
      });
    });
  }
}

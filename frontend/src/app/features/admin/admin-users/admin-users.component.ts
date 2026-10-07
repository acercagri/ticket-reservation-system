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
import { MatDialogModule, MatDialog } from '@angular/material/dialog';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatChipsModule } from '@angular/material/chips';
import { UserService } from '../../../core/services/user.service';
import { UserResponse } from '../../../core/models/user.models';
import { LoadingSpinnerComponent } from '../../../shared/components/loading-spinner/loading-spinner.component';
import { EmptyStateComponent } from '../../../shared/components/empty-state/empty-state.component';
import { extractErrorMessage } from '../../../shared/utils/error-handler';
import { HttpErrorResponse } from '@angular/common/http';
import { AdminConfirmDialogComponent } from '../shared/admin-confirm-dialog.component';

@Component({
  selector: 'app-admin-users',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatTableModule,
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatDialogModule,
    MatSnackBarModule,
    MatChipsModule,
    LoadingSpinnerComponent,
    EmptyStateComponent,
  ],
  templateUrl: './admin-users.component.html',
  styleUrls: ['./admin-users.component.scss'],
})
export class AdminUsersComponent implements OnInit {
  users: UserResponse[] = [];
  loading = true;
  showForm = false;
  formLoading = false;
  form: FormGroup;
  displayedColumns = ['id', 'username', 'email', 'role', 'createdAt', 'actions'];

  constructor(
    private userService: UserService,
    private fb: FormBuilder,
    private snackBar: MatSnackBar,
    private dialog: MatDialog
  ) {
    this.form = this.fb.group({
      username: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      role: ['USER', Validators.required],
    });
  }

  ngOnInit(): void { this.load(); }

  load(): void {
    this.loading = true;
    this.userService.findAllUsers().subscribe({
      next: (u) => { this.users = u; this.loading = false; },
      error: () => { this.loading = false; },
    });
  }

  save(): void {
    if (this.form.invalid) return;
    this.formLoading = true;
    this.userService.createUser(this.form.value).subscribe({
      next: () => {
        this.snackBar.open('Kullanıcı oluşturuldu.', 'Kapat', { duration: 3000 });
        this.form.reset({ role: 'USER' });
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

  delete(user: UserResponse): void {
    const ref = this.dialog.open(AdminConfirmDialogComponent, {
      data: { message: `"${user.username}" kullanıcısını silmek istediğinize emin misiniz?` },
    });
    ref.afterClosed().subscribe((confirmed) => {
      if (!confirmed) return;
      this.userService.deleteUser(user.id).subscribe({
        next: () => {
          this.snackBar.open('Kullanıcı silindi.', 'Kapat', { duration: 3000 });
          this.load();
        },
        error: (err: HttpErrorResponse) =>
          this.snackBar.open(extractErrorMessage(err), 'Kapat', { duration: 4000, panelClass: 'snack-error' }),
      });
    });
  }
}

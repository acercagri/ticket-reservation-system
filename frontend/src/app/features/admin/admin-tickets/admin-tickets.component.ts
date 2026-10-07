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
import { TicketService } from '../../../core/services/ticket.service';
import { TicketResponse, TicketStatus } from '../../../core/models/ticket.models';
import { LoadingSpinnerComponent } from '../../../shared/components/loading-spinner/loading-spinner.component';
import { EmptyStateComponent } from '../../../shared/components/empty-state/empty-state.component';
import { extractErrorMessage } from '../../../shared/utils/error-handler';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-admin-tickets',
  standalone: true,
  imports: [
    CommonModule, FormsModule, MatTableModule, MatCardModule, MatButtonModule,
    MatIconModule, MatFormFieldModule, MatSelectModule, MatSnackBarModule,
    LoadingSpinnerComponent, EmptyStateComponent,
  ],
  templateUrl: './admin-tickets.component.html',
  styleUrls: ['./admin-tickets.component.scss'],
})
export class AdminTicketsComponent implements OnInit {
  tickets: TicketResponse[] = [];
  loading = true;
  selectedStatus: TicketStatus = 'VALID';
  statusOptions: TicketStatus[] = ['VALID', 'CANCELLED'];
  displayedColumns = ['id', 'ticketCode', 'eventName', 'rowName', 'seatNumber', 'status', 'createdAt', 'actions'];

  constructor(
    private ticketService: TicketService,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void { this.load(); }

  load(): void {
    this.loading = true;
    this.ticketService.findTicketsByStatus(this.selectedStatus).subscribe({
      next: (t) => { this.tickets = t; this.loading = false; },
      error: () => { this.loading = false; },
    });
  }

  updateStatus(ticket: TicketResponse, status: TicketStatus): void {
    this.ticketService.updateTicketStatus(ticket.id, { status }).subscribe({
      next: () => {
        this.snackBar.open('Bilet durumu güncellendi.', 'Kapat', { duration: 3000 });
        this.load();
      },
      error: (err: HttpErrorResponse) =>
        this.snackBar.open(extractErrorMessage(err), 'Kapat', { duration: 4000, panelClass: 'snack-error' }),
    });
  }

  getStatusLabel(s: string): string {
    return s === 'VALID' ? 'Geçerli' : 'İptal';
  }
}

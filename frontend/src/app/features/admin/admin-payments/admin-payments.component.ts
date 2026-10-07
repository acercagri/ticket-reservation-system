import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatTableModule } from '@angular/material/table';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { PaymentService } from '../../../core/services/payment.service';
import { PaymentResponse, PaymentStatus } from '../../../core/models/payment.models';
import { LoadingSpinnerComponent } from '../../../shared/components/loading-spinner/loading-spinner.component';
import { EmptyStateComponent } from '../../../shared/components/empty-state/empty-state.component';

@Component({
  selector: 'app-admin-payments',
  standalone: true,
  imports: [
    CommonModule, FormsModule, MatTableModule, MatCardModule, MatIconModule,
    MatFormFieldModule, MatSelectModule, MatSnackBarModule,
    LoadingSpinnerComponent, EmptyStateComponent,
  ],
  templateUrl: './admin-payments.component.html',
  styleUrls: ['./admin-payments.component.scss'],
})
export class AdminPaymentsComponent implements OnInit {
  payments: PaymentResponse[] = [];
  loading = true;
  selectedStatus: PaymentStatus = 'PAID';
  statusOptions: PaymentStatus[] = ['PENDING', 'PAID', 'FAILED', 'REFUNDED'];
  displayedColumns = ['id', 'reservationId', 'amount', 'status', 'paymentDate'];

  constructor(private paymentService: PaymentService) {}

  ngOnInit(): void { this.load(); }

  load(): void {
    this.loading = true;
    this.paymentService.findPaymentsByStatus(this.selectedStatus).subscribe({
      next: (p) => { this.payments = p; this.loading = false; },
      error: () => { this.loading = false; },
    });
  }

  getStatusLabel(s: string): string {
    const m: Record<string, string> = {
      PENDING: 'Bekliyor', PAID: 'Ödendi', FAILED: 'Başarısız', REFUNDED: 'İade Edildi',
    };
    return m[s] ?? s;
  }
}

import { Component, OnInit, ElementRef, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatDividerModule } from '@angular/material/divider';
import { TicketService } from '../../../core/services/ticket.service';
import { TicketResponse } from '../../../core/models/ticket.models';
import { LoadingSpinnerComponent } from '../../../shared/components/loading-spinner/loading-spinner.component';

@Component({
  selector: 'app-ticket-detail',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink,
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    MatDividerModule,
    LoadingSpinnerComponent,
  ],
  templateUrl: './ticket-detail.component.html',
  styleUrls: ['./ticket-detail.component.scss'],
})
export class TicketDetailComponent implements OnInit {
  @ViewChild('qrCanvas') qrCanvas?: ElementRef<HTMLCanvasElement>;

  ticket: TicketResponse | null = null;
  loading = true;
  qrGenerated = false;

  constructor(
    private route: ActivatedRoute,
    private ticketService: TicketService
  ) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.ticketService.findTicketById(id).subscribe({
      next: (ticket) => {
        this.ticket = ticket;
        this.loading = false;
        setTimeout(() => this.generateQR(), 100);
      },
      error: () => { this.loading = false; },
    });
  }

  async generateQR(): Promise<void> {
    if (!this.ticket || !this.qrCanvas) return;
    try {
      const QRCode = await import('qrcode');
      await QRCode.toCanvas(this.qrCanvas.nativeElement, this.ticket.ticketCode, {
        width: 200,
        margin: 2,
        color: { dark: '#1a237e', light: '#ffffff' },
      });
      this.qrGenerated = true;
    } catch {
      // QR generation optional - silently fail
    }
  }

  getStatusLabel(status: string): string {
    return status === 'VALID' ? 'Geçerli' : 'İptal Edildi';
  }
}

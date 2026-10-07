import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatChipsModule } from '@angular/material/chips';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { MatInputModule } from '@angular/material/input';
import { EventService } from '../../../core/services/event.service';
import { EventResponse, EventStatus } from '../../../core/models/event.models';
import { LoadingSpinnerComponent } from '../../../shared/components/loading-spinner/loading-spinner.component';
import { EmptyStateComponent } from '../../../shared/components/empty-state/empty-state.component';

@Component({
  selector: 'app-event-list',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink,
    FormsModule,
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    MatChipsModule,
    MatFormFieldModule,
    MatSelectModule,
    MatInputModule,
    LoadingSpinnerComponent,
    EmptyStateComponent,
  ],
  templateUrl: './event-list.component.html',
  styleUrls: ['./event-list.component.scss'],
})
export class EventListComponent implements OnInit {
  events: EventResponse[] = [];
  filteredEvents: EventResponse[] = [];
  loading = true;
  searchQuery = '';
  selectedStatus: EventStatus | '' = '';

  statusOptions: { value: EventStatus | ''; label: string }[] = [
    { value: '', label: 'Tümü' },
    { value: 'ACTIVE', label: 'Aktif' },
    { value: 'DRAFT', label: 'Taslak' },
    { value: 'COMPLETED', label: 'Tamamlandı' },
    { value: 'CANCELLED', label: 'İptal' },
  ];

  constructor(private eventService: EventService) {}

  ngOnInit(): void {
    this.eventService.findAllEvents().subscribe({
      next: (events) => {
        this.events = events;
        this.filteredEvents = events;
        this.loading = false;
      },
      error: () => { this.loading = false; },
    });
  }

  applyFilter(): void {
    this.filteredEvents = this.events.filter((e) => {
      const matchSearch =
        !this.searchQuery ||
        e.name.toLowerCase().includes(this.searchQuery.toLowerCase()) ||
        e.venueName.toLowerCase().includes(this.searchQuery.toLowerCase());
      const matchStatus = !this.selectedStatus || e.status === this.selectedStatus;
      return matchSearch && matchStatus;
    });
  }

  getStatusColor(status: EventStatus): string {
    const map: Record<EventStatus, string> = {
      ACTIVE: 'primary',
      DRAFT: 'accent',
      CANCELLED: 'warn',
      COMPLETED: '',
    };
    return map[status] ?? '';
  }

  getStatusLabel(status: EventStatus): string {
    const map: Record<EventStatus, string> = {
      ACTIVE: 'Aktif',
      DRAFT: 'Taslak',
      CANCELLED: 'İptal',
      COMPLETED: 'Tamamlandı',
    };
    return map[status] ?? status;
  }
}

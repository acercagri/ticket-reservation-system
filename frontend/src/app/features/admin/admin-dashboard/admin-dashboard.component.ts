import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { forkJoin } from 'rxjs';
import { UserService } from '../../../core/services/user.service';
import { VenueService } from '../../../core/services/venue.service';
import { EventService } from '../../../core/services/event.service';
import { ReservationService } from '../../../core/services/reservation.service';

interface StatCard {
  label: string;
  value: number | string;
  icon: string;
  color: string;
}

@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  imports: [CommonModule, MatCardModule, MatIconModule, MatProgressSpinnerModule],
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.scss'],
})
export class AdminDashboardComponent implements OnInit {
  stats: StatCard[] = [];
  loading = true;

  constructor(
    private userService: UserService,
    private venueService: VenueService,
    private eventService: EventService,
    private reservationService: ReservationService
  ) {}

  ngOnInit(): void {
    forkJoin({
      users: this.userService.findAllUsers(),
      venues: this.venueService.findAllVenues(),
      events: this.eventService.findAllEvents(),
      pendingRes: this.reservationService.findReservationsByStatus('PENDING'),
      confirmedRes: this.reservationService.findReservationsByStatus('CONFIRMED'),
    }).subscribe({
      next: (data) => {
        const activeEvents = data.events.filter((e) => e.status === 'ACTIVE').length;
        this.stats = [
          { label: 'Toplam Kullanıcı', value: data.users.length, icon: 'people', color: '#3f51b5' },
          { label: 'Toplam Mekan', value: data.venues.length, icon: 'place', color: '#009688' },
          { label: 'Toplam Etkinlik', value: data.events.length, icon: 'event', color: '#ff5722' },
          { label: 'Aktif Etkinlik', value: activeEvents, icon: 'event_available', color: '#4caf50' },
          { label: 'Bekleyen Rezervasyon', value: data.pendingRes.length, icon: 'hourglass_empty', color: '#ff9800' },
          { label: 'Onaylı Rezervasyon', value: data.confirmedRes.length, icon: 'check_circle', color: '#2196f3' },
        ];
        this.loading = false;
      },
      error: () => { this.loading = false; },
    });
  }
}

import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatListModule } from '@angular/material/list';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { AuthService } from '../../../core/services/auth.service';

interface NavItem {
  label: string;
  icon: string;
  route: string;
}

@Component({
  selector: 'app-admin-layout',
  standalone: true,
  imports: [
    CommonModule,
    RouterOutlet,
    RouterLink,
    RouterLinkActive,
    MatSidenavModule,
    MatToolbarModule,
    MatListModule,
    MatIconModule,
    MatButtonModule,
  ],
  templateUrl: './admin-layout.component.html',
  styleUrls: ['./admin-layout.component.scss'],
})
export class AdminLayoutComponent {
  isMobile = false;
  sidenavOpened = true;

  navItems: NavItem[] = [
    { label: 'Dashboard', icon: 'dashboard', route: '/admin' },
    { label: 'Kullanıcılar', icon: 'people', route: '/admin/users' },
    { label: 'Mekanlar', icon: 'place', route: '/admin/venues' },
    { label: 'Koltuklar', icon: 'event_seat', route: '/admin/seats' },
    { label: 'Etkinlikler', icon: 'event', route: '/admin/events' },
    { label: 'Etkinlik Koltukları', icon: 'confirmation_number', route: '/admin/event-seats' },
    { label: 'Rezervasyonlar', icon: 'bookmark', route: '/admin/reservations' },
    { label: 'Ödemeler', icon: 'payments', route: '/admin/payments' },
    { label: 'Biletler', icon: 'local_activity', route: '/admin/tickets' },
  ];

  constructor(
    public authService: AuthService,
    private router: Router,
    private bp: BreakpointObserver
  ) {
    this.bp.observe([Breakpoints.Handset]).subscribe((result) => {
      this.isMobile = result.matches;
      this.sidenavOpened = !result.matches;
    });
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}

import { Routes } from '@angular/router';
import { authGuard } from './core/guards/auth.guard';
import { adminGuard } from './core/guards/admin.guard';

export const routes: Routes = [
  {
    path: 'login',
    loadComponent: () =>
      import('./features/auth/login/login.component').then((m) => m.LoginComponent),
  },
  {
    path: 'register',
    loadComponent: () =>
      import('./features/auth/register/register.component').then((m) => m.RegisterComponent),
  },

  // User layout wrapper (empty path, children handle sub-paths)
  {
    path: '',
    loadComponent: () =>
      import('./features/layout/user-layout/user-layout.component').then((m) => m.UserLayoutComponent),
    canActivate: [authGuard],
    children: [
      { path: '', redirectTo: 'events', pathMatch: 'full' },
      {
        path: 'events',
        loadComponent: () =>
          import('./features/events/event-list/event-list.component').then((m) => m.EventListComponent),
      },
      {
        path: 'events/:id',
        loadComponent: () =>
          import('./features/events/event-detail/event-detail.component').then((m) => m.EventDetailComponent),
      },
      {
        path: 'reservations',
        loadComponent: () =>
          import('./features/reservations/reservation-list/reservation-list.component').then(
            (m) => m.ReservationListComponent
          ),
      },
      {
        path: 'reservations/:id',
        loadComponent: () =>
          import('./features/reservations/reservation-detail/reservation-detail.component').then(
            (m) => m.ReservationDetailComponent
          ),
      },
      {
        path: 'tickets',
        loadComponent: () =>
          import('./features/tickets/ticket-list/ticket-list.component').then((m) => m.TicketListComponent),
      },
      {
        path: 'tickets/:id',
        loadComponent: () =>
          import('./features/tickets/ticket-detail/ticket-detail.component').then((m) => m.TicketDetailComponent),
      },
    ],
  },

  // Admin routes
  {
    path: 'admin',
    loadComponent: () =>
      import('./features/admin/admin-layout/admin-layout.component').then((m) => m.AdminLayoutComponent),
    canActivate: [adminGuard],
    children: [
      {
        path: '',
        pathMatch: 'full',
        loadComponent: () =>
          import('./features/admin/admin-dashboard/admin-dashboard.component').then(
            (m) => m.AdminDashboardComponent
          ),
      },
      {
        path: 'users',
        loadComponent: () =>
          import('./features/admin/admin-users/admin-users.component').then((m) => m.AdminUsersComponent),
      },
      {
        path: 'venues',
        loadComponent: () =>
          import('./features/admin/admin-venues/admin-venues.component').then((m) => m.AdminVenuesComponent),
      },
      {
        path: 'seats',
        loadComponent: () =>
          import('./features/admin/admin-seats/admin-seats.component').then((m) => m.AdminSeatsComponent),
      },
      {
        path: 'events',
        loadComponent: () =>
          import('./features/admin/admin-events/admin-events.component').then((m) => m.AdminEventsComponent),
      },
      {
        path: 'event-seats',
        loadComponent: () =>
          import('./features/admin/admin-event-seats/admin-event-seats.component').then(
            (m) => m.AdminEventSeatsComponent
          ),
      },
      {
        path: 'reservations',
        loadComponent: () =>
          import('./features/admin/admin-reservations/admin-reservations.component').then(
            (m) => m.AdminReservationsComponent
          ),
      },
      {
        path: 'payments',
        loadComponent: () =>
          import('./features/admin/admin-payments/admin-payments.component').then(
            (m) => m.AdminPaymentsComponent
          ),
      },
      {
        path: 'tickets',
        loadComponent: () =>
          import('./features/admin/admin-tickets/admin-tickets.component').then(
            (m) => m.AdminTicketsComponent
          ),
      },
    ],
  },

  { path: '**', redirectTo: '/login' },
];

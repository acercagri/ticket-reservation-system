import { HttpErrorResponse } from '@angular/common/http';
import { ErrorResponse } from '../../../app/core/models/error.models';

export function extractErrorMessage(error: HttpErrorResponse): string {
  if (error.error && typeof error.error === 'object') {
    const apiError = error.error as ErrorResponse;
    if (apiError.message) {
      return apiError.message;
    }
  }
  switch (error.status) {
    case 400:
      return 'Geçersiz istek. Lütfen form alanlarını kontrol edin.';
    case 401:
      return 'Oturum süreniz dolmuş. Lütfen tekrar giriş yapın.';
    case 403:
      return 'Bu işlem için yetkiniz yok.';
    case 404:
      return 'İstenen kayıt bulunamadı.';
    case 409:
      return 'Seçtiğiniz koltuk artık müsait değil. Lütfen başka bir koltuk seçin.';
    default:
      return 'Bir hata oluştu. Lütfen tekrar deneyin.';
  }
}

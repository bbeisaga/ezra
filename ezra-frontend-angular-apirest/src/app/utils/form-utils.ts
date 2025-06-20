import { FormGroup } from '@angular/forms';
export class FormUtils {

  static isValidField(form: FormGroup, fieldName: string): boolean | null {
    return (form.controls[fieldName].errors && form.controls[fieldName].touched)
  }

  static getFieldError(form: FormGroup, fieldName: string): string | null {
    if (!form.controls[fieldName]) return null;
    const errors = form.controls[fieldName].errors || {}
    for (const key of Object.keys(errors)) {
      switch (key) {
        case 'required': return 'Campo requerido';
        case 'minlength': return `Mínimo de ${errors['minlength'].requiredLength} caracteres`;
        case 'maxlength': return `Máximo de ${errors['maxlength'].requiredLength} caracteres`;
        case 'pattern': return `No cumple requerimientos de campo`;
        case 'min': return `Valor mínimo de ${errors['min'].min}`;
      }
    }
    return null;
  }

}
import { EventEmitter } from "@angular/core";

/**
 * Event emitter which will store a boolean value that is true if user is authenticated and false if not.
 */
export class AuthEmitter {
  static authEmitter = new EventEmitter<boolean>()
}

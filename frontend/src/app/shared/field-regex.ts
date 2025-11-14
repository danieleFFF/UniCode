export class FieldRegex {

  public static validateUsername(username: string): string | null {
    if (username.length < 4 || username.length > 16 || !/[a-zA-Z]/.test(username)) {
      return "Username must be between 4 and 16 characters and contain at least one letter.";
    }
    return null;
  }

  public static validateEmail(email: string): string | null {
    const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    if (!emailRegex.test(email)) {
      return "Please enter a valid email address.";
    }
    return null;
  }

  public static validatePassword(password: string): string | null {
    const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).{8,}$/;
    if (!passwordRegex.test(password)) {
      return "Password must be at least 8 characters long and contain an uppercase letter, a lowercase letter, and a number.";
    }
    return null;
  }
}

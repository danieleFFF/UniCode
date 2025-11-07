import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

export interface DropdownItem {
  name: string;
  path: string;
  iconPath?: string;
}

@Component({
  selector: 'app-dropdown-menu',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './dropdown-menu.html',
  styleUrl: './dropdown-menu.scss'
})
export class DropdownMenu {
  @Input() items: DropdownItem[] = [];
}
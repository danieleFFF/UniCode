import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DropdownMenu, DropdownItem } from './dropdown-menu';

describe('DropdownMenu', () => {
  let component: DropdownMenu;
  let fixture: ComponentFixture<DropdownMenu>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DropdownMenu]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DropdownMenu);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should display no items if the items input is empty', () => {
    const compiled = fixture.nativeElement as HTMLElement;
    expect(compiled.querySelector('.dropdown-item')).toBeNull();
  });

  it('should display the correct number of items based on input', () => {
    const mockItems: DropdownItem[] = [
      { name: 'Item 1', path: '/item1' },
      { name: 'Item 2', path: '/item2' },
    ];

    component.items = mockItems;
    fixture.detectChanges(); // Trigger change detection after setting input

    const compiled = fixture.nativeElement as HTMLElement;
    const items = compiled.querySelectorAll('.dropdown-item');
    expect(items.length).toBe(2);
  });

  it('should render item name and icon correctly', () => {
    const mockItems: DropdownItem[] = [
      { name: 'Python', path: '/python', iconPath: 'assets/python.png' },
    ];

    component.items = mockItems;
    fixture.detectChanges();

    const compiled = fixture.nativeElement as HTMLElement;
    const itemText = compiled.querySelector('.dropdown-item-text');
    const itemIcon = compiled.querySelector('.dropdown-item-icon') as HTMLImageElement;

    expect(itemText?.textContent).toContain('Python');
    expect(itemIcon).not.toBeNull();
    expect(itemIcon?.alt).toBe('Python');
  });
});

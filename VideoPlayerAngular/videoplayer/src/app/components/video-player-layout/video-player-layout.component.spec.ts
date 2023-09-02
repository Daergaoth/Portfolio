import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VideoPlayerLayoutComponent } from './video-player-layout.component';

describe('VideoPlayerLayoutComponent', () => {
  let component: VideoPlayerLayoutComponent;
  let fixture: ComponentFixture<VideoPlayerLayoutComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [VideoPlayerLayoutComponent]
    });
    fixture = TestBed.createComponent(VideoPlayerLayoutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

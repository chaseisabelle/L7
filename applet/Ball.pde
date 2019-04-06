class Ball
extends Rectangle {
  
  int livesR = 0;
  int livesG = 0;
  int livesB = 0;
  
  Ball() {
    super(screenWidth / 2 - 10, screenHeight / 2 - 10, 20, 20, 255, 255, 255);
    dy = 3;
  }
  
  public void move() {
    Brick brick;
    
    for (brick = level.bricks; brick != null;  brick = brick.next) {
      if (brick.explosion == null) break;
    }
    
    if (brick == null) return;
    
    if (isTouching(level.paddle)) {
      dx = (x + w / 2) - (level.paddle.x + level.paddle.w / 2);
      dy = (y + h / 2) - (level.paddle.y + level.paddle.h / 2);
      
      dx /= 2;
      dy /= 2;
      
      if (dy == 0) dy = 1;
    }
    
    if (y + h >= screenHeight) {
      lives--;
    }
    
    super.move();
  }
  
  public void draw() {
    super.draw();
    
    stroke(livesR, livesG, livesB);
    fill(livesR, livesG, livesB);
    text(lives, x + w / 2, y + h / 2);
  }
  
}

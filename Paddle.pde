class Paddle
extends Rectangle {
  
  public int scoreR = 255;
  public int scoreG = 255;
  public int scoreB = 255;
  
  Paddle() {
    super(0, screenHeight - 60, 100, 20, 255, 255, 255);
  }
  
  public void move() {
    Brick brick;
    
    for (brick = level.bricks; brick != null;  brick = brick.next) {
      if (brick.explosion == null) break;
    }
    
    if (brick == null) return;
    
    x = mouseX - w / 2;
  }
  
  public void render() {
    super.render();
    
    stroke(scoreR, scoreG, scoreB);
    fill(scoreR, scoreG, scoreB);
    text(score, x + w / 2, y + h / 2);
  }
  
}

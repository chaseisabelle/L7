class Brick
extends Rectangle {
  
  public Explosion explosion = null;
  
  public Brick next = null;
  
  public int worth = 0;
  public int worthR = 0;
  public int worthG = 0;
  public int worthB = 0;
  
  Brick(int worth, int r, int g, int b) {
    super((int)random(screenWidth - 25), (int)random(screenHeight / 3), 25, 15, r, g, b);
    
    this.worth = worth;
    
    dy = 5;
    dx = (int)random(5) + 1;
  }
  
  public void draw() {
    if (explosion != null) {
      explosion.draw();
      return;
    }
    
    super.draw();
  }
  
  public void move() {
    for (Brick brick = level.bricks; brick != null; brick = brick.next) {
      if (brick != this && brick.explosion == null && isTouching(brick)) {
        dx = (x - brick.x) / 10;
        dy = (y - brick.y) / 2;
      }
    }
    
    if (y >= screenHeight / 3) {
      dy -= (y - screenHeight / 3) / 5;
    }
    
    if (isTouching(level.ball)) {
      explosion = new Explosion(this);
      score += worth;
      return;
    }
    
    super.move();
  }
  
  public void render() {
    super.render();
    
    stroke(worthR, worthG, worthB);
    fill(worthR, worthG, worthB);
    text(worth, x + w / 2, y + h / 2);
  }
  
}

class Explosion {
  
  public Brick brick;
  
  public int[] x;
  public int[] y;
  public int[] dx;
  public int[] dy;
  
  Explosion(Brick brick) {
    x = new int[brick.w * brick.h];
    y = new int[brick.w * brick.h];
    dx = new int[brick.w * brick.h];
    dy = new int[brick.w * brick.h];
    
    int i = 0;
    
    for (int xi = brick.x; xi < brick.x + brick.w; xi++) {
      for (int yi = brick.y; yi < brick.y + brick.h; yi++) {
        x[i] = xi;
        y[i] = yi;
        dx[i] = xi - level.ball.x + level.ball.w / 2 + (int)random(level.ball.dx);
        dy[i] = yi - level.ball.y + level.ball.h / 2 + (int)random(level.ball.dy);
        
        i++;
      }
    }
    
    this.brick = brick;
  }
  
  public void draw() {
    move();
    render();
  }
  
  public void move() {
    for (int i = 0; i < brick.w * brick.h; i++) {
      if (!(dy[i] == 0 && y[i] == 0)) {
        if (x[i] <= 0) dx[i] = (int)abs(dx[i]);
        if (x[i] >= screenWidth) dx[i] = -(int)abs(dx[i] / ((int)random(2) + 1));
        
        if (y[i] <= 0) dy[i] = (int)abs(dy[i]);
        if (y[i] >= screenHeight) dy[i] = -(int)abs(dy[i] / 2);
        
        if (x[i] >= level.paddle.x && x[i] <= level.paddle.x + level.paddle.w &&
            y[i] >= level.paddle.y && y[i] <= level.paddle.y + level.paddle.h) {
              dx[i] = x[i] - level.paddle.x + level.paddle.w / 2;
              dy[i] = y[i] - level.paddle.y + level.paddle.h / 2;
        }
        
        if (x[i] >= level.ball.x && x[i] <= level.ball.x + level.ball.w &&
            y[i] >= level.ball.y && y[i] <= level.ball.y + level.ball.h) {
              dx[i] = (x[i] - level.ball.x + level.ball.w / 2) / ((int)random(2) + 1);
              dy[i] = (y[i] - level.ball.y + level.ball.h / 2) / ((int)random(2) + 1);    
        }
        
        x[i] += dx[i];
        y[i] += dy[i];
        
        dy[i]++;  //< gravity
      }
    }
  }
  
  public void render() {
    stroke(brick.r, brick.g, brick.b);
    
    for (int i = 0; i < brick.w * brick.h; i++) {
      point(x[i], y[i]);
    }
  }
  
}

class RainbowBrick
extends Brick {
  
  public int dr = 1;
  public int dg = 1;
  public int db = 1;
  
  RainbowBrick(int worth, int r, int g, int b) {
    super(worth, r, g, b);
  }
  
  public void render() {
    if (r >= 255) dr = -(int)abs(dr);
    if (r <= 0) dr = (int)abs(dr);
    
    if (g >= 255) dg = -(int)abs(dg);
    if (g <= 0) dg = (int)abs(dg);
    
    if (b >= 255) db = -(int)abs(db);
    if (b <= 0) db = (int)abs(db);
    
    r += dr;
    g += dg;
    b += db;
    
    worthR = (int)random(255);
    worthG = (int)random(255);
    worthB = (int)random(255);
    
    super.render();
  }
  
}

abstract class Level {
  
  public int r = 0;
  public int g = 0;
  public int b = 0;
  
  public Paddle paddle = new Paddle();
  public Ball ball = new Ball();
  
  public Brick bricks = null;
  
  Level(int r, int g, int b) {
    this.r = r;
    this.g = g;
    this.b = b;
  }
  
  public void draw() {
    background(r, g, b);
    
    ball.draw();
    paddle.draw();
    
    for (Brick brick = bricks; brick != null; brick = brick.next) {
      brick.draw();
    }
    
    if (mousePressed) {
      nextLevel();
    }
  }
  
  public void nextLevel() {
    for (Brick brick = bricks; brick != null; brick = brick.next) {
      if (brick.explosion == null) return;
    }
    
    level = getNextLevel();
  }
  
  public abstract Level getNextLevel();
  
}

class White
extends Level { 
  
  White() {
    super(255, 255, 255);
    
    paddle.r = paddle.g = paddle.b = 0;
    paddle.scoreR = paddle.scoreG = paddle.scoreB = 255;
    ball.r = ball.g = ball.b = 0;
    ball.livesR = ball.livesG = ball.livesB = 255;
    
    for (int i = 0; i < 5; i++) {
      Brick brick = new Brick(1 * (i + 1), 0, 0, 0);
      
      brick.worthR = brick.worthG = brick.worthB = 255;
      
      brick.next = bricks;
      bricks = brick;
    }
  }
  
  public Level getNextLevel() {
    return new Blue();
  }
  
}

class Blue
extends Level { 
  
  Blue() {
    super(0, 0, 255);
    
    paddle.r = 255;
    paddle.g = 0;
    paddle.b = 0;
    
    paddle.scoreR = 0;
    paddle.scoreG = 0;
    paddle.scoreB = 255;
    
    ball.r = 255;
    ball.g = 0;
    ball.b = 0;
    
    ball.livesR = 0;
    ball.livesG = 0;
    ball.livesB = 255;
    
    for (int i = 0; i < 7; i++) {
      Brick brick = new Brick(2 * (i + 1), 255, 0, 0);
      
      brick.worthR = 0;
      brick.worthG = 0;
      brick.worthB = 255;
      
      brick.next = bricks;
      bricks = brick;
    }
  }
  
  public Level getNextLevel() {
    return new Green();
  }
  
}

class Green
extends Level {
  
  Green() {
    super(0, 255, 0);
    
    paddle.r = 255;
    paddle.g = 0;
    paddle.b = 255;
    
    paddle.scoreR = 0;
    paddle.scoreG = 255;
    paddle.scoreB = 0;
    
    ball.r = 255;
    ball.g = 0;
    ball.b = 255;
    
    ball.livesR = 0;
    ball.livesG = 255;
    ball.livesB = 0;
    
    for (int i = 0; i < 10; i++) {
      Brick brick = new Brick(3 * (i + 1), 255, 0, 255);
      
      brick.worthR = 0;
      brick.worthG = 255;
      brick.worthB = 0;
      
      brick.next = bricks;
      bricks = brick;
    }
  }
  
  public Level getNextLevel() {
    return new Red();
  }
  
}

class Red
extends Level {
  
  Red() {
    super(255, 0, 0);
    
    paddle.r = 0;
    paddle.g = 255;
    paddle.b = 255;
    
    paddle.scoreR = 255;
    paddle.scoreG = 0;
    paddle.scoreB = 0;
    
    ball.r = 0;
    ball.g = 255;
    ball.b = 255;
    
    ball.livesR = 255;
    ball.livesG = 255;
    ball.livesB = 0;
    
    for (int i = 0; i < 13; i++) {
      Brick brick = new Brick(4 * (i + 1), 0, 255, 255);
      
      brick.worthR = 255;
      brick.worthG = 0;
      brick.worthB = 0;
      
      brick.next = bricks;
      bricks = brick;
    }
  }
  
  public Level getNextLevel() {
    return new Black();
  }
  
}

class Black
extends Level {
  
  Black() {
    super(0, 0, 0);
    
    paddle.r = 255;
    paddle.g = 255;
    paddle.b = 255;
    
    paddle.scoreR = 0;
    paddle.scoreG = 0;
    paddle.scoreB = 0;
    
    ball.r = 255;
    ball.g = 255;
    ball.b = 255;
    
    ball.livesR = 0;
    ball.livesG = 0;
    ball.livesB = 0;
    
    for (int i = 0; i < 15; i++) {
      Brick brick = new Brick(5 * (i + 1), 255, 255, 255);
      
      brick.worthR = 0;
      brick.worthG = 0;
      brick.worthB = 0;
      
      brick.next = bricks;
      bricks = brick;
    }
  }
  
  public Level getNextLevel() {
    return new Purple();
  }
  
}

class Purple
extends Level {
  
  Purple() {
    super(255, 0, 255);
    
    paddle.r = 0;
    paddle.g = 255;
    paddle.b = 0;
    
    paddle.scoreR = 255;
    paddle.scoreG = 0;
    paddle.scoreB = 255;
    
    ball.r = 0;
    ball.g = 255;
    ball.b = 0;
    
    ball.livesR = 255;
    ball.livesG = 0;
    ball.livesB = 255;
    
    for (int i = 0; i < 17; i++) {
      Brick brick = new Brick(5 * (i + 1), 0, 255, 0);
      
      brick.worthR = 0;
      brick.worthG = 0;
      brick.worthB = 0;
      
      brick.next = bricks;
      bricks = brick;
    }
  }
  
  public Level getNextLevel() {
    return new Crazy();
  }
  
}

class Crazy
extends Level {
  
  int dr = 1;
  int dg = 1;
  int db = 1;
  
  Crazy() {
    super((int)random(255), (int)random(255), (int)random(255));
    
    for (int i = 0; i < 20; i++) {
      Brick brick = new RainbowBrick(10 * (i + 1), (int)random(255), (int)random(255), (int)random(255));
      
      brick.worthR = (brick.r + 255) % 255;
      brick.worthG = (brick.g + 255) % 255;
      brick.worthB = (brick.b + 255) % 255;
      
      brick.next = bricks;
      bricks = brick;
    }
  }
  
  public void draw() {
    if (r <= 0) dr = (int)abs(dr);
    if (r >= 255) dr = -(int)abs(dr);
    if (g <= 0) dg = (int)abs(dg);
    if (g >= 255) dg = -(int)abs(dg);
    if (b <= 0) db = (int)abs(db);
    if (b >= 255) db = -(int)abs(db);
    
    r += dr;
    b += db;
    g += dg;
    
    ball.r = paddle.r = (int)random(255);
    ball.g = paddle.g = (int)random(255);
    ball.b = paddle.b = (int)random(255);
    
    paddle.scoreR = (int)random(255);
    paddle.scoreG = (int)random(255);
    paddle.scoreB = (int)random(255);
    
    ball.livesR = (int)random(255);
    ball.livesG = (int)random(255);
    ball.livesB = (int)random(255);
    
    super.draw();
  }
  
  public Level getNextLevel() {
    score = 0;
    return new White();
  }
  
}

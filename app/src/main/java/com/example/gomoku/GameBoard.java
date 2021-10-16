package com.example.gomoku;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

class GameBoard extends View {
    private final int boardColor ;

    private final Paint paint = new Paint();

    private int cellSize = getWidth()/8 ;

    private GameEngine gameEngine = new GameEngine() ;

    private int ROW=8, COLUMN=8 ;

    public GameBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.GameBoard, 0, 0) ;

        try{
            boardColor = a.getInteger(R.styleable.GameBoard_boardLineColor, 0) ;
        }finally {
            a.recycle();
        }
    }

    int flag = 0 ;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        int action = event.getAction();

        if(flag==1){
            if(action == MotionEvent.ACTION_DOWN){
                flag = -1 ;
                int row = (int) Math.ceil(y/cellSize) ;
                int col = (int) Math.ceil(x/cellSize) ;

                if(gameEngine.getGameBoard()[row-1][col-1]==0 && gameEngine.turn=="human"){
                    if(gameEngine.humanTurn(row-1, col-1)){
                        gameEngine.changePlayer() ;
                        invalidate();
                    }
                    else{
                        gameEngine.turn = "gameOver" ;
                    }
                }
            }
            else if(action == MotionEvent.ACTION_UP){
                AI_Turn();
                flag = 1;
            }
        }

        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int dimensions = Math.min(getMeasuredWidth(), getMeasuredHeight()) ;
        cellSize = dimensions/COLUMN ;
        setMeasuredDimension(dimensions, dimensions);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        paint.setStyle(Paint.Style.STROKE) ;
        paint.setAntiAlias(true);

        drawGameBoard(canvas) ;
        drawMarkers(canvas);
    }

    private void drawGameBoard(Canvas canvas){
        paint.setColor(boardColor);
        paint.setStrokeWidth(3);
        paint.setStyle(Paint.Style.FILL) ;

        for(int r=0; r<ROW; r++){
            for(int c=0; c<COLUMN; c++){
                canvas.drawOval((int)(cellSize*c+cellSize*0.2), (int)(cellSize*r+cellSize*0.2), (int)(cellSize*c+cellSize*0.8), (int)(cellSize*r+cellSize*0.8), paint);
            }
        }
    }

    private void drawMarkers(Canvas canvas){

        for(int r=0; r<ROW; r++){
            for(int c=0; c<COLUMN; c++){

                if(gameEngine.getGameBoard()[r][c]==6)
                    drawPlayer1Circle(canvas, r, c);

                else if(gameEngine.getGameBoard()[r][c]==7){
                    drawPlayer2Circle(canvas, r, c);
                }
            }
        }

        if(gameEngine.gameOver(gameEngine.getGameBoard(), "main")){
            System.out.println("================>>>>>>" + gameEngine.gameOverLine[0] + "\t" + gameEngine.gameOverLine[1] + "\t"+ gameEngine.gameOverLine[2] + "\t" + gameEngine.gameOverLine[3] + "\t");
            drawGameOverLine(canvas);
        }
    }

    private void AI_Turn(){
        if(gameEngine.turn=="AI"){
            gameEngine.AI_Turn() ;
            gameEngine.changePlayer() ;
            invalidate();
        }
    }

    private void drawGameOverLine(Canvas canvas){
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(10);
        gameEngine.turn = "gameOver" ;
        canvas.drawLine((int)(cellSize*gameEngine.gameOverLine[1]+ cellSize*0.5), (int)(cellSize*gameEngine.gameOverLine[0] + cellSize*0.5), (int)(cellSize*gameEngine.gameOverLine[3] + cellSize*0.5), (int)(cellSize*gameEngine.gameOverLine[2] + cellSize*0.5), paint);
    }

    private void drawPlayer1Circle(Canvas canvas, int row, int column){
        paint.setColor(Color.RED);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.FILL) ;

        canvas.drawOval((int)(cellSize*column+cellSize*0.3), (int)(cellSize*row+cellSize*0.3), (int)(cellSize*column+cellSize*0.7), (int)(cellSize*row+cellSize*0.7), paint);
    }

    private void drawPlayer2Circle(Canvas canvas, int row, int column){
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.FILL) ;
        canvas.drawOval((int)(cellSize*column+cellSize*0.3), (int)(cellSize*row+cellSize*0.3), (int)(cellSize*column+cellSize*0.7), (int)(cellSize*row+cellSize*0.7), paint);
    }

    public void resetGame(){
        gameEngine = new GameEngine();
        invalidate();
    }
}

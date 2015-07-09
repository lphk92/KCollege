package luchoe.diningphilosophers;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class PhilosopherView extends View implements HungerListener
{
    public static final int RADIUS = 30;
    public static final int BORDER = 3;
    
    private Paint paint;
    private float x;
    private float y;
    
    private Philosopher philosopher;
    
    public PhilosopherView(Context context)
    {
        super(context);
        this.paint = new Paint();
    }
    
    public PhilosopherView(Context context, AttributeSet attrs)
    {
        this(context, attrs, (float)RADIUS+BORDER, (float)RADIUS+BORDER );
    }
    
    public PhilosopherView(Context context, AttributeSet attrs, float x, float y)
    {
        super(context);
        this.x = x;
        this.y = y;
        this.paint = new Paint();
    }
    
    @Override
    public void onHunger()
    {
        Log.i("hunger", "Hunger received by view for philosopher " + this.philosopher.getId());
        this.invalidate();
    }
    
    @Override
    public void onBeginMeal()
    {
        Log.i("hunger", "BeginMeal received by view for philosopher " + this.philosopher.getId());
        this.invalidate();
    }
    
    @Override
    public void onFull()
    {
        Log.i("hunger", "Full received by view for philosopher " + this.philosopher.getId());
        this.invalidate();
    }
    
    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        
        // Draw the border
        this.paint.setColor(Color.BLACK);
        canvas.drawCircle(x, y, RADIUS + BORDER, this.paint);
        
        // Update the color
        switch(this.philosopher.getState())
        {
            case THINKING: this.paint.setColor(Color.BLUE); 
                break;
            case EATING: this.paint.setColor(Color.GREEN); 
                break;
            case HUNGRY: this.paint.setColor(Color.RED);
                break;
        }
        
        // Then draw the philosopher on top of it
        canvas.drawCircle(x, y, RADIUS, this.paint);
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        //TODO: Implement the necessary changes when philosopher is touched
        if (event.getAction() == MotionEvent.ACTION_DOWN)
        {
            float eventX = event.getX();
            float eventY = event.getY();
            Log.i("poke", "A poke was executed at " + eventX + ", " + eventY);
            if ((eventX >= this.x - RADIUS && eventX <= this.x + RADIUS) &&
                (eventY >= this.y - RADIUS && eventY <= this.y + RADIUS))
            {
                MainActivity.monitor.pickup(philosopher.getId());
                invalidate();
                
                Toast.makeText(this.getContext(), "You poked a philosopher!", Toast.LENGTH_SHORT).show();
                Log.i("phil", "A philosopher was poked.");
            }
        }
        return true;
    }
    
    public static PhilosopherView spawn(Context context, int x, int y, Philosopher philosopher)
    {
        return spawn(context, (float)x, (float)y, philosopher);
    }
    
    public static PhilosopherView spawn(Context context, double x, double y, Philosopher philosopher)
    {
        return spawn(context, (float)x, (float)y, philosopher);
    }
    
    public static PhilosopherView spawn(Context context, float x, float y, Philosopher philosopher)
    {
        int intX = (int)Math.ceil((double)x);
        int intY = (int)Math.ceil((double)y);
        
        PhilosopherView p = new PhilosopherView(context);
        p.setPhilosopher(philosopher);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(intX+RADIUS+BORDER, intY+RADIUS+BORDER);
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        params.setMargins(intX, intY, 0, 0);
        p.setLayoutParams(params);
        p.setPosition(x, y);
        
        return p;
    }
    
    // Getters and setters
    public void setPhilosopher(Philosopher philosopher) 
    { 
        this.philosopher = philosopher;
        this.philosopher.setHungerListener(this);
    }

    public Philosopher getPhilosopher()
    {
        return this.philosopher;
    }

    public void setX(float x)
    {
        this.x = x;
    }

    public void setY(float y)
    {
        this.y = y;
    }

    public void setPosition(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    public float getX()
    {
        return this.x;
    }

    public float getY()
    {
        return this.y;
    }
}
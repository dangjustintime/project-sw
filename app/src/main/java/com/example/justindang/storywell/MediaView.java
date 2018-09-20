package com.example.justindang.storywell;

import android.Manifest;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

public class MediaView extends AppCompatImageView {
  private static final int INVALID_POINTER_ID = -1;

  // member data
  private Drawable image;
  private ScaleGestureDetector scaleGestureDetector;
  private float scaleFactor = 1.f;
  private float posX;
  private float posY;
  private float lastTouchX;
  private float lastTouchY;
  private int activePointerId;

  // constructor
  public MediaView(Context context) {
    super(context);
    scaleGestureDetector = new ScaleGestureDetector(context, new ScaleListener());
  }

  // scalelistener
  private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
    @Override
    public boolean onScale(ScaleGestureDetector detector) {
      scaleFactor *= detector.getScaleFactor();

      // zoom limits for image
      scaleFactor = Math.max(1.f, Math.min(scaleFactor, 1.5f));

      invalidate();
      return true;
    }
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    scaleGestureDetector.onTouchEvent(event);

    final int action = event.getAction();
    switch (action & MotionEvent.ACTION_MASK) {
      case MotionEvent.ACTION_DOWN: {
        final float x = event.getX();
        final float y = event.getY();

        lastTouchX = x;
        lastTouchY = y;
        activePointerId = event.getPointerId(0);
        break;
      }
      case MotionEvent.ACTION_MOVE: {
        final int pointerIndex = event.findPointerIndex(activePointerId);
        final float x = event.getX(pointerIndex);
        final float y = event.getY(pointerIndex);

        // only move if the ScaleGestureDetector isn't processing a gesture
        if (!scaleGestureDetector.isInProgress()) {
          final float dx = x - lastTouchX;
          final float dy = y - lastTouchY;

          posX += dx;
          posY += dy;

          invalidate();
        }

        lastTouchX = x;
        lastTouchY = y;

        break;
      }
      case MotionEvent.ACTION_UP: {
        activePointerId = INVALID_POINTER_ID;
        break;
      }
      case MotionEvent.ACTION_CANCEL: {
        activePointerId = INVALID_POINTER_ID;
      }
      case MotionEvent.ACTION_POINTER_UP: {
        final int pointerIndex = (event.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK)
                >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
        final int pointerId = event.getPointerId(pointerIndex);

        if (pointerId == activePointerId) {
          // this was the active pointer going up
          // choose a new active pointer and adjust accordingly
          final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
          lastTouchX = event.getX(newPointerIndex);
          lastTouchY = event.getY(newPointerIndex);
          activePointerId = event.getPointerId(newPointerIndex);
        }
        break;
      }
    }

    return true;
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    canvas.save();
    canvas.translate(posX, posY);
    canvas.scale(scaleFactor, scaleFactor);
    image.draw(canvas);
    canvas.restore();
  }

}

package lerner.ethan.kingscamp;

import android.annotation.SuppressLint;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class MapFragment extends Fragment {
    ImageView imageView;
    Matrix matrix;
    Matrix defMatrix = new Matrix();
    Matrix savedMatrix = new Matrix();
    PointF startPoint = new PointF();
    PointF midPoint = new PointF();
    float oldDist = 1f;
    static final int NONE = 0;
    static final int DRAG = 1;
    static final int ZOOM = 2;
    int mode = NONE;

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.map_layout, null);

        defMatrix.setTranslate(0f, 600f);
        defMatrix.setScale(.3f, .3f, 0, 600);
        matrix = new Matrix(defMatrix);
        imageView = view.findViewById(R.id.imageView2);
        imageView.setImageMatrix(defMatrix);

        imageView.setOnTouchListener(new View.OnTouchListener() {
            private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onDoubleTap(MotionEvent e) {
                    matrix = new Matrix(defMatrix);
                    Log.d("TEST", "onDoubleTap");
                    return super.onDoubleTap(e);
                }
            });
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                ImageView view = (ImageView) v;
                float[] values = new float[9];
                matrix.getValues(values);

                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:

                        savedMatrix.set(matrix);
                        startPoint.set(event.getX(), event.getY());
                        mode = DRAG;
                        break;

                    case MotionEvent.ACTION_POINTER_DOWN:

                        oldDist = spacing(event);

                        if (oldDist > 10f) {
                            savedMatrix.set(matrix);
                            midPoint(midPoint, event);
                            mode = ZOOM;
                        }
                        break;

                    case MotionEvent.ACTION_UP:

                    case MotionEvent.ACTION_POINTER_UP:
                        mode = NONE;

                        break;

                    case MotionEvent.ACTION_MOVE:
                        if (mode == DRAG) {
                            matrix.set(savedMatrix);
                            matrix.postTranslate(event.getX() - startPoint.x, event.getY() - startPoint.y);
                        } else if (mode == ZOOM) {
                            float newDist = spacing(event);

                            if (newDist > 10f) {
                                matrix.set(savedMatrix);
                                float scale = newDist / oldDist;

                                if ((values[0] > .3f && scale < 1f) || (scale > 1f && values[0] < 2f))
                                    matrix.postScale(scale, scale, midPoint.x, midPoint.y);
                            }
                        }
                        break;
                }
                Log.d("VALUES", "TRANS X: " + values[2]);
                Log.d("VALUES", "TRANS Y: " + values[5]);
                Log.d("VALUES", "SCALE X: " + values[0]);
                Log.d("VALUES", "SCALE Y: " + values[4]);
                if (values[0] < .3) {
                    values[0] = .3f;
                    values[4] = .3f;
                    matrix.setValues(values);
                } else if (values[0] > 2f) {
                    values[0] = 2f;
                    values[4] = 2f;
                    matrix.setValues(values);
                }

                gestureDetector.onTouchEvent(event);
                view.setImageMatrix(matrix);

                return true;
            }

            private float spacing(MotionEvent event) {
                float x = event.getX(0) - event.getX(1);
                float y = event.getY(0) - event.getY(1);
                return (float) Math.sqrt(x * x + y * y);
            }

            private void midPoint(PointF point, MotionEvent event) {
                float x = event.getX(0) + event.getX(1);
                float y = event.getY(0) + event.getY(1);
                point.set(x / 2, y / 2);
            }
        });
        return view;
    }
}

package com.dejun.commonsdk.wight;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;

/**
 * Author:DoctorWei
 * Time:2018/4/10 11:35
 * Description:监听EditText的删除键
 * email:1348172474@qq.com
 */

public class DeleteEditText extends android.support.v7.widget.AppCompatEditText{
    private static final String TAG = "NotesEditText";

    private BackKeyListener mBackKeyListener;

    public DeleteEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public DeleteEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DeleteEditText(Context context) {
        super(context);
    }

    public void setBackKeyListener(BackKeyListener backKeyListener){
        mBackKeyListener = backKeyListener;
    }


    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        return new NotesInputConnection(super.onCreateInputConnection(outAttrs),
                true);
    }

    private class NotesInputConnection extends InputConnectionWrapper {

        public NotesInputConnection(InputConnection target, boolean mutable) {
            super(target, mutable);
        }

        @Override
        public boolean sendKeyEvent(KeyEvent event) {
            if (event.getAction() == KeyEvent.ACTION_DOWN
                    && event.getKeyCode() == KeyEvent.KEYCODE_DEL) {
                if(null != mBackKeyListener){
                    mBackKeyListener.onBackPressedDown();
                }
                // Un-comment if you wish to cancel the backspace:
                // return false;
            }
            return super.sendKeyEvent(event);
        }

        @Override
        public boolean deleteSurroundingText(int beforeLength, int afterLength) {
            // magic: in latest Android, deleteSurroundingText(1, 0) will be called for backspace
            if (beforeLength == 1 && afterLength == 0) {
                Log.e(TAG, "### deleteSurroundingText::");
                // backspace
                return sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL))
                        && sendKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_DEL));
            }

            return super.deleteSurroundingText(beforeLength, afterLength);
        }


    }


    public interface BackKeyListener{
        void onBackPressedDown();
    }
}

## View的Touch事件分发

### OnTouchListener、OnTouchEvent、OnClickLintener三个都有的情况下:

#### 1.OnTouchListener return false

`OnTouchListener.DOWN` -> `OnTouchEvent.DOWN` -> `OnTouchListener.MOVE`
-> `OnTouchEvent.MOVE` -> `OnTouchListener.UP` -> `OnTouchEvent.UP` ->
`OnTouchListener`
#### 2.OnTouchListener return true
`OnTouchListener.DOWN` -> `OnTouchListener.MOVE` -> `OnTouchListener.UP`

### dispatchTouchEvent
```
boolean result = false;

ListenerInfo li = mListenerInfo;

  if (li != null && li.mOnTouchListener != null
                    && (mViewFlags & ENABLED_MASK) == ENABLED
                    && li.mOnTouchListener.onTouch(this, event)) { //OnTouchListener 返回false result = false 反之 result = true
                result = true;
            }
            
            //如果result = false 就会执行 onTouchEvent 
            if (!result && onTouchEvent(event)) {
                result = true;
            }

```
### View的点击事件
View 的onTouchEvent -> case MotionEvent.ACTION_UP: -> performClick() -> li.mOnClickListener.onClick(this);

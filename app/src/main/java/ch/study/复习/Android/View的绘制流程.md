#### 一、在绘制View之前顺便提下setContentView(int resId)的一个过程

setContentView(int resId)是将Activity的布局文件显示在屏幕上，这个过程可以简述如下：
1. 在Activity.setContentView(int resId)中调用PhoneWindow.setContentView(int resId)，在其中，如果是第一次调用，那么需要安装DecorView和初始化mContentParent；
否则，清空mContentParent中的内容。然后将资源文件通过LayoutInflater的inflate方法来将其转换为View树，并将其添加到mContentParent中。
2. 在将资源文件添加到mContentParent中之前，还有一些布局步骤：（由DecorView来完成）确定窗口类型，既是否有标题栏等等；

选择窗口风格对应的布局文件

DecorView将该布局文件添加进去，因为DecorView是根视图；

DecorView获取id为content的FrameLayout传给mContentParent

3.通过回调Activity的handleResumeActivity()来调用Activity的makeVisiable()方法来显示创建的视图族。


#### 二、View的绘制过程：

`Activity`的启动流程里面  
ActivityThead:  
`performLaunchActivity`->`callActivityOnCreate`->`activity.performCreate`->`onCreate`

`handleResumeActivity`->`performResumeActivity` ->`r.activity.performResume(r.startsNotResumed, reason)` ->`mInstrumentation.callActivityOnResume`->`activity.onResume()`

`wm.addView(decor, l)`把DecorView添加到 WindowManager ,
View的绘制流程在这个时候开始`onMeasure`、`onLayout`、`onDraw`

`wm.addView(decor, l)`调用流程：
ActivityThread:`a.getWindowManager()` -> Activity:`mWindow.getWindowManager()` -> `((WindowManagerImpl)wm).createLocalWindowManager(this)` -> `WindowManagerImpl.addView()` ->
`WindowManagerGlobal.addView()`

```
ViewRootImpl:->

  root = new ViewRootImpl(view.getContext(), display);

            view.setLayoutParams(wparams);

            mViews.add(view);
            mRoots.add(root);
            mParams.add(wparams);
            
            root.setView(view, wparams, panelParentView, userId);//会调用requestLayout();
```

`requestLayout()` -> `scheduleTraversals()` -> TraversalRunnable:`doTraversal()` -> `performTraversals()`

```
private void performTraversals() {
    ...
    performMeasure(childWidthMeasureSpec, childHeightMeasureSpec);
    ...
    performLayout(lp, mWidth, mHeight);
    ...
    performDraw();
}
```

#### performMeasure(childWidthMeasureSpec, childHeightMeasureSpec):

->`mView.measure(childWidthMeasureSpec, childHeightMeasureSpec);`

->`onMeasure(widthMeasureSpec, heightMeasureSpec)`测量开始

```
比如 ViewGroup 是 LinearLayout 会调用 LinearLayout 的：

->`onMeasure(widthMeasureSpec, heightMeasureSpec)`

->`measureVertical(widthMeasureSpec,heightMeasureSpec)`

->`measureChildBeforeLayout(child,i,widthMeasureSpec,0,heightMeasureSpec,usedHeight)`

->`measureChildWithMargins(child, widthMeasureSpec,totalWidth,heightMeasureSpec, totalHeight)`

->`child.measure(childWidthMeasureSpec,childHeightMeasureSpec)`
```

->View:`onMeasure(widthMeasureSpec,heightMeasureSpec)` -> `setMeasuredDimension()` -> `setMeasuredDimensionRaw(measuredWidth,
measuredHeight)`这个时候布局才真正指定宽度和高度`mMeasuredWidth`、`mMeasuredHeight`才开始有值

#### performLayout(lp, mWidth, mHeight):
->`host.layout(0, 0, host.getMeasuredWidth(), host.getMeasuredHeight())`

->View:`layout(int l, int t, int r, int b)`

->onLayout(changed, l, t, r, b)

#### performDraw():
->`boolean canUseAsync = draw(fullRedrawNeeded)`

->`drawSoftware(surface, mAttachInfo, xOffset, yOffset,scalingRequired,dirty, surfaceInsets)`

->`mView.draw(canvas)`

-->  View:`draw(Canvas canvas)`:

->`drawBackground(canvas)`画背景

->`onDraw(canvas)`画自己 ViewGroup默认情况下不会调

->`dispatchDraw(canvas)`画子View 不断循环调用子View的draw();


#### View的绘制流程面试时候可以这样答：
1. `performMeasure()`:用于指定和测量布局中所有控件的宽高

* 对于 ViewGroup ,先去测量里面的子View,根据子View的宽高来计算和指定自己的宽高

* 对于View,它的宽高是由自己和父布局决定的

2. `performLayout()`:用于摆放子布局,for循环所有子View,用`child.layout()`摆放子View
3. `performDraw()`:用于绘制自己还有子View

* 对于 ViewGroup 首先绘制自己的背景,for循环绘制子View调用子view的draw()方法

* 对于 View 绘制自己的背景，绘制自己的内容(如：TextView)
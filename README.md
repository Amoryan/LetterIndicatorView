# 使用方法
请使用1.0.1
```gradle
implementation 'com.fxyan.widget:letterIndicatorView:1.0.1'
```
# 示例
## 微信通讯录
效果图如下
![001](images/001.gif)
## 城市选择列表
效果图如下
# LetterIndicatorView
## 属性说明
1. livIndicatorBgColor指示器背景色；
2. livIndicatorItemWidth指示器宽度；
3. livIndicatorItemHeight指示器高度；
4. livIndicatorTextSize指示器文本大小；
5. livIndicatorSelectedTextColor指示器选中文本颜色；
6. livIndicatorSelectedBgColor指示器选中文本背景色；
7. livIndicatorSelectedBgRadius指示器选中文本背景半径(目前只有原型的背景)；
8. livIndicatorUnSelectTextColor指示器未选中文本颜色；
9. livZoomUpIndicatorTextSize放大部分文本大小；
10. livZoomUpIndicatorTextColor放大部分文本颜色；
11. livZoomUpIndicatorBgColor放大部分背景颜色；
12. livZoomUpIndicatorBgRadius放大部分圆的半径；
13. livZoomUpIndicatorMargin放大部分和指示器之间的距离；
14. livHeaderDrawable是否有列表头，列表头对应图标。

```xml
<com.fxyan.widget.LetterIndicatorView
    android:id="@+id/indicatorView"
    android:layout_width="wrap_content"
    android:layout_height="0dp"
    app:layout_constraintBottom_toBottomOf="@+id/recyclerView"
    app:layout_constraintRight_toRightOf="@+id/recyclerView"
    app:layout_constraintTop_toTopOf="@+id/recyclerView"
    app:livHeaderDrawable="@mipmap/ic_search"
    app:livIndicatorBgColor="@android:color/transparent"
    app:livIndicatorItemHeight="15dp"
    app:livIndicatorItemWidth="20dp"
    app:livIndicatorSelectedBgColor="#43c561"
    app:livIndicatorSelectedBgRadius="8dp"
    app:livIndicatorSelectedTextColor="@android:color/white"
    app:livIndicatorTextSize="10sp"
    app:livIndicatorUnSelectTextColor="#646464"
    app:livZoomUpIndicatorBgColor="#20000000"
    app:livZoomUpIndicatorBgRadius="20dp"
    app:livZoomUpIndicatorMargin="5dp"
    app:livZoomUpIndicatorTextColor="@android:color/white"
    app:livZoomUpIndicatorTextSize="20sp" />
```

## 回调监听
可以给LetterIndicatorView设置OnIndicatorIndexChangeListener，index的取值范围大于等于-1。
```java
public interface OnIndicatorIndexChangeListener {
    void onIndicatorIndexChanged(int index);
}
```
## 和RecyclerView联动
默认配置了一个简单的ItemDecoration，如果刚好可以满足需求，你可以直接通过attachToRecyclerView()关联RecyclerView，对于ItemDecoration的配置可以通过DecorationConfig.Builder来进行构造
### DecorationConfig
如果你使用attachToRecyclerView()，则需要配置一个DecorationConfig。
```java
DecorationConfig config = new DecorationConfig.Builder()
        .setLine(1, Color.parseColor("#ebebeb"))
        .setSelectedTextColor(0x33, 0x33, 0x33)
        .setUnSelectTextColor(0x64, 0x64, 0x64)
        .setSelectedBgColor(0xff, 0xff, 0xff)
        .setUnSelectBgColor(0xf8, 0xf9, 0xfa)
        .setTextXOffset(Tools.dp2px(this, 12))
        .setTextSize(Tools.dp2px(this, 14))
        .setHeight((int) Tools.dp2px(this, 30))
        .build();
indicatorView.attachToRecyclerView(recyclerView, config, array);
```

属性说明
1. lineHeight分割线高度，通过setLine()设置；
2. lineColor分割线颜色，通过setLine()设置；
3. selectedTextColor，当前分组的文本颜色，这里将颜色拆分成R,G,B三原色，方便实现碰撞颜色的渐变效果，通过setSelectedTextColor()设置；
4. unSelectTextColor，其他分组的文本颜色，通过setUnSelectTextColor()设置；
5. selectedBgColor，当前分组的背景色，通过setSelectedBgColor()设置；
6. unSelectBgColor，其他分组的文本颜色，通过setUnSelectBgColor设置；
7. textSize，文本大小，通过setTextSize()设置；
8. textXOffset，文本x轴偏移量，通过setTextXOffset()设置；
9. height，分组高度，通过setHeight()设置。
### 自定义ItemDecoration
如果自带的Decoration不支持需求，可以自己编写Decoration
### 碰撞效果
碰撞效果在Decoration的onDrawOver()方法中
```java
View firstVisibleView = parent.getChildAt(0);
int firstVisibleViewIndex = parent.getChildAdapterPosition(firstVisibleView);
View secondVisibleView = parent.getChildAt(1);
int secondVisibleViewIndex = parent.getChildAdapterPosition(secondVisibleView);
float top = 0;
if (array.indexOfKey(secondVisibleViewIndex) >= 0
        && firstVisibleView.getBottom() <= config.getHeight()) {
    // 这里表示需要绘制碰撞效果，第二组的第一个元素刚好是RecyclerView的第二个子控件
    top = firstVisibleView.getBottom() - config.getHeight();
    // y轴偏移量
    float yOffset = top + config.getHeight() / 2 - (fontMetrics.bottom + fontMetrics.top) / 2;
    // ...绘制
}
```

### 颜色渐变
onDraw()方法中主要是处理Item上的指示器渐变
```java
public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
    super.onDraw(c, parent, state);
    for (int i = 0; i < parent.getChildCount(); i++) {
        View child = parent.getChildAt(i);
        int position = parent.getChildAdapterPosition(child);
        if (array.indexOfKey(position) >= 0) {
            String tmp = array.get(position);
            float xOffset = config.getTextXOffset();
            float yOffset = child.getTop() - config.getHeight() / 2 - (fontMetrics.bottom + fontMetrics.top) / 2;

            View firstVisibleView = parent.getChildAt(0);
            View secondVisibleView = parent.getChildAt(1);
            int secondVisibleViewIndex = parent.getChildAdapterPosition(secondVisibleView);

            int bgColor = Color.rgb(config.getUnSelectBgColorR(), config.getUnSelectBgColorG(), config.getUnSelectBgColorB());
            int textColor = Color.rgb(config.getUnSelectTextColorR(), config.getUnSelectTextColorG(), config.getUnSelectTextColorB());
            if (array.indexOfKey(secondVisibleViewIndex) >= 0 && secondVisibleViewIndex == position
                    && firstVisibleView.getBottom() <= config.getHeight()) {
                // 这里是计算碰撞的时候Item上的文本和背景的渐变颜色值
                int currentTextR = config.getUnSelectTextColorR();
                int currentTextG = config.getUnSelectTextColorG();
                int currentTextB = config.getUnSelectTextColorB();
                int currentBgR = config.getUnSelectBgColorR();
                int currentBgG = config.getUnSelectBgColorG();
                int currentBgB = config.getUnSelectBgColorB();

                int endTextR = config.getSelectedTextColorR();
                int endTextG = config.getSelectedTextColorG();
                int endTextB = config.getSelectedTextColorB();
                int endBgR = config.getSelectedBgColorR();
                int endBgG = config.getSelectedBgColorG();
                int endBgB = config.getSelectedBgColorB();
                // 这里是计算渐变百分比
                float percent = 1f * (config.getHeight() - firstVisibleView.getBottom()) / config.getHeight();
                // text
                currentTextR = (int) (currentTextR + (endTextR - currentTextR) * percent);
                currentTextG = (int) (currentTextG + (endTextG - currentTextG) * percent);
                currentTextB = (int) (currentTextB + (endTextB - currentTextB) * percent);
                // bg
                currentBgR = (int) (currentBgR + (endBgR - currentBgR) * percent);
                currentBgG = (int) (currentBgG + (endBgG - currentBgG) * percent);
                currentBgB = (int) (currentBgB + (endBgB - currentBgB) * percent);
                bgColor = Color.rgb(currentBgR, currentBgG, currentBgB);
                textColor = Color.rgb(currentTextR, currentTextG, currentTextB);
            }
            
            // ... 
            paint.setColor(bgColor);
            c.drawRect(0, child.getTop() - config.getHeight() + config.getLineHeight(), child.getRight(), child.getTop() - config.getLineHeight(), paint);
            paint.setColor(textColor);
            c.drawText(tmp, xOffset, yOffset, paint);
        }
    }
}
```

onDrawOver()方法中主要是处理覆盖物上的指示器渐变。
```java
public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
    super.onDrawOver(c, parent, state);

    View firstVisibleView = parent.getChildAt(0);
    int firstVisibleViewIndex = parent.getChildAdapterPosition(firstVisibleView);
    View secondVisibleView = parent.getChildAt(1);
    int secondVisibleViewIndex = parent.getChildAdapterPosition(secondVisibleView);

    // ...
    
    float top = 0;
    int currentTextR = config.getSelectedTextColorR();
    int currentTextG = config.getSelectedTextColorG();
    int currentTextB = config.getSelectedTextColorB();
    int currentBgR = config.getSelectedBgColorR();
    int currentBgG = config.getSelectedBgColorG();
    int currentBgB = config.getSelectedBgColorB();
    if (array.indexOfKey(secondVisibleViewIndex) >= 0
            && firstVisibleView.getBottom() <= config.getHeight()) {
        // 第一个可见的控件是该组最后一个控件，这里是计算覆盖物的文本和背景的颜色值
        top = firstVisibleView.getBottom() - config.getHeight();
        // text
        int endTextR = config.getUnSelectTextColorR();
        int endTextG = config.getUnSelectTextColorG();
        int endTextB = config.getUnSelectTextColorB();
        // bg
        int endBgR = config.getUnSelectBgColorR();
        int endBgG = config.getUnSelectBgColorG();
        int endBgB = config.getUnSelectBgColorB();
        // 这里是渐变百分比
        float percent = 1f * Math.abs(top) / config.getHeight();

        currentTextR = (int) (currentTextR + (endTextR - currentTextR) * percent);
        currentTextG = (int) (currentTextG + (endTextG - currentTextG) * percent);
        currentTextB = (int) (currentTextB + (endTextB - currentTextB) * percent);
        currentBgR = (int) (currentBgR + (endBgR - currentBgR) * percent);
        currentBgG = (int) (currentBgG + (endBgG - currentBgG) * percent);
        currentBgB = (int) (currentBgB + (endBgB - currentBgB) * percent);
    }
    paint.setColor(Color.rgb(currentBgR, currentBgG, currentBgB));
    c.drawRect(0, top + config.getLineHeight(), parent.getWidth(), top + config.getHeight() - config.getLineHeight(), paint);
    float xOffset = config.getTextXOffset();
    float yOffset = top + config.getHeight() / 2 - (fontMetrics.bottom + fontMetrics.top) / 2;
    int color = Color.rgb(currentTextR, currentTextG, currentTextB);
    paint.setColor(color);
    c.drawText(tmp, xOffset, yOffset, paint);
}
```

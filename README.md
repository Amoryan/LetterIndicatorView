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
8. textXOffset，文本x轴偏移量，通过setTextSize()设置；
9. height，分组高度，通过setHeight()设置。

# BadgeView [![](https://jitpack.io/v/wong1988/BadgeView.svg)](https://jitpack.io/#wong1988/BadgeView)

 Android徽章View

 Step 1. Add it in your root build.gradle at the end of repositories:
```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```
Step 2. Add the dependency
```
dependencies {
    implementation 'com.github.wong1988:BadgeView:0.0.3'
}
```

## Method and Attribute

```
<!-- 最大数值 -->
<attr name="badge_max" format="enum">
    <!-- 超过99显示 99+ -->
    <enum name="MAX_99" value="0" />
    <!-- 超过999显示 999+ -->
    <enum name="MAX_999" value="1" />
</attr>

<!-- 样式 -->
<attr name="badge_style" format="enum">
    <!-- 显示文字 -->
    <enum name="normal" value="0" />
    <!-- 不显示文字，即圆点模式 -->
    <enum name="dot" value="1" />
</attr>
```

```
// 对应badge_style属性
setDotStyle(boolean dot)
// 对应badge_max属性
setBadgeMax(BadgeMax max)
```

## About

本质上就是一个TextView，您可以像使用TextView一样使用它。本控件自带一个默认的背景样式（红色实心，白色外边框），可自行设置setBackground()来自定义样式。

不同之处：
1. setVisibility(int visibility)方法会失效，此方法由BadgeView全面进行统一管理
2. setSingleLine(boolean singleLine)方法会强制为true，设置为单行
3. setGravity(int gravity)方法会强制为Gravity.CENTER，设置居中显示
4. 当文本长度为1时，paddingLeft与paddingRight将会失效，长度大于1时paddingLeft与paddingRight将会自动生效

注意：
1. 控件宽高尽量使用wrap_content，而大小使用textSize去控制
2. 如果setText()文本能正确格式化为long类型，会自动显示为符合数字类型的格式（小于 0 不显示 ，大于设置的最大值 显示为最大值），否则直接按照文本显示

## Change Log

0.0.3:

* 首个版本发布

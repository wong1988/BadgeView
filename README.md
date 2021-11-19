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

本质上就是一个TextView，您可以像使用TextView一样使用它。
不同之处：
1. setVisibility(int visibility)方法会失效，此方法由BadgeView全面进行统一管理
2. setSingleLine(boolean singleLine)方法会强制设置为单行
3. setGravity(int gravity)方法会强制设置居中显示


## Change Log

0.0.3:

* 首个版本发布
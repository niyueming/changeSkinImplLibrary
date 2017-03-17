# changeSkinImplLibrary

这只是个Android Studio的Module，不能独立运行。

 依赖[changeSkinLibrary](https://github.com/niyueming/changeSkinLibrary.git)


布局文件中添加支持，主要依赖于tag属性：

例如：

``` 
<TextView
         android:layout_width="wrap_content"
         android:layout_height="wrap_content"
         android:tag="skin:item_text_color:textColor"
         android:text="@string/hello_world"
         android:textColor="@color/item_text_color"/>
```


tag属性分为3部分组成：

 - skin
 - 资源的名称，即插件包中资源的名称，需要与当前app内使用的资源名称一致。
 - 支持的属性，目前支持src,background,textColor,支持扩展。src和background是R.drawable/mipmap.*

3部分，必须以:分隔拼接。

简言之：如果你哪个View需要换肤，就添加tag属性，tag值按照上述方式设置即可。

上面的换肤过程都是对Activity的View树做遍历换肤操作的，树根是：

`activity.findViewById(android.R.id.content);`

所有不在这颗树内的View都不能换肤，哪些View不在换肤范围呢？ 
Dialog的View、popWindow的View、悬浮窗(WindowManager上直接加View)，目前这三类View要换肤都应该使用特定View的换肤管理模块。 
需要注意的是：Dialog的交互具有排他型，通常在换肤操作时是不展示的，所以一般可以在show接口调用时做换肤，而不使用IWindowViewManager。 
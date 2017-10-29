## Android 应用友链
Android 圈其实很多开发者开发了很多优秀的应用，不乏非常有价值的产品，国内生态又特别不好，Android 个人开发者真是非常艰难。  

Android 应用友链 计划，做 Android 开发者之间的应用友情链接，抱团共赢未来。  

再小的个体，也是力量，欢迎个人开发者的应用加入。
## _应用参与条件_
1. 已有的功能是完备的；
2. 足够稳定；
3. 界面良好；

## _如何加入_
### 第零步、统一名称 『Android 应用友链』    
1. 中文全名：Android 应用友链  
2. 中文简称：Android 友链  
3. 英文名称：Android Links  
### 第一步、接入 Android 应用友链

#### 通过SDK加入，更多姿势 参考[ about-page](https://github.com/android-links/about-page)
> 省时、省力，快捷接入

添加依赖 `build.gradle`:

```groovy
dependencies {
    compile 'me.drakeet.support:about:2.0.0'
    compile 'me.drakeet.multitype:multitype:3.3.3'
    // extension for loading our host Android Links data
    compile 'me.drakeet.support:about-extension:2.0.0'
}
```

添加主题，自带了ToolBar

```xml
    <style name="AppTheme.About" parent="Theme.AppCompat.Light.DarkActionBar">
        <item name="windowActionBar">false</item>
        <item name="windowNoTitle">true</item>
    </style>
```

创建 AboutActivity
```java
/**
 * @author drakeet
 */
@SuppressLint("SetTextI18n")
@SuppressWarnings("SpellCheckingInspection")
public class AboutActivity extends AbsAboutActivity implements OnRecommendedClickedListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setImageLoader(new PicassoImageLoader());
        setOnRecommendedClickedListener(this);
    }


    @Override
    protected void onCreateHeader(@NonNull ImageView icon, @NonNull TextView slogan, @NonNull TextView version) {
        icon.setImageResource(R.mipmap.ic_launcher);
        slogan.setText(R.string.app_name);
        version.setText("v" + BuildConfig.VERSION_NAME);
    }


    @Override
    protected void onItemsCreated(@NonNull Items items) {
        // Load more Recommended items from remote server asynchronously
        RecommendedLoaderDelegate.attach(this, items.size());
    }


    @Override
    public boolean onRecommendedClicked(@NonNull View itemView, @NonNull Recommended recommended) {
        if(recommended.openWithGooglePlay) {
            openMarket(this, recommended.packageName, recommended.downloadUrl);
        } else {
            openWithBrowser(this, recommended.downloadUrl);
        }
        return false;
    }


    private void openMarket(@NonNull Context context, @NonNull String targetPackage, @NonNull String defaultDownloadUrl) {
        try {
            Intent googlePlayIntent = context.getPackageManager().getLaunchIntentForPackage("com.android.vending");
            ComponentName comp = new ComponentName("com.android.vending", "com.google.android.finsky.activities.LaunchUrlHandlerActivity");
            // noinspection ConstantConditions
            googlePlayIntent.setComponent(comp);
            googlePlayIntent.setData(Uri.parse("market://details?id=" + targetPackage));
            context.startActivity(googlePlayIntent);
        } catch (Throwable e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(defaultDownloadUrl)));
            e.printStackTrace();
        }
    }

    public static void openWithBrowser(Context context, String url) {
        //在浏览器打开
        try {
            context.startActivity(Intent.createChooser(new Intent(Intent.ACTION_VIEW, Uri.parse(url)),
                    context.getString(R.string.chooser_browser)));
        } catch (ActivityNotFoundException e) {
            // nop
        }
    }

}
```
添加至AndroidManifest.xml
```xml
    <activity
        android:name=".AboutActivity"
        android:screenOrientation="portrait"
        android:theme="@style/AppTheme.About"/>
```


#### 通过API加入
> App已经有自己的框架和主题，适合API接入

##### 请求接口URL：[Android 应用友链 API 文档](https://www.zhaoj.in/read-4574.html)

> https://recommend.wetolink.com/api/v2/app_recommend/pull?limit=50&package_name=xx.oo

##### 参数：
> limit(可选)：请求条数    
offset(可选)：记录起始位置，用于分页    
package_name(可选)：请求发起APP的包名，统计数据    
order_type(可选)：0或不填为随机顺序返回，1为按照App请求排行返回    

##### 响应数据为List：
RecommendedResponse.java
```
/**
 * @author drakeet
 */
@Keep
public class RecommendedResponse {
    public int code;
    public List<Recommended> data;
}
```
Recommended.java
```
/**
 * @author drakeet
 */
@Keep
public class Recommended {

    public int id;
    public String appName;
    public String iconUrl;
    public String packageName;
    public String description;
    public String downloadUrl;
    public String createdTime;
    public String updatedTime;
    public double downloadSize;
    public boolean openWithGooglePlay;


    public Recommended() {}


    public Recommended(
        int id,
        @NonNull String appName,
        @NonNull String iconUrl,
        @NonNull String packageName,
        @NonNull String description,
        @NonNull String downloadUrl,
        @NonNull String createdTime,
        @NonNull String updatedTime,
        double downloadSize,
        boolean openWithGooglePlay) {
        this.id = id;
        this.appName = appName;
        this.iconUrl = iconUrl;
        this.packageName = packageName;
        this.description = description;
        this.downloadUrl = downloadUrl;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
        this.downloadSize = downloadSize;
        this.openWithGooglePlay = openWithGooglePlay;
    }
}
```
参考布局：item_recommended.xml
```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:orientation="vertical">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal">

        <ImageView
            android:id="@+id/icon"
            android:layout_width="48dp"
            android:layout_height="48dp"
            android:layout_marginRight="12dp"
            android:layout_marginEnd="12dp"
            tools:src="#000000"/>

        <LinearLayout
            android:layout_width="0dp"
            android:layout_height="48dp"
            android:layout_weight="1"
            android:orientation="vertical"
            android:gravity="center_vertical">

            <TextView
                android:id="@+id/name"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:textSize="16sp"
                tools:text="App Name"/>

            <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="wrap_content">

                <TextView
                    android:id="@+id/packageName"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    tools:text="Package Name"/>

                <TextView
                    android:id="@+id/size"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:layout_marginLeft="8dp"
                    android:layout_marginStart="8dp"
                    tools:text="Size"/>

            </LinearLayout>

        </LinearLayout>

    </LinearLayout>

    <TextView
        android:id="@+id/description"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="8dp"
        android:layout_marginBottom="4dp"
        tools:text="Description"/>

</LinearLayout>
```
### 第二步、应用信息
1. 图标文件或者URL
2. App名称
3. 包名(用于跳应用市场)
4. 描述(推荐理由)
5. 下载页面URL
6. 包大小
7. 是否发布在Google play

把应用信息发送到 [jdlingyu at gmail.com](mailto:jdlingyu@gmail.com)，会将应用添加『Android 应用友链』列表中。

ps:如果全部默认酷安的信息，直接发送酷安链接也可以。



### 『Android 应用友链』客户端：[下载Android 友链](https://www.coolapk.com/apk/164137)
1. 可以查看推荐榜，推荐应用的浏览次数
2. 可以查看加入『Android 应用友链』的应用列表
3. 可以查看加入『Android 绿色应用公约』的应用列表
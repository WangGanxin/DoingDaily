# DoingDaily
多元日报，资讯阅读客户端

多元化阅读+深度阅读，为用户提供有价值的信息流，这是『多元日报』的定位和理念，产品从0到1的过程，学习了很多，收获了很多，如无意外地将会持续维护下去，未来的日子会增加一些有意思的功能，敬请期待。

---

#Features
###1、Material Design设计风格

>Toolbar、Snackbar、RecycleView、SwipeRefreshLayout、Activity跳转动画

###2、MVP架构+单Activity多Fragment模式

>参考[Googole MVP Demo](https://github.com/googlesamples/android-architecture)加上自己的一些思考，搭建了一个属于自己的项目架构，虽然不一定很准确无误，但起码是我目前水平所能做到比较满意的了

项目结构如下所示：

![package_structure](/banner/package_structure.png)

- application :自定义的全局application类
- commom ：公共类库
	- constants ：常量类
	- data ： 数据源，包括本地和远程
	- network ：网络请求封装，使用Retrofit+rxJava
	- share ：分享管理类
	- utlis ：常用工具类
	- widgets ：自定义的View，如TabLayout、RowView
- framework ：全局框架，使用时必须继承相关基类，如BaseActivity、BaseFragment、RxBus
- module ：业务逻辑层，按照相关功能划分模块
- wxapi ：微信分享回调所必须的集成类

###3、首页仿知乎上下滑动隐藏菜单栏

滑动隐藏顶部Toolbar这种效果网上大多数Demo都可以看到，但隐藏底部的Tab就需要用到自定义Behavior了，注意这个Behavior是依赖于AppBarLayout的，当AppBarLayout里的Toolbar发生位移的时候底部的Tab也跟随着向下隐藏，在此附上自定义的TabBehavior:

```Java

public class TabBehavior extends  CoordinatorLayout.Behavior<View> {
    public TabBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof AppBarLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {

        float translationY = Math.abs(dependency.getTop());
        child.setTranslationY(translationY);
        return true;
    }
}

```

使用的时候引入这个Behavior即可

###4、封装底部Tab、常用RowView、PullRecycleView等

底部Tab封装成TabLayout,添加一个Tab几行代码搞定：

```Java

        ArrayList<TabLayout.Tab> tabs = new ArrayList<>();
        tabs.add(new TabLayout.Tab(R.drawable.ic_bottomtabbar_news, R.string.tab_news, NewsFragment.class));
        tabs.add(new TabLayout.Tab(R.drawable.ic_bottomtabbar_wechat, R.string.tab_wechat, WechatFragment.class));
        tabs.add(new TabLayout.Tab(R.drawable.ic_bottomtabbar_about, R.string.tab_about, AboutFragment.class));
        mTabLayout.setUpData(tabs, this);
        mTabLayout.setCurrentTab(0);

```


###5、集成第三方：社会化分享、检测更新、埋点统计

主要是用到了[友盟社会化分享](http://mobile.umeng.com/social)，[Bugly异常上报与应用升级](https://bugly.qq.com/v2/index)，[LeanCloud用户反馈](https://leancloud.cn/)这些SDK，基本没有什么大的技术含量，照着文档集成就Ok了

#Demo

运行效果图：

![doingdaily-demo](/banner/doingdaily.gif)

#Banner

![doingdaily-ad](/banner/ad.png)

ewre

![doingdaily-ad1](/banner/ad_01.png)
![doingdaily-ad2](/banner/ad_02.png)
![doingdaily-ad3](/banner/ad_03.png)
![doingdaily-ad4](/banner/ad_04.png)


#Download

- [Github](https://github.com/WangGanxin/DoingDaily/releases)
- [Bugly](http://beta.bugly.qq.com/doingdaily)
- [360Market](http://zhushou.360.cn/detail/index/soft_id/3709747)


#Todo

###V 1.1.0 (update 2017-02-23)
- [ ] 知乎频道
- [ ] 其他...

###V 1.0.0
- [x] ~~项目框架搭建（Material Design + MVP + ButterKnife + RxJava + Retrofit + Glide）~~
- [x] ~~主界面（新闻、微信热文）~~
- [x] ~~社会化分享~~
- [x] ~~在线更新~~
- [x] ~~埋点统计~~

# Contact Me

- QQ：445253393（注明来自GitHub）
- Email：mail@wangganxin.me

# License
   		Copyright 2016 DoingDaily of copyright 守望君

   		Licensed under the Apache License, Version 2.0 (the "License");
   		you may not use this file except in compliance with the License.
   		You may obtain a copy of the License at

       		http://www.apache.org/licenses/LICENSE-2.0

   		Unless required by applicable law or agreed to in writing, software
   		distributed under the License is distributed on an "AS IS" BASIS,
   		WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   		See the License for the specific language governing permissions and
   		limitations under the License.

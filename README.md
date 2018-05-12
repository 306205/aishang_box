# [aishang_box][project] #

### Copyright notice ###

我在网上写的文章、项目都可以转载，但请注明出处，这是我唯一的要求。当然纯我个人原创的成果被转载了，不注明出处也是没有关系的，但是由我转载或者借鉴了别人的成果的请注明他人的出处，算是对前辈们的一种尊重吧！

虽然我支持写禁止转载的作者，这是他们的成果，他们有这个权利，但我不觉得强行扭转用户习惯会有一个很好的结果。纯属个人的观点，没有特别的意思。可能我是一个版权意识很差的人吧，所以以前用了前辈们的文章、项目有很多都没有注明出处，实在是抱歉！有想起或看到的我都会逐一补回去。

从一开始，就没指望从我写的文章、项目上获得什么回报，一方面是为了自己以后能够快速的回忆起曾经做过的事情，避免重复造轮子做无意义的事，另一方面是为了锻炼下写文档、文字组织的能力和经验。如果在方便自己的同时，对你们也有很大帮助，自然是求之不得的事了。要是有人转载或使用了我的东西觉得有帮助想要打赏给我，多少都行哈，心里却很开心，被人认可总归是件令人愉悦的事情。

站在了前辈们的肩膀上，才能走得更远视野更广。前辈们写的文章、项目给我带来了很多知识和帮助，我没有理由不去努力，没有理由不让自己成长的更好。写出好的东西于人于己都是好的，但是由于本人自身视野和能力水平有限，错误或者不好的望多多指点交流。

项目中如有不同程度的参考借鉴前辈们的文章、项目会在下面注明出处的，纯属为了个人以后开发工作或者文档能力的方便。如有侵犯到您的合法权益，对您造成了困惑，请联系协商解决，望多多谅解哈！若您也有共同的兴趣交流技术上的问题加入交流群QQ： 548545202

感谢作者[tcking][author]、[Bilibili][author1] [ijkplayer][author]，本项目借鉴了[GiraffePlayer][url] [ijkplayer][url1]项目，项目一开始的灵感来源于[GiraffePlayer][url]项目，后期做纯粹做了视频播放器的界面的定制，基于[ijkplayer][url1]项目进行的播放器界面UI封装。

## Introduction ##

当前项目是基于[ijkplayer][url1]项目进行的播放器界面UI封装。
是一个适用于 Android 的 RTMP 直播播放 SDK，可高度定制化和二次开发。特色是同时支持 H.264 软编／硬编和 AAC 软编／硬编。主要是支持RIMP、HLS、MP4、M4A等视频格式的播放。

## Features ##

1. 基于ijkplayer封装的视频播放器界面,支持 RTMP , HLS (http & https) , MP4,M4A 等；
2. 可根据需求去定制部分界面样式；
3. 常用的手势操作左边上下亮度，右边上下声音，左右滑动播放进度调整；
4. 支持多种分辨率流的切换播放；
5. 播放出错尝试重连；
6. 界面裁剪显示样式；

## Screenshots ##

<img src="https://raw.githubusercontent.com/jjdxmashl/jjdxm_ijkplayer/master/screenshots/icon01.gif" width="300"> 
<img src="https://raw.githubusercontent.com/jjdxmashl/jjdxm_ijkplayer/master/screenshots/icon02.gif" width="300"> 
<img src="https://raw.githubusercontent.com/jjdxmashl/jjdxm_ijkplayer/master/screenshots/icon03.gif" width="300"> 
<img src="https://raw.githubusercontent.com/jjdxmashl/jjdxm_ijkplayer/master/screenshots/icon04.gif" width="300"> 
<img src="https://raw.githubusercontent.com/jjdxmashl/jjdxm_ijkplayer/master/screenshots/icon01.png" width="300"> 
<img src="https://raw.githubusercontent.com/jjdxmashl/jjdxm_ijkplayer/master/screenshots/icon02.png" width="300"> 
 
## Download ##

[demo apk下载][downapk]

Download or grab via Maven:

	<dependency>
	  <groupId>com.dou361.ijkplayer</groupId>
	  <artifactId>jjdxm-ijkplayer</artifactId>
	  <version>x.x.x</version>
	</dependency>

or Gradle:

	compile 'com.dou361.ijkplayer:jjdxm-ijkplayer:x.x.x'



jjdxm-ijkplayer requires at minimum Java 9 or Android 2.3.

[架包的打包引用以及冲突解决][jaraar]

## Proguard ##

根据你的混淆器配置和使用，您可能需要在你的proguard文件内配置以下内容：

	-keep com.dou361.ijkplayer.** {
    *;
	}


[AndroidStudio代码混淆注意的问题][minify]

## Get Started ##

#### step1: ####
依赖本项目类库

该项目是基于ijkplayer项目进行的视频UI的二次封装，目前只是默认在：

	compile 'com.dou361.ijkplayer:jjdxm-ijkplayer:1.0.5' 

中加入了以下依赖：

	compile 'tv.danmaku.ijk.media:ijkplayer-java:0.6.0'
    compile 'tv.danmaku.ijk.media:ijkplayer-armv7a:0.6.0'

如果你的项目中已经有依赖了v4或者v7包并且使用的版本不一样可能会造成冲突，可以类似下面的方式进行引入依赖

	compile('com.dou361.ijkplayer:jjdxm-ijkplayer:1.0.5') {
	    exclude group: 'com.android.support', module: 'appcompat-v7'
	}


如果要支持多种ABI类型的机型，可以根据需要添加以下依赖：

	# required, enough for most devices.
    compile 'tv.danmaku.ijk.media:ijkplayer-java:0.6.0'
    compile 'tv.danmaku.ijk.media:ijkplayer-armv7a:0.6.0'

    # Other ABIs: optional
    compile 'tv.danmaku.ijk.media:ijkplayer-armv5:0.6.0'
    compile 'tv.danmaku.ijk.media:ijkplayer-arm64:0.6.0'  //最小版本21
    compile 'tv.danmaku.ijk.media:ijkplayer-x86:0.6.0'
    compile 'tv.danmaku.ijk.media:ijkplayer-x86_64:0.6.0'  //最小版本21

ijkplayer打包不同的ABI后，应该是对EXO支持才把部分ABI的最小版本设置为21，考虑到部分机型需要64的支持，然而项目最小版本又不行改到21，当前在项目中加入x86、x86_64、arm64文件，以下是提供最小版本为9的compile依赖出来

	//对应ijkplayer的  compile 'tv.danmaku.ijk.media:ijkplayer-armv7a:0.6.0'
	compile 'com.dou361.ijkplayer-armv7a:jjdxm-ijkplayer-armv7a:1.0.0'  
	//对应ijkplayer的  compile 'tv.danmaku.ijk.media:ijkplayer-armv5:0.6.0'
	compile 'com.dou361.ijkplayer-armv5:jjdxm-ijkplayer-armv5:1.0.0' 
	//对应ijkplayer的  compile 'tv.danmaku.ijk.media:ijkplayer-arm64:0.6.0'
	compile 'com.dou361.ijkplayer-arm64:jjdxm-ijkplayer-arm64:1.0.0' 
	//对应ijkplayer的  compile 'tv.danmaku.ijk.media:ijkplayer-x86:0.6.0'
	compile 'com.dou361.ijkplayer-x86:jjdxm-ijkplayer-x86:1.0.0' 
	//对应ijkplayer的  compile 'tv.danmaku.ijk.media:ijkplayer-x86_64:0.6.0'
	compile 'com.dou361.ijkplayer-x86_64:jjdxm-ijkplayer-x86_64:1.0.0'


demo中原来的jniLibs目录下的文件，已经移除，都是使用上面的依赖方式，如果网络环境差compile不下来，可以到项目的release目录中去下载 

#### step2: ####

多种分辨率流切换的案例，例如播放器的标清、高清、超清、720P等。

#### 1.简单的播放器实现 ####

    rootView = getLayoutInflater().from(this).inflate(R.layout.simple_player_view_player, null);
	setContentView(rootView);
	String url = "http://9890.vod.myqcloud.com/9890_9c1fa3e2aea011e59fc841df10c92278.f20.mp4";
    player = new PlayerView(this,rootView)
            .setTitle("什么")
            .setScaleType(PlayStateParams.fitparent)
            .hideMenu(true)
            .forbidTouch(false)
            .showThumbnail(new OnShowThumbnailListener() {
                @Override
                public void onShowThumbnail(ImageView ivThumbnail) {
                    Glide.with(mContext)
                            .load("http://pic2.nipic.com/20090413/406638_125424003_2.jpg")
                            .placeholder(R.color.cl_default)
                            .error(R.color.cl_error)
                            .into(ivThumbnail);
                }
            })
            .setPlaySource(url)
            .startPlay();

#### 2.多种不同的分辨率流的播放器实现 ####
在布局中使用simple_player_view_player.xml布局

	<include
        layout="@layout/simple_player_view_player"
        android:layout_width="match_parent"
        android:layout_height="180dp"/>

代码中创建一个播放器对象

	/**播放资源*/
	ist<VideoijkBean> list = new ArrayList<VideoijkBean>();
	String url1 = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4";
    String url2 = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f30.mp4";
    VideoijkBean m1 = new VideoijkBean();
    m1.setStream("标清");
    m1.setUrl(url1);
    VideoijkBean m2 = new VideoijkBean();
    m2.setStream("高清");
    m2.setUrl(url2);
    list.add(m1);
    list.add(m2);
	/**播放器*/
	rootView = getLayoutInflater().from(this).inflate(你的布局, null);
	setContentView(rootView);
	player = new PlayerView(this,rootView)
                .setTitle("什么")
                .setScaleType(PlayStateParams.fitparent)
                .hideMenu(true)
                .forbidTouch(false)
                .showThumbnail(new OnShowThumbnailListener() {
                    @Override
                    public void onShowThumbnail(ImageView ivThumbnail) {
						/**加载前显示的缩略图*/
                        Glide.with(mContext)
                                .load("http://pic2.nipic.com/20090413/406638_125424003_2.jpg")
                                .placeholder(R.color.cl_default)
                                .error(R.color.cl_error)
                                .into(ivThumbnail);
                    }
                })
                .setPlaySource(list)
                .startPlay();

#### step3: ####

配置生命周期方法,为了让播放器同步Activity生命周期，建议以下方法都去配置，注释的代码，主要作用是播放时屏幕常亮和暂停其它媒体的播放。

	 @Override
    protected void onPause() {
        super.onPause();
        if (player != null) {
            player.onPause();
        }
        /**demo的内容，恢复系统其它媒体的状态*/
        //MediaUtils.muteAudioFocus(mContext, true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (player != null) {
            player.onResume();
        }
        /**demo的内容，暂停系统其它媒体的状态*/
        MediaUtils.muteAudioFocus(mContext, false);
        /**demo的内容，激活设备常亮状态*/
        //if (wakeLock != null) {
        //    wakeLock.acquire();
        //}
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.onDestroy();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (player != null) {
            player.onConfigurationChanged(newConfig);
        }
    }

    @Override
    public void onBackPressed() {
        if (player != null && player.onBackPressed()) {
            return;
        }
        super.onBackPressed();
        /**demo的内容，恢复设备亮度状态*/
        //if (wakeLock != null) {
        //    wakeLock.release();
        //}
    }


## More Actions ##

1.视频界面裁剪设置，可通过方法setScaleType(int type)去设置

	1. PlayStateParams.fitParent:可能会剪裁,保持原视频的大小，显示在中心,当原视频的大小超过view的大小超过部分裁剪处理
    2. PlayStateParams.fillParent:可能会剪裁,等比例放大视频，直到填满View为止,超过View的部分作裁剪处理
    3. PlayStateParams.wrapcontent:将视频的内容完整居中显示，如果视频大于view,则按比例缩视频直到完全显示在view中
    4. PlayStateParams.fitXY:不剪裁,非等比例拉伸画面填满整个View
    5. PlayStateParams.f16_9:不剪裁,非等比例拉伸画面到16:9,并完全显示在View中
    6. PlayStateParams.f4_3:不剪裁,非等比例拉伸画面到4:3,并完全显示在View中



## License ##

    Copyright (C) dou361, The Framework Open Source Project
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
     	http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

## (Frequently Asked Questions)FAQ ##
## Bugs Report and Help ##

If you find any bug when using project, please report [here][issues]. Thanks for helping us building a better one.




[web]:http://www.dou361.com
[github]:https://github.com/jjdxmashl/
[project]:https://github.com/jjdxmashl/jjdxm_ijkplayer/
[issues]:https://github.com/jjdxmashl/jjdxm_ijkplayer/issues/new
[downapk]:https://raw.githubusercontent.com/jjdxmashl/jjdxm_ijkplayer/master/apk/app-debug.apk
[lastaar]:https://raw.githubusercontent.com/jjdxmashl/jjdxm_ijkplayer/master/release/jjdxm-ijkplayer-1.0.0.aar
[lastjar]:https://raw.githubusercontent.com/jjdxmashl/jjdxm_ijkplayer/master/release/jjdxm-ijkplayer-1.0.0.jar
[icon01]:https://raw.githubusercontent.com/jjdxmashl/jjdxm_ijkplayer/master/screenshots/icon01.png
[icon02]:https://raw.githubusercontent.com/jjdxmashl/jjdxm_ijkplayer/master/screenshots/icon02.png

[jaraar]:https://github.com/jjdxmashl/jjdxm_ecodingprocess/blob/master/架包的打包引用以及冲突解决.md
[minify]:https://github.com/jjdxmashl/jjdxm_ecodingprocess/blob/master/AndroidStudio代码混淆注意的问题.md
[author]:https://github.com/tcking
[author1]:https://github.com/Bilibili
[url]:https://github.com/tcking/GiraffePlayer
[url1]:https://github.com/Bilibili/ijkplayer

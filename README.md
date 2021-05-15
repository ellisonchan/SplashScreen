# Splash Screen Feature
Splash screen demo that used with [Splash Screen](https://developer.android.google.cn/reference/android/window/SplashScreen) API on `Android 12`.

![](https://img-blog.csdnimg.cn/img_convert/06cb7a569e6968912d6a9a369bb486a7.png)

## :camera_flash:Screenshot
### Default splash screen

![在这里插入图片描述](https://img-blog.csdnimg.cn/20210514235608908.gif#pic_center)

### Splash screen with animated icon

![在这里插入图片描述](https://img-blog.csdnimg.cn/20210514235916608.gif#pic_center)

### Splash screen exit animation by total view

![在这里插入图片描述](https://img-blog.csdnimg.cn/20210515000406406.gif#pic_center)

### Splash screen exit animation by icon

![在这里插入图片描述](https://img-blog.csdnimg.cn/20210514235535862.gif#pic_center)


## :orange_book:　Splash Screen API

###  Class & Interface
| 类/接口                 | 作用                                                         |
| ----------------------- | ------------------------------------------------------------ |
| SplashScreen            | 启动画面管理接口，通过Activity#getSplashScreen取得           |
| OnExitAnimationListener | 启动画面退出的回调接口，通过SplashScreen#setOnExitAnimationListener注册 |
| SplashScreenView        | 启动画面包含的视图，用以定制整体或Icon的退出动画             |

### attr

| attr                                  | 作用                             | 备注                                     |
| :------------------------------------ | :------------------------------- | :--------------------------------------- |
| splashScreenTheme                     | 指定SplashScreen相关的Style      | 指定的style有些问题比如brand图片会不显示 |
| windowSplashScreenBackground          | 定制启动画面的背景颜色           | 未设置的话从windowBackground里读取       |
| windowSplashScreenBrandingImage       | 自定义指定启动画面底部的品牌图标 | -                                        |
| windowSplashScreenAnimatedIcon        | 指定Icon，支持静态或动画Drawable | -                                        |
| windowSplashScreenAnimationDuration   | 指定动画Icon的时长               | 上限为1000ms                             |
| windowSplashScreenIconBackgroundColor | 补充图标Icon的背景色             | -                                        |

## :orange_book:　Reference
[New feature:Splash Screen](https://developer.android.google.cn/about/versions/12/features/splash-screen)

## :orange_book:　My blog

<https://blog.csdn.net/allisonchen>

## :copyright: License
MIT License

Copyright (c) 2021 Ellison Chan

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
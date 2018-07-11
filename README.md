
# Hands-Chopping #
<p align="center">
    <img src="https://raw.githubusercontent.com/noterpopo/Hands-Chopping/master/images/screenshot4.png">
</p>
<p align="center">
    <img src="https://img.shields.io/badge/version-1.00-brightgreen.svg">
    <img src="https://img.shields.io/badge/build%20-passing-brightgreen.svg">
    <img src="https://img.shields.io/badge/Api-14%2B-blue.svg">
    <img src="https://img.shields.io/badge/license-Apache--2.0-blue.svg">
    <img src="https://img.shields.io/badge/Author-%E6%A2%81LG.P-orange.svg">
    <img src="https://img.shields.io/badge/license-Apache--2.0-blue.svg">
</p>

+ 主要功能
	1. 获取Steam和杉果实时优惠信息
	2. 关键字查找两个平台的游戏
	3. 游戏详情页面和获取评论

## 技术框架和第三方库 ##

### MVP框架 ###
>&emsp;&emsp;MVP 模式是 MVC 模式在 **Android** 上的一种变体。在 MVC 模式中，Activity 应该是属于 View 这一层。而实质上，它既承担了 View，同时也包含一些 Controller 的东西在里面。这对于开发与维护来说不太友好，耦合度大高了。把 Activity 的 View 和 Controller 抽离出来就变成了 View 和 Presenter，这就是 MVP 模式。
     
&emsp;&emsp;个人理解是平时开发中Activity既要承担展示数据和基本交互功能，又要处理View和Model之间的逻辑，这样使得一个Activity写起来极为臃肿，部分逻辑因为和view层耦合较高而不能复用。**MVP 把 Activity 中的 UI 逻辑抽象成 View 接口，把业务逻辑抽象成 Presenter 接口，Model 类还是原来的 Model**。

&emsp;&emsp;所以本项目采用了MVP架构，框架结构基于[MVPArms项目]( https://github.com/JessYanCoding/MVPArms )。

<p align="center">
    <img src="https://raw.githubusercontent.com/noterpopo/Hands-Chopping/master/images/MVP1.png">
    <img src="https://raw.githubusercontent.com/noterpopo/Hands-Chopping/master/images/MVP2.png">
</p>

***
### RxJava + Retrofit ###

&emsp;&emsp;其实，RxJava 的本质可以压缩为**异步**这一个词。说到根上，它就是一个实现异步操作的库。但同样是做异步，为什么我们用它，而不用现成的 AsyncTask / Handler / XXX / ... ?

&emsp;&emsp;一个词：简洁。

&emsp;&emsp;异步操作很关键的一点是程序的简洁性，因为在调度过程比较复杂的情况下，异步代码经常会既难写也难被读懂。(例如Callback Hell)。Android创造的 AsyncTask 和Handler ，其实都是为了让异步代码更加简洁。**RxJava 的优势也是简洁**。

&emsp;&emsp;同时RxJava的编写风格是**流式**的，比原生的异步逻辑上更加清楚，而且**RxJava支持线程的自由控制**。例如下面的方法是请求Steam的打折列表（函数属于逻辑控制，处于P层），首先mModel（M层）请求服务器数据，subscribeOn(Schedules.io())表示getSteamSaleGame()这个方法执行在io线程上，subscribeOn(AndroidSchedulers.mainThread())表示retryWhen(new RetryWithDelay(3,2))（这个方法就是如果请求失败就继续重试3次，每次延时2秒）执行在主线程，而observeOn(AndroidSchedulers.mainThread())方法表示后面的都执行在主线程中。比如onNext()中的mRootView.imgLoad()，就是P层在M层获取data后调用V层显示data，这个就是MVP架构的运作模式。结合RxJava控制M层数据操作都在IO线程，V层都在主线程（安卓限制只能在主线程更新UI）。所以RxJava和MVP结合是起到1+1>2的作用的。

<p align="center">
    <img src="https://raw.githubusercontent.com/noterpopo/Hands-Chopping/master/images/code1.png">
</p>

&emsp;&emsp;那么M层获取数据的操作是如何和RxJava结合的呢？答案是**Retrofit**。

&emsp;&emsp;**Retrofit是一个基于okhttp3的网络请求库，在本项目采用它的原因是因为它能很好地与RxJava结合**。

&emsp;&emsp;下面是Retrofit的使用例子：

<p align="center">
    <img src="https://raw.githubusercontent.com/noterpopo/Hands-Chopping/master/images/code2.png">
</p>

&emsp;&emsp;可以看到通过注解就能构建出动态请求连接，并且返回的类型是经过Observable包装后的GameSaleList。Observable是RxJava中最基本的一个类，表明这个对象是可以被观察的。

&emsp;&emsp;其实RxJava就是扩展的观察者模式。基于观察者模式，RxJava 有四个基本概念：**Observable (可观察者，即被观察者)、 Observer (观察者)、 subscribe (订阅)、事件**。

<p align="center">
    <img src="https://raw.githubusercontent.com/noterpopo/Hands-Chopping/master/images/rxjava1.png">
</p>

&emsp;&emsp;Observable 和 Observer 通过 subscribe() 方法实现**订阅关系**，从而 Observable 可以在需要的时候发出事件（即onNext()，相当于onClick()于Button）来通知 Observer。

<p align="center">
    <img src="https://raw.githubusercontent.com/noterpopo/Hands-Chopping/master/images/rxjava2.png">
</p>

&emsp;&emsp;所以Retrofit能把请求来的数据转化成Observable对象，结合RxJava和MVP架构，实现了从数据请求到数据展示的完整流式过程。最重要的是，在Rxjava帮助下，代码的编写也是符合流式逻辑的，不会像AsyncTask或者Handle那样逻辑跳跃，随着项目变大，RxJava还是能保持这种流式逻辑。
***
### Dagger依赖注入 ###

&emsp;&emsp;MVP+RxJava+Retorfit已经能很大程度降低我们代码的耦合度了，但Dagger依赖注入是最后一个大杀器，能使我们类与类之间的依赖统一管理。进一步降低耦合度。可以参考示意图理解Dagger：

<p align="center">
    <img src="https://raw.githubusercontent.com/noterpopo/Hands-Chopping/master/images/dagger.png">
</p>

&emsp;&emsp;**MVP架构中，M层需要持有P层对象，P层需要持有M和V层对象，V层需要持有P层对象**，这样三者之间的耦合度是很高的，通过Dagger进行依赖注入，我们能够很大程度减低这种耦合，具体原理涉及编译器代码生成，这里不展开叙述。
***
##### 在上述框架和工具的基础上，我们的项目最终由7个模块构成 #####
1. **App模块**。这个模块负责把其他模块统一起来，是最上层的模块；
2. **CommonRes模块**。这个模块存放比如进度条，加载条等所有模块都能使用到的资源；
3. **CommonSDK模块**。这个模块可以看作是所有模块的公共工具集合。比如一些json解析，字符串处理工具的集合；
4. **CommonService模块**。这个模块是App模块与其他模块的桥梁。这里存放其他模块的接口，然后再在各个模块实现接口，实现App模块与其他功能模块的交流；（这个使用到的是接口下沉技术）；
5. **Home模块**。这个是第一个功能模块，实现的是获取优惠信息并展示；
6. **Search模块**。这是第二个功能模块，实现的是查找游戏；
7. **Detail模块**。这个是第三个功能模块，实现的是展示游戏详情和评论；

<p align="center">
    <img src="https://raw.githubusercontent.com/noterpopo/Hands-Chopping/master/images/mvp.png">
</p>

&emsp;&emsp;我们的App模块对应的就是宿主层，而Home，Search和Detail模块对应的是业务层，可以看出微型服务提供者，CommonRes，CommonSDK和CommonService对应基础层，是公共资源和底层基础的提供者；
***
##### 另外我们每个模块目录结构如下：#####

<p align="center">
    <img src="https://raw.githubusercontent.com/noterpopo/Hands-Chopping/master/images/module.png">
</p>

三个模块都基本类似结构，第三个模块会有较大改动；
***
##### 程序截图 #####

<p align="center">
    <img src="https://raw.githubusercontent.com/noterpopo/Hands-Chopping/master/images/screenshot1.png">
</p>

<p align="center">
    <img src="https://raw.githubusercontent.com/noterpopo/Hands-Chopping/master/images/screenshot2.png">
</p>

<p align="center">
    <img src="https://raw.githubusercontent.com/noterpopo/Hands-Chopping/master/images/screenshot3.png">
</p>

## License
``` 
 Copyright 2018, 梁LG.P       
  
   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at 
 
       http://www.apache.org/licenses/LICENSE-2.0 

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
```

# Frontend



## 📌목표

- Android Studio 를 이해하고 활용할 수 있다.
- Kotlin, xml 코딩을 통해 어플리케이션의 기능들을 구현할 수 있다.
- 팀으로 작업하여 시너지를 내고, 새로운 아이디어 등을 추가하여 학습한 결과를 구현할 기획 및 설계

------

✅ 비화 서버를 구축

✅ 설계서

- 화면 설계서(WireFrame)
- REST API

✅ 소스 코드

- 프로젝트 관련 어플리케이션 프론트엔드 전체 소스
- 레이아웃 디자인, 기능 및 사용된 리소스

<hr>



## 개발

- Android Studio 레이아웃 구성 및 기능 구현

## 구조

안드로이드app 프로젝트 구조는 크게 manifest와 java, resouce, Gradle 패키지로 이루어져 있다.


manifest :​ "AndroidManifest.xml"란 단 하나의 파일이 담겨져 있으며, 안드로이드의 컨트롤 타워라고 이해하면 된다. 안드로이드 어플리케이션을 구동하는데 필요한 설정값을 관리해주는 곳이다. 

    애플리케이션 구성 정보를 담고 있는 파일. 애플리케이션을 구성하는 전반적인 기능에 대한 명세를 포함.
    안드로이드 앱 컴포넌트의 선언
    안드로이드 앱 실행을 위한 소유 권한 정의
    안드로이드 앱이 필요로 하는 최소한의 API 레벨 정의
    안드로이드 앱이 필요로 하는 H/W, S/W 기능 정의
    안드로이드 앱이 필요로 하는 API 라이브러리 정의

java : 클래스를 관리하는 폴더이다. 

res : Resource 폴더로 UI와 관련된 파일과 디자인 리소스, 문자열 리소스를 담고 있는 폴더이다.

안드로이드에서는 애플리케이션의 레이아웃을 위해 주로 자바 코드보다는 XML 형식을 이용한다.

(XML = eXtensible Markup Language. HTML과 같은 마크업 언어)


## 개발 환경 설정

```
# Gradle
- Gradle은 빌드 시스템이다. 

쉽게 말하면,
우리가 개발한 코드를 모바일에서 실행할 수 있도록 변환해주는 시스템이다.
또한 다른 사람이 개발한 소스코드(=dependency)를 쉽게 가져와 사용할 수 있다.

그 밖에도 테스트, 배포 등 다양한 기능을 제공하고 있다.

빌드 시스템이 없다면

다른 사람이 개발한 라이브러리를 직접 다운받고,
프로젝트에 lib 폴더를 만들어 복사하고,

복잡한 클래스 패스를 설정해주어야 한다.

```
- build.gradle (Project: 프로젝트명)
: 프로젝트 수준의 Gradle 설정 파일

    1. buildscript
    -repositories : 외부 저장소 설정. google()이 기본으로 설정된다.
    -dependencies(의존성:라이브러리): gradle 플러그인 버전 설정

    2. allprojects
    -repositories: 위의 buildscript > repositories와 동일한 외부 저장소가 설정된다.

    3. task
    : 프로젝트 공통적으로 사용할 작업을 정의한다.
    -clean(type:Delete): 기본으로 추가된 공통작업으로, 빌드 시 생성되는 build 디렉터리들을 삭제한다.


- build.gradle (Module: 프로젝트명.app)
: 모듈 수준의 Gradle 설정 파일.
모듈의 종류로는 app모듈, 웨어러블 모듈, 안드로이드 TV 모듈 등이 있다.
phone, tablet 프로젝트를 생성하기 때문에, 기본적으로 app 모듈 수준의 빌드 설정, 라이브러리 정보가 저장된다.

    1) plugins
    : 안드로이드 개발을 위한 플러그인 설정 영역으로, 'com.android.application'이 기본으로 지정된다.

    2) android
    : 컴파일/빌드, 버전정보, 난독여부 등을 설정한다.
    : 원래 버전정보 등은 manifest.xml에 설정했으나, 지금은 모듈 수준의 build.gradle 파일에 작성한다.

    - compileSdkVersion: 컴파일 SDK버전 설정
    - defaultConfig: 각종 버전 정보 설정
    - buildTypes: 빌드 설정
    - compileOptions: 컴파일 설정

    3) dependencies
    : 외부 라이브러리를 설정한다.


- gradle-wrapper.properties(Gradle Version)
: Gradle 자체와 관련된 설정 파일

- proguard-rules.pro (ProGuard Rules for 프로젝트명.app)
: 코드 난독화 도구 설정 파일. 
필요한 규칙이 있다면 이 파일에 기술한다.

- gradle.properties (Project Properties)
: 프로젝트 수준의 Gradle 환경 설정 파일

- settings.gradle (Project Settings)
: 프로젝트에 포함된 모듈을 등록/관리하는 파일
phone, tablet으로 프로젝트를 생성한 경우, app 모듈만 기본으로 등록되어 있다.
웨어러블 모듈이나 안드로이드 TV모듈을 추ㅏ하면 이 곳에 등록된다.

- local.properties (SDK Location)
: 안드로이드 SDK 경로를 관리하는 파일
열어보면 그냥 안드로이드 SDK가 설치돈 경로만 적혀 있다.

## 실행, 배포

```

- 안드로이드 앱의 실행 과정

    개발자가 작성한 JAVA 코드가 JAVA 컴파일러에 의해 JAVA 바이트 코드로 컴파일 됨
    원래 JAVA 환경에서는 컴파일된 JAVA 바이트 코드를 JVM을 통해 실행하지만, 
    안드로이드는 Dalvik이라는 별도의 가상 머신에서 실행함. 
    
    이를 위해 안드로이드 SDK가 제공하는 DEX 변환기를 이용하여 JAVA 바이트 코드를 Dalvik의 실행 포맷인 .dex 파일로 변환함.

    변환된 .dex 파일과 리소스 파일들은 설치할 수 있는 .apk 파일로 만들어짐. 
    배포 및 설치를 위한 apk 파일로 만들어지기 위해서는 AAPT라는 개발 도구를 이용하고, 이 과정을 패키징이라고 함.

    패키징 과정이 끝나 만들어진 apk 파일을 플랫폼에 설치하면 런타임에서 실행될 수 있음.
    최초 설치시 AOT 컴파일러의 dex2oat를 사용하여 네이티브 코드로 변환한 후 안드로이드 스마트폰에서 실행할 수 있게 함.

안드로이드 애플리케이션은 .apk 파일 확장자로 패키징되어 배포됨.

    안드로이드 프로젝트에 대한 코드 컴파일 작업이 수행되면, JAVA 프로그램의 .class 파일 생성
    .class 파일을 이용하여 안드로이드 실행환경(Android Runtime)에 적합한 .dex 파일 생성 / 
    컴파일된 리소스 파일(XML)은 resources.arsc라는 파일로 생성 / 
    안드로이드 앱의 설정 환경을 정의하는 AndroidManifest.xml 파일 생성

    .dex 파일 + resources.arsc + AndroidManifest.xml + 컴파일되지 않은 리소스 파일(이미지, 아이콘 파일 등) =>
    함께 패키징하여 .apk 파일 생성

    Debug Key를 사용하여 .apk 파일에 서명하는 Signing 작업을 함(.apk 파일이 타인에 의하여 위변조되는 것을 방지)
    Google Play에 개발된 안드로이드 앱의 배포가 준비되면, 자신의 Key 값을 이용하여 Signing 가능
    Signing 작업에서 사용한 키값은 안드로이드 애플리케이션의 업데이트에서 개발자의 식별에 사용됨
    패키징된 apk 파일을 .dex 파일로 변환하여 
    실행시 Dalvik이나 ART(Android Run-Time)라고 하는 모바일 기기에 적합화된 런타인 가상 머신 사용. 
    
    요즘에는 ART를 디폴트로 사용. 
    


```

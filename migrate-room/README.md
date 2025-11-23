# 기존 앱을 Room KMP로 마이그레이션하기 코드랩

이 폴더에는 [기존 앱을 Room KMP로 마이그레이션하기 코드랩](http://goo.gle/kmp-migrate-room-codelab)의 소스 코드가 포함되어 있습니다.

## 📚 학습 내용

- Room 데이터베이스를 Kotlin Multiplatform으로 마이그레이션하는 방법
- Android와 iOS 간 데이터베이스 공유
- Repository 패턴과 의존성 주입(Hilt)
- Flow와 StateFlow를 사용한 반응형 UI
- DAO, Entity, 그리고 Room 어노테이션 활용

## 🏗️ 프로젝트 구조

```
migrate-room/
├── androidApp/          # Android 앱 모듈
│   ├── database/       # Room 데이터베이스 및 DAO
│   ├── di/            # Hilt 의존성 주입 모듈
│   ├── model/         # 데이터 모델 (Entity)
│   ├── network/       # Ktor 네트워크 클라이언트
│   ├── repository/    # Repository 패턴 구현
│   └── ui/            # Compose UI 및 ViewModel
├── shared/            # 공유 코드 모듈 (향후 확장)
└── iosApp/            # iOS 앱 (향후 마이그레이션)
```

## 💡 주요 개념

- **Room KMP**: Android의 Room 데이터베이스를 iOS와 공유
- **Repository 패턴**: 데이터 소스 추상화
- **Hilt**: 의존성 주입 프레임워크
- **Flow/StateFlow**: 반응형 데이터 스트림
- **Ktor**: 멀티플랫폼 HTTP 클라이언트


## License
```
Copyright 2025 The Android Open Source Project

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

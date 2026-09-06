# 要件定義
## やること
- 本の管理表
- 追加処理
- API
- **横スクロール**
- **左列固定**
- 遷移先から戻るボタン
- リストの削除ボタン

## やらないこと
- 削除処理
- 検索処理
- 編集処理

## ディレクトリ構成
```list
com.example.bookmanager/
├── data/
│   ├── local/                  ← Room関連(端末内DB)
│   │   ├── BookEntity.kt
│   │   ├── BookDao.kt
│   │   └── AppDatabase.kt
│   │
│   ├── remote/                 ← API通信関連
│   │   ├── BookApiModel.kt     (BookResponse, BookItem, VolumeInfo)
│   │   ├── ApiService.kt
│   │   └── RetrofitInstance.kt
│   │
│   ├── mapper/                 ← API⇔DB間の変換処理
│   │   └── BookMapper.kt       (BookItem.toBookEntity())
│   │
│   └── repository/             ← Room・APIをまとめる窓口
│       └── BookRepository.kt
│
├── ui/
│   ├── list/                   ← 管理表(一覧)画面
│   │   ├── MainActivity.kt
│   │   ├── FixedColumnAdapter.kt
│   │   ├── ScrollColumnAdapter.kt
│   │   └── BookListViewModel.kt
│   │
│   └── search/                 ← 検索・追加画面
│       ├── SearchActivity.kt
│       ├── SearchResultAdapter.kt
│       └── SearchViewModel.kt
│
└── BookManagerApplication.kt   ← アプリ全体の初期化(任意)
```
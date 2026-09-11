package com.example.myapplication.data.remote

/**
 * Google Books APIのJSON形式
 * {
 *   "items": [
 *     {
 *       "id": "abc123",
 *       "volumeInfo": {
 *         "title": "サンプルタイトル",
 *         "authors": ["山田太郎"],
 *         "publisher": "サンプル出版",
 *         "publishedDate": "2024",
 *         "imageLinks": {
 *           "thumbnail": "https://..."
 *         }
 *       }
 *     }
 *   ]
 * }
 * */

// APIから返ってくるJSON全体に対応する形
data class BookResponse(
    val items: List<BookItem>?
)

// 検索結果一件分に対応する形
data class BookItem(
    val id: String,
    val volumeInfo: VolumeInfo
)

// 本の詳細情報に対応する形
data class VolumeInfo(
    val title: String,
    val authors: List<String>?,
    val publisher: String?,
    val publishedDate: String?,
    val imageLinks: ImageLinks?
)

// 表紙画像のURLに対応する形
data class ImageLinks(
    val thumbnail: String?
)
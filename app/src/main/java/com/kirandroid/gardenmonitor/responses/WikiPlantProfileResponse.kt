package com.kirandroid.gardenmonitor.responses

data class WikiPlantProfileResponse(
    val content_urls: ContentUrls,
    val description: String,
    val description_source: String,
    val dir: String,
    val displaytitle: String,
    val extract: String,
    val extract_html: String,
    val lang: String,
    val namespace: Namespace,
    val originalimage: Originalimage,
    val pageid: Int,
    val revision: String,
    val thumbnail: Thumbnail,
    val tid: String,
    val timestamp: String,
    val title: String,
    val titles: Titles,
    val type: String,
    val wikibase_item: String
)

data class ContentUrls(
    val desktop: Desktop,
    val mobile: Mobile
)

data class Namespace(
    val id: Int,
    val text: String
)

data class Originalimage(
    val height: Int,
    val source: String,
    val width: Int
)

data class Thumbnail(
    val height: Int,
    val source: String,
    val width: Int
)

data class Titles(
    val canonical: String,
    val display: String,
    val normalized: String
)

data class Desktop(
    val edit: String,
    val page: String,
    val revisions: String,
    val talk: String
)

data class Mobile(
    val edit: String,
    val page: String,
    val revisions: String,
    val talk: String
)
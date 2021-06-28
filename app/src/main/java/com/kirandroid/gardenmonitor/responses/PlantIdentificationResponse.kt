package com.kirandroid.gardenmonitor.responses

data class PlantIdentificationResponse(
    val language: String,
    val preferedReferential: String,
    val query: Query,
    val remainingIdentificationRequests: Int,
    val results: List<Result>,
    val switchToProject: String
)

data class Query(
    val images: List<String>,
    val organs: List<String>,
    val project: String
)

data class Result(
    val gbif: Gbif,
    val images: List<Image>,
    val score: Int,
    val species: Species
)

data class Gbif(
    val id: Int
)

data class Image(
    val author: String,
    val citation: String,
    val date: Date,
    val license: String,
    val organ: String,
    val url: Url
)

data class Species(
    val commonNames: List<String>,
    val family: Family,
    val genus: Genus,
    val scientificName: String,
    val scientificNameAuthorship: String,
    val scientificNameWithoutAuthor: String
)

data class Date(
    val string: String,
    val timestamp: Int
)

data class Url(
    val m: String,
    val o: String,
    val s: String
)

data class Family(
    val scientificName: String,
    val scientificNameAuthorship: String,
    val scientificNameWithoutAuthor: String
)

data class Genus(
    val scientificName: String,
    val scientificNameAuthorship: String,
    val scientificNameWithoutAuthor: String
)
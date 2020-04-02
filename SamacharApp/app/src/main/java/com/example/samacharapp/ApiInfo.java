package com.example.samacharapp;

class ApiInfo {

    public static String API_KEY="9fcbbabb517c43528069ef3b7eb67337";

    public static String allSources(){
        return "https://newsapi.org/v2/sources?apiKey=9fcbbabb517c43528069ef3b7eb67337"+API_KEY;
    }

    public static String articlesFrom(String sourceId)
    {
        return "https://newsapi.org/v2/top-headlines?sources=bbc-news&apiKey=9fcbbabb517c43528069ef3b7eb67337";
    }
}

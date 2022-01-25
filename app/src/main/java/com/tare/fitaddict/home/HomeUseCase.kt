package com.tare.fitaddict.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.tare.fitaddict.pojo.entities.UserCalories
import com.tare.fitaddict.pojo.entities.Article
import com.tare.fitaddict.pojo.response.ResponseNews

object HomeUseCase {
    private val rawDataCalories = HomeRepository.userCalories
    private val rawDataNews = HomeRepository.responseNews
    val consumedCalories: LiveData<String> = consumedCalories()
    val remainingCalories: LiveData<String> = remainingCalories()
    val newsArticles: LiveData<List<Article>> = newsArticles()

    private fun consumedCalories(): LiveData<String> {
        val repoLiveData = rawDataCalories
        val ans = Transformations.map(repoLiveData) {
            createCon(it)
        }
        return ans
    }

    private fun remainingCalories(): LiveData<String> {
        val repo = rawDataCalories
        val ans = Transformations.map(repo) {
            createRem(it)
        }
        return ans
    }

    private fun newsArticles(): LiveData<List<Article>>
    {
        HomeRepository.fetchNews()
        val data = rawDataNews
        val ans = Transformations.map(data){
            createArt(it)
        }
        return ans
    }

    private fun createArt(responseNews: ResponseNews): List<Article>
    {
        return responseNews.articles
    }

    private fun createRem(userCalories: UserCalories): String {
        return userCalories.remainingCalories.toString()
    }

    private fun createCon(userCalories: UserCalories): String {
        return userCalories.consumedCalories.toString()
    }
}
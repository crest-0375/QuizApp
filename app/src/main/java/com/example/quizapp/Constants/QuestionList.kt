package com.example.quizapp.Constants

import com.example.quizapp.Data.Question

object QuestionList {
    fun getQuestions(): ArrayList<Question> {
        return arrayListOf(
            Question(
                "To what political party did Abraham Lincoln belong when elected POTUS?",
                "Republican",
                "Democrat",
                "Independent",
                "Whig"
            ),
            Question(
                "These two countries held a commonwealth from the 16th to 18th century.",
                "Poland and Lithuania",
                "Hutu and Rwanda",
                "North Korea and south Korea",
                "Bangladesh and bhutan"
            ),
            Question(
                "Which of these country remained neutral during world war II?",
                "Switzerland",
                "United Kingdom",
                "France",
                "Italy"
            ),
            Question(
                "Who was the first president of United States?",
                "George Washington",
                "James Madison",
                "Thomas Jefferson",
                "James K. Polk"
            ),
            Question(
                "The idea of Socialism was articulated and advanced by whom?",
                "Karl Marx",
                "Vladimir Lenin",
                "Joseph Stalin",
                "Vladimir Putin"
            ),
            Question(
                "The \"Trail of Tears\" was a result of which United States President's Indian Removal Policy?",
                "Andrew Jackson",
                "Harry S. Truman",
                "Martin Van Buren",
                "John Quincy Adams"
            ),
            Question(
                "When was Google founded?",
                "September 4, 1998",
                "October 9, 1997",
                "December 12, 1989",
                "Feburary 7th, 2000"
            ),
            Question(
                "What year did World War I begin?",
                "1914",
                "1905",
                "1919",
                "1925"
            ),
            Question(
                "Which of the following countries was not an axis power during World War II",
                "Soviet Union",
                "Italy",
                "Germany",
                "Japan"
            ),
            Question(
                "Which modern country is known as \"The Graveyard of Empires\"?",
                "Afghanistan",
                "China",
                "Iraq",
                "Russia"
            ),
        )
    }
}
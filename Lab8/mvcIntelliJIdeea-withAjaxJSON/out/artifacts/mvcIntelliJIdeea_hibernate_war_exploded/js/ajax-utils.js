

function getQuestions(userid, callbackFunction) {
	$.getJSON(
		"QuizController",
		{ action: 'getAll', userid: userid },
	 	callbackFunction
	);
}

function startQuiz(nr_question, nr_question_page) {
    $.post("QuizController",
		{ action: "",
			number_questions: nr_question,
			questions_per_page: nr_question_page
		},
		callbackFunction
	);
}

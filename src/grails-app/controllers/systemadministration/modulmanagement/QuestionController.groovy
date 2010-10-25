/**
 * Copyright (c) 2009/2010, Very Large Business Applications, University Oldenburg. Specifically:
 * Daniel Süpke <suepke@gmx.de>
 * Andreas Solsbach <solsbach@wi-ol.de>
 * Benjamin Wagner vom Berg <wagnervomberg@wi-ol.de>
 * Prof. Dr. Jorge Marx Gomez <jorge.marx.gomez@uni-oldenburg.de>
 * Christian Wenke <cw81@cw81.de>
 * Desislava Dechkova <desislavamd@yahoo.com>
 * Edzard Fisch <edzard.fisch@googlemail.com>
 * Frank Gerken <frank.gerken@uni-oldenburg.de>
 * Gerrit Meerpohl <gerrit.meerpohl@uni-oldenburg.de>
 * Irene Fröhlich <froehlich.irene@web.de>
 * Kerem-Kazim Sezer <Kerem.Sezer@gmx.de>
 * Olaf Roeder <o.roeder@gmx.net>
 * Oliver Norkus <oliver.norkus@googlemail.com>
 * Rachid Lacheheb <rachid.lacheheb@mail.uni-oldenburg.de>
 * Sebastian van Vliet <sebastian.van.vliet@mail.uni-oldenburg.de>
 * Swetlana Lipnitskaya <swetlana.lipnitskaya@uni-oldenburg.de>
 * 
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 * 
 *     * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 *     * Neither the name of the copyright holders nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 */
 
package systemadministration.modulmanagement


import org.grails.rateable.*

/**
 * This class manages the Question-System ("Direkt-Zu-System)
 * 
 * @auhtor: Gerrit
 */
class QuestionController {
	
	//define allowed HTTP-Request methods
	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	
	/**
	 * Redirection to list
	 */ 
	def index = {
		redirect(action: "list", params: params)
	}

	/**
	 * List Questions
	 */
	def list = {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[questionInstanceList: Question.list(params), questionInstanceTotal: Question.count()]
	}

	/**
	 * List all answered Questions
	 */
	def allAnswered= {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		
		//Question is answered, if anser ist not empty
		def questionInstanceList= Question.findAllByAnswerIsNotNullOrAnswerNotLike("",[sort:"lastUpdated", order:"desc"]);
		
		[questionInstanceList: questionInstanceList, questionInstanceTotal: questionInstanceList.count()]
	}

	/**
	 * List all new Questions
	 */
	def allNew= {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		
		//Question is new, if question is unanswered, sorted by id
		def questionInstanceList= Question.findAllByAnswerIsNullOrAnswerLike("",[sort:"id", order:"desc"]);
		
		[questionInstanceList: questionInstanceList, questionInstanceTotal: questionInstanceList.count()]
	}

	/**
	 * List all top-rated Questions
	 */
	def allTop= {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		
		//Question is top, if question is unanswered, sorted by rating
		def questionInstanceList= Question.listOrderByAverageRating(null); 
		ArrayList<Question> questionInstanceList2 = new ArrayList<Question>();
		for (Question question : questionInstanceList) {
			if(question.answer==null||question.answer==""){
				questionInstanceList2.add(question)
			}
		}
		
		[questionInstanceList: questionInstanceList2, questionInstanceTotal: questionInstanceList2.count()]
	}

	/**
	 * Display Question in HTML-Representation
	 */
	def display = {
		def max = 5;
		
		//Question is new, if question is unanswered, sorted by id
		def newQuestionInstanceList= Question.findAllByAnswerIsNullOrAnswerLike("",[max:5, sort:"id", order:"desc"]);
		
		//Question is top, if question is unanswered, sorted by rating
		def topQuestionInstanceList= Question.listOrderByAverageRating(null); 
		ArrayList<Question> topQuestionInstanceList2 = new ArrayList<Question>();
		def i =0;
		for (Question question : topQuestionInstanceList) {
			if(question.answer==null||question.answer==""){
				if(i<5){
					topQuestionInstanceList2.add(question)
					i++
				}
			}
		}
		
		//Question is answered, if anser ist not empty
		def answeredQuestionInstanceList= Question.findAllByAnswerIsNotNullOrAnswerNotLike("",[max:5, sort:"lastUpdated", order:"desc"]);
		
		[answeredQuestionInstanceList:answeredQuestionInstanceList,topQuestionInstanceList:topQuestionInstanceList2, newQuestionInstanceList: newQuestionInstanceList]
	}
	
	/**
	 * Create new Question
	 */
	def create = {
		def questionInstance = new Question()
		questionInstance.properties = params
		return [questionInstance: questionInstance]
	}

	/**
	 * Save created Question
	 */
	def save = {
		def questionInstance = new Question(params)
		if (questionInstance.save(flush: true)) {
			flash.message = "${message(code: 'default.created.message', args: [message(code: 'question.label', default: 'Question'), questionInstance.id])}"
			redirect(action: "show", id: questionInstance.id)
		}
		else {
			render(view: "create", model: [questionInstance: questionInstance])
		}
	}
	
	/**
	 * New Questions from users
	 */
	def userSave = {
		def active = true;
		def title = params.title
		def question = params.question
		def author=session.user
		
		def questionInstance = new Question(title:title,question:question,author:session.user, active:active)
		if (questionInstance.save(flush: true)) {
			flash.message = "${message(code: 'default.created.message', args: [message(code: 'question.label', default: 'Question'), questionInstance.id])}"
			redirect(action: "display", id: questionInstance.id)
		}
		else {
			render(view: "display", model: [questionInstance: questionInstance])
		}
	}

	/**
	 * Show values of a Question
	 */
	def show = {
		def questionInstance = Question.get(params.id)
		if (!questionInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'question.label', default: 'Question'), params.id])}"
			redirect(action: "list")
		}
		else {
			[questionInstance: questionInstance]
		}
	}

	/**
	 * Show values of a Question. Important for controlling of User-priviledges
	 */
	def show2 = {
		def questionInstance = Question.get(params.id)
		if (!questionInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'question.label', default: 'Question'), params.id])}"
			redirect(action: "display")
		}
		else {
			[questionInstance: questionInstance]
		}
	}

	/**
	 * Edit Question
	 */
	def edit = {
		def questionInstance = Question.get(params.id)
		if (!questionInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'question.label', default: 'Question'), params.id])}"
			redirect(action: "list")
		}
		else {
			return [questionInstance: questionInstance]
		}
	}

	/**
	 * Update edited Question
	 */
	def update = {
		def questionInstance = Question.get(params.id)
		if (questionInstance) {
			if (params.version) {
				def version = params.version.toLong()
				if (questionInstance.version > version) {
					
					questionInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'question.label', default: 'Question')] as Object[], "Another user has updated this Question while you were editing")
					render(view: "edit", model: [questionInstance: questionInstance])
					return
				}
			}
			questionInstance.properties = params
			if (!questionInstance.hasErrors() && questionInstance.save(flush: true)) {
				flash.message = "${message(code: 'default.updated.message', args: [message(code: 'question.label', default: 'Question'), questionInstance.id])}"
				redirect(action: "show", id: questionInstance.id)
			}
			else {
				render(view: "edit", model: [questionInstance: questionInstance])
			}
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'question.label', default: 'Question'), params.id])}"
			redirect(action: "list")
		}
	}

	/**
	 * Delete Question
	 */
	def delete = {
		def questionInstance = Question.get(params.id)
		if (questionInstance) {
			try {
				questionInstance.delete(flush: true)
				flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'question.label', default: 'Question'), params.id])}"
				redirect(action: "list")
			}
			catch (org.springframework.dao.DataIntegrityViolationException e) {
				flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'question.label', default: 'Question'), params.id])}"
				redirect(action: "show", id: params.id)
			}
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'question.label', default: 'Question'), params.id])}"
			redirect(action: "list")
		}
	}
}

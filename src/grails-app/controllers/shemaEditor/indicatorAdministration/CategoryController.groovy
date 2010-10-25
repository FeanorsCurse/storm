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
 
package shemaEditor.indicatorAdministration

class CategoryController {
	
	static allowedMethods = [save: "POST", update: "POST"]
	
	def index = {
		redirect(action: "list", params: params)
	}
	
	def list = {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[categoryInstanceList: Category.list(params), categoryInstanceTotal: Category.count()]
	}
	
	def create = {
		def categoryInstance = new Category()
		categoryInstance.properties = params
		return [categoryInstance: categoryInstance]
	}
	
	/*
	 * indicators of selected category
	 */
	def categoryIndicators = {
		
		
		
		def result
		
		def category = Category.get(params.cid)
		if(category){
			result = category.indicators	
		}else{
			result = null
			if(params.destCategoryInstance){
				result = Category.get(params.destCategoryInstance).indicators
			}
		}		
		
		render(view:"categoryIndicators", model:[cindis: result])
		
	}
	
	def save = {
		def categoryInstance = new Category(params)
		if (categoryInstance.save(flush: true)) {
			flash.info = "${message(code: 'default.created.message', args: [message(code: 'category.label', default: 'Category'), categoryInstance.id])}"
			redirect(action: "show", id: categoryInstance.id)
		}
		else {
			flash.message = "${message(code: 'category.not.created.message', args: [message(code: 'category.label', default: 'Category'), categoryInstance.id])}"
			render(view: "create", model: [categoryInstance: categoryInstance])
		}
	}
	
	def show = {
		
		def categoryInstance = Category.get(params.id)
		if (!categoryInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'category.label', default: 'Category'), categoryInstance.name])}"
			redirect(action: "list")
		}
		else {
			if(params.destCategoryInstance){
				[categoryInstance: categoryInstance, destCategoryInstance:params.destCategoryInstance, catname:Category.get(params.destCategoryInstance)]
			}else{
				[categoryInstance: categoryInstance]
			}
		}
	}
	
	/*
	 * deletes all selected categories from categorie-administration
	 */
	def deleteSelectedCat = {
	
		def selectedCat = request.getParameterValues('categorycheck').collect{it.toLong()
		}
		
		selectedCat.each{
			def categoryInstance = Category.get(it)
			if(!categoryInstance.indicators) {
				categoryInstance.delete()
				flash.info = "${message(code: 'categorylist.deleted.message', args: [message(code: 'category.label', default: 'Category'), categoryInstance.name])}"
			}else{
				flash.message = "${message(code: 'category.not.deleted.message', args: [message(code: 'category.label', default: 'Category'), categoryInstance.name])}"
			}
		}
		
		redirect(action:"list")
	}
	
	
	/*
	 * moves the selected indicator/indicators from current category to the chosen category
	 */
	def moveIndicator ={
		
		def selectedInd = request.getParameterValues('indicatorbox').collect{it.toLong()
		}
		def destCategoryInstance = Category.get(params.selectedCategory)
		def categoryInstance = Category.get(params.id)
		
		if(selectedInd.size()>0 && destCategoryInstance){
		
		selectedInd.each{
			
			def indicatorInstance = Indicator.get(it)
			indicatorInstance.setCategory(destCategoryInstance)
			indicatorInstance.save()
		}
		
		def targetSelectedCategories = request.getParameterValues('category').collect{it.toLong()
		}
		if(targetSelectedCategories){
			
						
			def destCat_b = Category.get(params.cat_b)
			
			
			targetSelectedCategories.each{
				def indicator = Indicator.get(it)
				indicator.setCategory(destCat_b)
				indicator.save()
			}
		}
		flash.info = "${message(code: 'categories.indicators.moved.success', args: [message(code: 'category.label', default: 'Category'), destCategoryInstance.name])}"
		redirect(action:"show", params:[destCategoryInstance:destCategoryInstance.id, id:params.id])
		}else{
			flash.message = "${message(code: 'category.must.be.select', args: [message(code: 'category.label', default: 'Category'), params.id])}"
			render(view:"show", model:[categoryInstance:categoryInstance])
		}
	}
	
	
	def edit = {
		def categoryInstance = Category.get(params.id)
		if (!categoryInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'category.label', default: 'Category'), categoryInstance.name])}"
			redirect(action: "list")
		}
		else {
			return [categoryInstance: categoryInstance]
		}
	}
	
	def update = {
		def categoryInstance = Category.get(params.id)
		if (categoryInstance) {
			if (params.version) {
				def version = params.version.toLong()
				if (categoryInstance.version > version) {
					
					categoryInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'category.label', default: 'Category')] as Object[], "Another user has updated this Category while you were editing")
					render(view: "edit", model: [categoryInstance: categoryInstance])
					return
				}
			}
			categoryInstance.properties = params
			if (!categoryInstance.hasErrors() && categoryInstance.save(flush: true)) {
				flash.info = "${message(code: 'category.updated.message', args: [message(code: 'category.label', default: 'Category'), categoryInstance.name])}"
				redirect(action: "show", id: categoryInstance.id)
			}
			else {
				flash.message = "${message(code: 'category.not.updated.message', args: [message(code: 'category.label', default: 'Category'), categoryInstance.name])}"	
				render(view: "edit", model: [categoryInstance: categoryInstance])
			}
		}
		else {
			flash.message = "${message(code: 'category.not.found.message', args: [message(code: 'category.label', default: 'Category'), categoryInstance.name])}"
			redirect(action: "list")
		}
	}
	
	def delete = {
		def categoryInstance = Category.get(params.id)
		if (categoryInstance) {
			if(!categoryInstance.indicators) {
				categoryInstance.delete(flush: true)
				flash.info = "${message(code: 'category.deleted.message', args: [message(code: 'category.label', default: 'Category'), categoryInstance.name])}"
				redirect(action: "list")
			}
			else{
				flash.message = "${message(code: 'category.not.deleted.message', args: [message(code: 'category.label', default: 'Category'), categoryInstance.name])}"
				redirect(action: "show", id: params.id)
			}
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'category.label', default: 'Category'), categoryInstance.name])}"
			redirect(action: "list")
		}
	}
}

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
 
package systemadministration.log

class ChangeLogController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [changeLogInstanceList: ChangeLog.list(params), changeLogInstanceTotal: ChangeLog.count()]
    }

    def create = {
        def changeLogInstance = new ChangeLog()
        changeLogInstance.properties = params
        return [changeLogInstance: changeLogInstance]
    }

    def save = {
        def changeLogInstance = new ChangeLog(params)
        if (changeLogInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'changeLog.label', default: 'ChangeLog'), changeLogInstance.id])}"
            redirect(action: "show", id: changeLogInstance.id)
        }
        else {
            render(view: "create", model: [changeLogInstance: changeLogInstance])
        }
    }

    def show = {
        def changeLogInstance = ChangeLog.get(params.id)
        if (!changeLogInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'changeLog.label', default: 'ChangeLog'), params.id])}"
            redirect(action: "list")
        }
        else {
            [changeLogInstance: changeLogInstance]
        }
    }

    def edit = {
        def changeLogInstance = ChangeLog.get(params.id)
        if (!changeLogInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'changeLog.label', default: 'ChangeLog'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [changeLogInstance: changeLogInstance]
        }
    }

    def update = {
        def changeLogInstance = ChangeLog.get(params.id)
        if (changeLogInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (changeLogInstance.version > version) {
                    
                    changeLogInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'changeLog.label', default: 'ChangeLog')] as Object[], "Another user has updated this ChangeLog while you were editing")
                    render(view: "edit", model: [changeLogInstance: changeLogInstance])
                    return
                }
            }
            changeLogInstance.properties = params
            if (!changeLogInstance.hasErrors() && changeLogInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'changeLog.label', default: 'ChangeLog'), changeLogInstance.id])}"
                redirect(action: "show", id: changeLogInstance.id)
            }
            else {
                render(view: "edit", model: [changeLogInstance: changeLogInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'changeLog.label', default: 'ChangeLog'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def changeLogInstance = ChangeLog.get(params.id)
        if (changeLogInstance) {
            try {
                changeLogInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'changeLog.label', default: 'ChangeLog'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'changeLog.label', default: 'ChangeLog'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'changeLog.label', default: 'ChangeLog'), params.id])}"
            redirect(action: "list")
        }
    }
}

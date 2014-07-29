angular
.module('app.directives')
.directive "mmAwacDocumentQuestion", (directiveService, translationService, $upload, messageFlash, modalService) ->
    restrict: "E"
    scope: {}
    templateUrl: "$/angular/templates/mm-awac-document-question.html"
    replace: true
    compile: () ->
        pre: (scope) ->

            scope.getQuestionCode = ->
                return scope.$parent.getQuestionCode()

            scope.getCondition = ->
                return scope.$parent.getCondition()

            scope.getRepetitionMap = ->
                return scope.$parent.getRepetitionMap()

            scope.getAnswerValue = () ->
                return scope.$parent.$parent.$parent.getAnswerOrCreate(scope.getQuestionCode(), scope.getRepetitionMap())

            scope.inDownload = false
            scope.percent = 0

            scope.$watch 'percent', ->
                scope.style = {
                    "width": scope.percent + "%"
                    "color": ((scope.percent > 50) ? "white": "black")
                }

            scope.hasDescription = () ->
                return translationService.get(scope.getQuestionCode() + '_DESC') != null


            scope.$watch 'ngCondition', () ->
                if scope.getCondition() == false
                    scope.getAnswerValue().value = null
                else if scope.$parent.$parent.$parent.loading == false && scope.getAnswerValue().value == null
                    scope.getAnswerValue().value = scope.$parent.$parent.$parent.getQuestion(scope.getQuestionCode()).defaultValue

            scope.openDocumentManager = ->
                args = {}
                args['listDocuments'] = scope.getAnswerValue().value
                modalService.show(modalService.DOCUMENT_MANAGER,args)


            scope.getFileNumber = ->
                if scope.getAnswerValue().value==null || scope.getAnswerValue().value == undefined
                    return 0
                return Object.keys(scope.getAnswerValue().value).length

            scope.onFileSelect = ($files) ->
                scope.inDownload = true

                #$files: an array of files selected, each file has name, size, and type.
                i = 0

                while i < $files.length
                    file = $files[i]
                    scope.upload = $upload.upload(
                        url: "file/upload/"
                        data:
                            myObj: scope.myModelObj

                        file: file
                    ).progress((evt) ->
                        scope.percent = parseInt(100.0 * evt.loaded / evt.total)
                        console.log "percent: " + scope.percent
                        return
                    ).success((data, status, headers, config) ->
                        # file is uploaded successfully
                        scope.percent = 0
                        scope.inDownload = false
                        fileName = "??"
                        messageFlash.displaySuccess("The file " + fileName + " was upload successfully")
                        console.log data

                        #add the file to the answer
                        if scope.getAnswerValue().value == null || scope.getAnswerValue().value == undefined
                            scope.getAnswerValue().value = {}
                        scope.getAnswerValue().value[data.id] =  data.name

                        console.log "AnswerValue : "
                        console.log scope.getAnswerValue()

                        return
                    ).error((data, status, headers, config) ->
                        scope.percent = 0
                        scope.inDownload = false
                        messageFlash.displayError("The upload of the file was faild")
                        return
                    )
                    i++
                return

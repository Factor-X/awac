define ["../../module"], (directives) ->
  "use strict"
  directives.directive "mmAwacDocumentQuestion", (directiveService, translationService, $upload, messageFlash, modalService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngDataToCompare: '='
    templateUrl: "assets/html/sources/app/eu/factorx/awac/angular/directives/awac/mmAwacDocumentQuestion/template.html"
    replace: true
    compile: () ->
        pre: (scope) ->
            directiveService.autoScopeImpl scope

            #
            # get the question code :
            # call the getQuestionCode from the parent
            #
            scope.getQuestionCode = ->
                return scope.$parent.getQuestionCode()

            #
            # get the answer :
            # call the getAnswerOrCreate parent method or the
            # getAnswerToCompare if the question is a dataToCompare
            #
            scope.getAnswer = ->
                return scope.$parent.getAnswer(scope.getDataToCompare())

            #
            # called when the user change the value of the field
            #
            scope.edited = ->
                scope.$parent.edited()


            #
            # specific functions and variables for upload files
            #

            scope.inDownload = false
            scope.percent = 0

            #
            # change the css of the progress bar
            #
            scope.$watch 'percent', ->
                scope.style = {
                    "width": scope.percent + "%"
                    "color": ((scope.percent > 50) ? "white": "black")
                }

            #
            # open the modal document manager :
            # this modal display already uploaded files and
            # and allows to delete / download files
            #
            scope.openDocumentManager = ->
                if scope.getAnswer()!=null
                    args = {}
                    args['listDocuments'] = scope.getAnswer().value
                    modalService.show(modalService.DOCUMENT_MANAGER,args)

            #
            # return the number of files already uploaded
            #
            scope.getFileNumber = ->
                if scope.getAnswer() == null || scope.getAnswer().value==null || scope.getAnswer().value == undefined
                    return 0
                return Object.keys(scope.getAnswer().value).length

            #
            # listener when the user select a file : launch the
            # upload, display the progress bar and manager
            # the result of the request
            #
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
                        if scope.getAnswer().value == null || scope.getAnswer().value == undefined
                            scope.getAnswer().value = {}
                        scope.getAnswer().value[data.id] =  data.name

                        console.log "AnswerValue : "
                        console.log scope.getAnswer()

                        return
                    ).error((data, status, headers, config) ->
                        scope.percent = 0
                        scope.inDownload = false
                        messageFlash.displayError("The upload of the file was faild")
                        return
                    )
                    i++
                return

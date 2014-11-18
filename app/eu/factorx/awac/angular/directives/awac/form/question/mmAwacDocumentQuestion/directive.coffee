angular
.module('app.directives')
.directive "mmAwacDocumentQuestion", (directiveService, translationService, $upload, messageFlash, modalService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngDataToCompare: '='
        ngIsAggregation:'='
    templateUrl: "$/angular/templates/mm-awac-document-question.html"
    replace: true
    compile: () ->
        pre: (scope) ->
            directiveService.autoScopeImpl scope

            scope.getDisabled = ->
                return scope.$parent.isDisabled()

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
            if scope.getDataToCompare() == false && scope.getIsAggregation() == false
                scope.$watch 'getAnswer().value', (o,n)->
                    if ""+n != ""+o
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

            scope.getDisabled = ->
                return scope.$parent.isDisabled()

            #
            # open the modal document manager :
            # this modal display already uploaded files and
            # and allows to delete / download files
            #
            scope.openDocumentManager = ->
                if scope.getAnswer()!=null
                    args = {}
                    args.listDocuments = scope.getAnswer().value
                    args.readyOnly = scope.getDataToCompare()==true || scope.getIsAggregation()==true || scope.getDisabled() == true
                    args.wasEdited = ->
                        scope.$parent.edited()
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
                        url: "/awac/file/upload/"
                        data:
                            myObj: scope.myModelObj

                        file: file
                    ).progress((evt) ->
                        scope.percent = parseInt(100.0 * evt.loaded / evt.total)
                        #console.log "percent: " + scope.percent
                        return
                    ).success((data, status, headers, config) ->
                        # file is uploaded successfully
                        scope.percent = 0
                        scope.inDownload = false
                        fileName = data.name
                        messageFlash.displaySuccess("The file " + fileName + " was upload successfully")
                        #console.log data

                        #add the file to the answer
                        if scope.getAnswer().value == null || scope.getAnswer().value == undefined
                            scope.getAnswer().value = {}
                        scope.getAnswer().value[data.id] =  data.name

                        scope.$parent.edited()

                        return
                    ).error((data, status, headers, config) ->
                        scope.percent = 0
                        scope.inDownload = false
                        messageFlash.displayError("The upload of the file was faild")
                        return
                    )
                    i++
                return

angular
.module('app.directives')
.directive "mmAwacDocumentQuestion", (directiveService, translationService,$upload) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngQuestionCode: '='
        ngCondition: '='
        ngObject: '='
    templateUrl: "$/angular/templates/mm-awac-document-question.html"
    replace: true
    compile: () ->
        pre: (scope) ->
            directiveService.autoScopeImpl scope

            scope.getAnswerByQuestionCode = (code) ->
                if scope.ngObject
                    for qv in scope.ngObject.answersSaveDTO.listAnswers
                        if qv.questionKey == code
                            return qv
                return null

            scope.hasDescription = () ->
                return translationService.get(scope.getQuestionCode() + '_DESC') != null


            scope.$watch 'ngCondition', () ->
                if scope.getCondition() == false
                    scope.getAnswerValue().value = null
                else if scope.$parent.loading == false
                    scope.getAnswerValue().value = scope.$parent.getQuestion(scope.getQuestionCode()).defaultValue


            scope.percent = 0
            scope.onFileSelect = ($files) ->

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
                        console.log data
                        return
                    )
                    i++
                return
